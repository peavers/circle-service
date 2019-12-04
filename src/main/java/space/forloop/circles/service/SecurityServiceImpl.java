package space.forloop.circles.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public Mono<String> getUsername() {

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(authentication -> Mono.just(authentication.getName()));
    }

}
