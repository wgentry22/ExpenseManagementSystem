package io.gtrain.security.token.interfaces;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface TokenParser {

    Mono<Authentication> getAuthentication(String token);
}
