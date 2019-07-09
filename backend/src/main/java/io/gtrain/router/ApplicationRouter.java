package io.gtrain.router;

import io.gtrain.handler.AuthenticationHandler;
import io.gtrain.handler.RegistrationHandler;
import io.gtrain.handler.UserInfoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author William Gentry
 */
@Configuration
public class ApplicationRouter {

	private final AuthenticationHandler authenticationHandler;
	private final RegistrationHandler registrationHandler;
	private final UserInfoHandler userInfoHandler;

	public ApplicationRouter(AuthenticationHandler authenticationHandler, RegistrationHandler registrationHandler, UserInfoHandler userInfoHandler) {
		this.authenticationHandler = authenticationHandler;
		this.registrationHandler = registrationHandler;
		this.userInfoHandler = userInfoHandler;
	}

	@Bean
	public RouterFunction<ServerResponse> applicationRouterBean() {
		return RouterFunctions
				.route(POST("/login").and(contentType(MediaType.APPLICATION_JSON_UTF8)), authenticationHandler::attemptAuthentication)
				.andRoute(POST("/register").and(contentType(MediaType.APPLICATION_JSON_UTF8)), registrationHandler::attemptRegistration)
				.andRoute(GET("/api/v1/info"), userInfoHandler::handleUserInfoRequest);
	}
}
