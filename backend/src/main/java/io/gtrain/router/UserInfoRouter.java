package io.gtrain.router;

import io.gtrain.handler.UserInfoHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

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
						.build();
	}
}
