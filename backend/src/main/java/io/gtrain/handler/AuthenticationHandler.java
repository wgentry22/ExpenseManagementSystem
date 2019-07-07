package io.gtrain.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.gtrain.authentication.EmsAuthenticationManager;
import io.gtrain.domain.dto.LoginForm;
import io.gtrain.domain.model.EmsAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;
import sun.rmi.runtime.Log;

/**
 * @author William Gentry
 */
@Service
public class AuthenticationHandler {

	private final EmsAuthenticationManager authenticationManager;
	private final Logger logger = Loggers.getLogger(getClass());

	public AuthenticationHandler(EmsAuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public Mono<ServerResponse> attemptAuthentication(ServerRequest request) {
		return request.bodyToMono(LoginForm.class)
				.map(EmsAuthenticationToken::new)
				.map(auth -> {
					logger.info("Found authentication: {}", auth);
					return auth;
				})
				.flatMap(authenticationManager::authenticate)
				.flatMap(authentication -> ServerResponse.ok().build())
				.onErrorResume(BadCredentialsException.class, err -> ServerResponse.status(HttpStatus.UNAUTHORIZED).body(Mono.just(err.getMessage()), String.class));
	}
}
