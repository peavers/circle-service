package space.forloop.circles.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.circles.domain.Post;
import space.forloop.circles.service.PostService;

import javax.validation.Valid;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@RestController
@RequestMapping("/v1/circles")
@RequiredArgsConstructor
public class PostController {

  /** Injected service implementation */
  private final PostService postService;

  /**
   * Find all the posts in the circle that the user has access to.
   *
   * @param circleId of the circle to retrieve all posts of
   * @return a stream of {@link Post} objects that belong to the circle
   */
  @GetMapping("/{circleId}/posts")
  public Flux<Post> findAllByCircleId(@PathVariable final String circleId) {
    return postService.findAllByCircleId(circleId);
  }

  /**
   * Create a new post under the circle.
   *
   * @param circleId of the circle to retrieve all posts of
   * @param post must be a valid object
   * @return the newly created {@link Post} object with all values populated
   */
  @PostMapping("/{circleId}/posts")
  public Mono<Post> create(
      @PathVariable final String circleId, @Valid @RequestBody final Post post) {
    return postService.create(circleId, post);
  }
}
