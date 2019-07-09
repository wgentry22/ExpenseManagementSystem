package io.gtrain.security.token.interfaces;

/**
 * @author William Gentry
 */
public interface TokenValidator {

	boolean isValid(String token);
}
