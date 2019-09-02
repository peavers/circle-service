package space.forloop.circles.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/** @author Chris Turner (chris@forloop.space) */
@EnableWebFluxSecurity
public class SecurityConfig {

  /**
   * Blocks all requests that do not have a valid OKTA JWT
   *
   * @param http incoming request
   * @return a filter that will block everything that isn't an authenticated request
   */
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http) {
    return http.authorizeExchange()
        .pathMatchers("/v1/circles/image/**")
        .permitAll()
        .anyExchange()
        .authenticated()
        .and()
        .oauth2Login()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .and()
        .and()
        .build();
  }

  /**
   * Opens up the world... this should be limited to a specific domain once this is hosted somewhere
   *
   * @return configuration of open CORS
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    configuration.setAllowedMethods(Collections.singletonList("*"));
    configuration.setAllowedHeaders(Collections.singletonList("*"));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}
