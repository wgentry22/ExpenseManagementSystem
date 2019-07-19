package io.gtrain.router;

import io.gtrain.handler.UserInfoHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

/**
 * @author William Gentry
 */
@Component
public class UserInfoRouter implements ApplicationRouter {

	private final UserInfoHandler userInfoHandler;

	public UserInfoRouter(UserInfoHandler userInfoHandler) {
		this.userInfoHandler = userInfoHandler;
	}

	@Override
	public RouterFunction<ServerResponse> getRoutes() {
		return RouterFunctions.route()
						.GET("/api/v1/info", userInfoHandler::handleUserInfoRequest)
						.PUT("/api/v1/info/address", accept(MediaType.APPLICATION_JSON_UTF8).and(contentType(MediaType.APPLICATION_JSON_UTF8)), userInfoHandler::handleUpdateUserAddress)
						.build();
	}
}
