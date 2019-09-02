package space.forloop.circles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Access denied. Nope. Not coming in. Can't have it. Not allowed. Go away.
 *
 * @author Chris Turner (chris@forloop.space)
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {}
