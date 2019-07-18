package io.gtrain.router;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author William Gentry
 */
public interface ApplicationRouter {

	RouterFunction<ServerResponse> getRoutes();
}
