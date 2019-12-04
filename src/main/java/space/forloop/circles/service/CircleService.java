package space.forloop.circles.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.circles.domain.Circle;
import space.forloop.circles.domain.CircleCreateDto;

/**
 * Handles actions with groups
 *
 * @author Chris Turner (chris@forloop.space)
 */
public interface CircleService {

    Mono<Circle> addMember(String userId, String circleId);

    Mono<Circle> removeMember(String userId, String circleId);

    Mono<Circle> create(CircleCreateDto circleCreateDto);

    /**
     * Get a circle only if the requesting user is a member of the circle.
     *
     * @param circleId of the circle to request
     *
     * @return the circle if the user is a member, otherwise forbidden
     */
    Mono<Circle> findByIdWhereMember(String circleId);

    /**
     * Get a circle only if the requesting user is the owner of the circle.
     *
     * @param circleId of the circle to request
     *
     * @return the circle if the user is the owner, otherwise forbidden
     */
    Mono<Circle> findByIdWhereOwner(String circleId);

    /**
     * Get all the circles that the requesting user is a member of.
     *
     * @return the circles that the user is a member of, if none then forbidden
     */
    Flux<Circle> findAllWhereMember();

    /**
     * Get all the circles that the requesting user is the owner of.
     *
     * @return the circles that the user is the owner of, if none then forbidden
     */
    Flux<Circle> findAllWhereOwner();

    /**
     * Deletes a circle by its id.
     *
     * @param circleId id of the circle to delete
     */
    Mono<Void> deleteById(String circleId);

    /**
     * Updates a circle with new partial information
     *
     * @param circle with information to replace the old
     *
     * @return newly updated circle
     */
    Mono<Circle> patch(Circle circle);

}
