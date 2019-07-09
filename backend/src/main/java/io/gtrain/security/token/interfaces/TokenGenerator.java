package io.gtrain.security.token.interfaces;

import org.springframework.security.core.Authentication;

/**
 * @author William Gentry
 */
public interface TokenGenerator {

    String generateToken(Authentication authentication);
}
