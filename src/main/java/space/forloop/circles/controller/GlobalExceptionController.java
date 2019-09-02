package space.forloop.circles.controller;

import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Handles common exceptions that can be thrown any service such as bad formatted request, or
 * duplication exception.
 *
 * @author Chris Turner (chris@forloop.space)
 */
@RestController
@ControllerAdvice
public class GlobalExceptionController {

  /**
   * Mongo has unique indexes enabled on the value a user has tried to create. Throw this instead.
   *
   * @return bad request
   */
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(MongoWriteException.class)
  public Mono<String> handleDuplicateKeyException() {
    return Mono.just(HttpStatus.CONFLICT.getReasonPhrase());
  }
}
