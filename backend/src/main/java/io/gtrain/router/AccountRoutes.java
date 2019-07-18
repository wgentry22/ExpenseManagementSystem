package io.gtrain.router;

import io.gtrain.handler.AccountHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author William Gentry
 */
@Component
public class AccountRoutes implements ApplicationRouter {

	private final AccountHandler accountHandler;

	public AccountRoutes(AccountHandler accountHandler) {
		this.accountHandler = accountHandler;
	}

	@Override
	public RouterFunction<ServerResponse> getRoutes() {
		return RouterFunctions.route()
						.POST("/api/v1/account", accountHandler::handleCreateAccount)
						.GET("/api/v1/account", accountHandler::getAllAccounts)
						.GET("/api/v1/account/{id}", accountHandler::handleGetAccount)
						.DELETE("/api/v1/account/{id}", accountHandler::handleDeleteAccount)
						.build();
	}
}
