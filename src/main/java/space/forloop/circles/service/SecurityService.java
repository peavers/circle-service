package space.forloop.circles.service;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

/**
 * Handles security based helpers
 *
 * @author Chris Turner (chris@forloop.space)
 */
public interface SecurityService {

    /**
     * Very simple method that will extract the username of the user making a HTTP request from the {@link
     * ReactiveSecurityContextHolder}
     *
     * @return the username of who is making the request
     */
    Mono<String> getUsername();

}
