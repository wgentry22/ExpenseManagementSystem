package io.gtrain.security.converter;

import io.gtrain.security.token.TokenFactory;
import io.gtrain.util.CookieUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class JwtConverter implements ServerAuthenticationConverter {

	private final TokenFactory tokenFactory;

	public JwtConverter(TokenFactory tokenFactory) {
		this.tokenFactory = tokenFactory;
	}

	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		return CookieUtils.getJwtFromExchange(exchange)
							.filter(tokenFactory::isValid)
							.flatMap(tokenFactory::getAuthentication);
	}
}
