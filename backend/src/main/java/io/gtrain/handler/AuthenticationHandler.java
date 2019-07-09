package io.gtrain.handler;

import io.gtrain.domain.dto.JWT;
import io.gtrain.domain.dto.LoginForm;
import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.security.authentication.EmsAuthenticationManager;
import io.gtrain.security.token.TokenFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class AuthenticationHandler {

	private final EmsAuthenticationManager authenticationManager;
	private final TokenFactory tokenFactory;

	public AuthenticationHandler(EmsAuthenticationManager authenticationManager, TokenFactory tokenFactory) {
		this.authenticationManager = authenticationManager;
		this.tokenFactory = tokenFactory;
	}

	public Mono<ServerResponse> attemptAuthentication(ServerRequest request) {
		return request.bodyToMono(LoginForm.class)
				.map(EmsAuthenticationToken::new)
				.flatMap(authenticationManager::authenticate)
				.flatMap(authentication -> ServerResponse.ok().body(Mono.just(new JWT(tokenFactory.generateToken(authentication))), JWT.class))
				.onErrorResume(BadCredentialsException.class, err -> ServerResponse.status(HttpStatus.UNAUTHORIZED).body(Mono.just(err.getMessage()), String.class));
	}
}
