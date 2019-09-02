package space.forloop.circles.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.circles.domain.Circle;
import space.forloop.circles.domain.CircleCreateDto;
import space.forloop.circles.service.CircleService;

import javax.validation.Valid;

/**
 * Handles CRUD operations with Circles. Circles represent a space where a person can invite their
 * friends to post and share messages.
 *
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@RestController
@RequestMapping("/v1/circles")
@RequiredArgsConstructor
public class CircleController {

  /** Injected service implementation */
  private final CircleService circleService;

  /**
   * Creates a new circle. The person who calls this endpoint becomes the owner, and the first user
   * of the Circle.
   *
   * @param circle must be a valid circle object, i.e the name value must be unique and not empty
   * @return a new circle object with default values such as id, created, user and owner set.
   */
  @PostMapping
  public Mono<Circle> create(@Valid @RequestBody final CircleCreateDto circle) {
    return circleService.create(circle);
  }

  /**
   * Update an existing circle with new details
   *
   * @param circle to be updated, must include the id value.
   * @return the updated value
   */
  @PatchMapping
  public Mono<Circle> patch(@Valid @RequestBody final Circle circle) {
    return circleService.patch(circle);
  }

  /**
   * Finds all the circles that the user is a member of.
   *
   * @return all the circles the user has access to view or manage
   */
  @GetMapping
  public Flux<Circle> findAll() {
    return circleService.findAllWhereMember();
  }

  /**
   * Returns all details we have about a single {@link Circle}. The circleId of the {@link Circle}
   * must be passed as a path param on the GET request.
   *
   * @param circleId of the circle to retrieve all details of
   * @return the circle object assuming it can be found
   */
  @GetMapping("/{circleId}")
  public Mono<Circle> findById(@PathVariable final String circleId) {
    return circleService.findByIdWhereMember(circleId);
  }

  /**
   * Delete the circle if the user has permissions
   *
   * @param circleId id of the chicle to delete
   * @return void
   */
  @DeleteMapping("/{circleId}")
  public Mono<Void> deleteById(@PathVariable final String circleId) {
    return circleService.deleteById(circleId);
  }

  @PatchMapping("/{circleId}/add/{userId})")
  public Mono<Circle> addMember(
      @PathVariable final String circleId, @PathVariable final String userId) {
    return circleService.addMember(circleId, userId);
  }

  @PatchMapping("/{circleId}/remove//{userId})")
  public Mono<Circle> removeMember(
      @PathVariable final String circleId, @PathVariable final String userId) {
    return circleService.removeMember(circleId, userId);
  }
}
