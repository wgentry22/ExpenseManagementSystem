package io.gtrain.router;

import io.gtrain.handler.AuthenticationHandler;
import io.gtrain.handler.RegistrationHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author William Gentry
 */
@Component
public class AuthenticationRouter implements ApplicationRouter {

	private final AuthenticationHandler authenticationHandler;
	private final RegistrationHandler registrationHandler;

	public AuthenticationRouter(AuthenticationHandler authenticationHandler, RegistrationHandler registrationHandler) {
		this.authenticationHandler = authenticationHandler;
		this.registrationHandler = registrationHandler;
	}

	@Override
	public RouterFunction<ServerResponse> getRoutes() {
		return RouterFunctions.route()
						.POST("/login", authenticationHandler::attemptAuthentication)
//						.POST("/register", RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8), registrationHandler::attemptRegistration)
						.build();
	}
}
