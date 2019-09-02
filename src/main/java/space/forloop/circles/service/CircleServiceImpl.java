package space.forloop.circles.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.circles.domain.Circle;
import space.forloop.circles.domain.CircleCreateDto;
import space.forloop.circles.exceptions.ForbiddenException;
import space.forloop.circles.repository.CircleRepository;

import java.time.Instant;
import java.util.HashSet;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class CircleServiceImpl implements CircleService {

  /** Used for database actions */
  private final CircleRepository circleRepository;

  /** Used to get the requester username from the request */
  private final SecurityService securityService;

  @Override
  public Mono<Circle> create(final CircleCreateDto circleCreateDto) {

    final HashSet<String> creator = new HashSet<>();

    return securityService
        .getUsername()
        .map(creator::add)
        .then(
            circleRepository.save(
                Circle.builder()
                    .created(Instant.now().toEpochMilli())
                    .name(circleCreateDto.getName())
                    .description(circleCreateDto.getDescription())
                    .featureImage(circleCreateDto.getFeatureImage())
                    .owners(creator)
                    .members(creator)
                    .build()));
  }

  @Override
  public Mono<Circle> findByIdWhereMember(final String circleId) {

    return securityService
        .getUsername()
        .flatMap(
            authentication ->
                circleRepository
                    .findById(circleId)
                    .flatMap(
                        circle ->
                            circle.getMembers().contains(authentication)
                                ? Mono.just(circle)
                                : Mono.error(new ForbiddenException())));
  }

  @Override
  public Mono<Circle> findByIdWhereOwner(final String circleId) {

    return securityService
        .getUsername()
        .flatMap(
            authentication ->
                circleRepository
                    .findById(circleId)
                    .flatMap(
                        circle ->
                            circle.getOwners().contains(authentication)
                                ? Mono.just(circle)
                                : Mono.error(new ForbiddenException())));
  }

  @Override
  public Flux<Circle> findAllWhereMember() {

    return securityService
        .getUsername()
        .flux()
        .flatMap(
            authentication ->
                circleRepository
                    .findAllByMembersIsIn(authentication)
                    .flatMap(
                        circle ->
                            circle.getOwners().contains(authentication)
                                ? Mono.just(circle)
                                : Mono.error(new ForbiddenException())));
  }

  @Override
  public Flux<Circle> findAllWhereOwner() {

    return securityService
        .getUsername()
        .flux()
        .flatMap(
            authentication ->
                circleRepository
                    .findAllByOwnersIsIn(authentication)
                    .flatMap(
                        circle ->
                            circle.getOwners().contains(authentication)
                                ? Mono.just(circle)
                                : Mono.error(new ForbiddenException())));
  }

  @Override
  public Mono<Void> deleteById(final String circleId) {

    return findByIdWhereOwner(circleId).flatMap(circleRepository::delete);
  }

  @Override
  public Mono<Circle> patch(final Circle circle) {

    return findByIdWhereOwner(circle.getId())
        .flatMap(
            existingCircle ->
                circleRepository.save(
                    Circle.builder()
                        .id(circle.getId())
                        .name(circle.getName())
                        .description(circle.getDescription())
                        .featureImage(circle.getFeatureImage())
                        .owners(existingCircle.getOwners())
                        .members(existingCircle.getMembers())
                        .build()));
  }

  @Override
  public Mono<Circle> addMember(final String userId, final String circleId) {

    return findByIdWhereOwner(circleId)
        .flatMap(
            circle -> {
              circle.getMembers().add(userId);
              return circleRepository.save(circle);
            });
  }

  @Override
  public Mono<Circle> removeMember(final String userId, final String circleId) {

    return findByIdWhereOwner(circleId)
        .flatMap(
            circle -> {
              circle.getMembers().remove(userId);
              return circleRepository.save(circle);
            });
  }
}
