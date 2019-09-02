package space.forloop.circles.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.circles.domain.Post;
import space.forloop.circles.exceptions.ForbiddenException;

/**
 * Handles creating new posts under specific circles. All methods require the user be at least a
 * member of the circle otherwise a forbidden exception is returned.
 *
 * @author Chris Turner (chris@forloop.space)
 */
public interface PostService {

  /**
   * Create a new post under a specific circle.
   *
   * @param circleId where the post will live
   * @param post must contain just the message of the post
   * @return the newly created post object
   */
  Mono<Post> create(String circleId, Post post);

  /**
   * Returns a stream of posts in the circle. The user must be a member of the circle.
   *
   * @param circleId of where to find the posts from
   * @return a stream of posts if the user is permitted otherwise {@link ForbiddenException}
   */
  Flux<Post> findAllByCircleId(String circleId);
}
