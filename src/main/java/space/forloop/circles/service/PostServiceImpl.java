package space.forloop.circles.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.circles.domain.Post;
import space.forloop.circles.repository.PostRepository;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    /**
     * Exchange name for the message broker.
     */
    private static final String RABBIT_EXCHANGE = "circle-service-exchange";

    /**
     * Routing key for the message broker.
     */
    private static final String RABBIT_ROUTE_KEY = "circleId.";

    /**
     * If a post is created, push it to a message broker. This allows live updates in the UI.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * Used for database actions
     */
    private final PostRepository postRepository;

    /**
     * Used for database actions
     */
    private final CircleService circleService;

    /**
     * Used to get the requester username from the request
     */
    private final SecurityService securityService;

    @Override
    public Mono<Post> create(final String circleId, final Post post) {

        return circleService
                .findByIdWhereMember(circleId)
                .flatMap(
                        circle ->
                                securityService
                                        .getUsername()
                                        .flatMap(
                                                authentication ->
                                                        Mono.just(
                                                                Post.builder()
                                                                        .circleId(circleId)
                                                                        .message(post.getMessage())
                                                                        .ownerId(authentication)
                                                                        .build()))
                                        .flatMap(postRepository::save))
                .doOnSuccess(
                        savedPost ->
                                rabbitTemplate.convertAndSend(
                                        RABBIT_EXCHANGE, RABBIT_ROUTE_KEY + savedPost.getCircleId(), savedPost));
    }

    @Override
    public Flux<Post> findAllByCircleId(final String circleId) {

        return circleService
                .findByIdWhereMember(circleId)
                .flux()
                .flatMap(circle -> postRepository.findAllByCircleIdOrderByCreatedDesc(circleId));
    }

}
