package io.gtrain.router;

import io.gtrain.handler.AuthenticationHandler;
import io.gtrain.handler.RegistrationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

/**
 * @author William Gentry
 */
@Configuration
public class ApplicationRouter {

	private final AuthenticationHandler authenticationHandler;
	private final RegistrationHandler registrationHandler;

	public ApplicationRouter(AuthenticationHandler authenticationHandler, RegistrationHandler registrationHandler) {
		this.authenticationHandler = authenticationHandler;
		this.registrationHandler = registrationHandler;
	}

	@Bean
	public RouterFunction<ServerResponse> applicationRouterBean() {
		return RouterFunctions
				.route(POST("/login").and(contentType(MediaType.APPLICATION_JSON_UTF8)), authenticationHandler::attemptAuthentication)
				.andRoute(POST("/register").and(contentType(MediaType.APPLICATION_JSON_UTF8)), registrationHandler::attemptRegistration);
	}
}
