package space.forloop.circles.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import space.forloop.circles.domain.Post;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface PostRepository extends ReactiveMongoRepository<Post, String> {

  Flux<Post> findAllByCircleIdOrderByCreatedDesc(String owner);
}
