package io.gtrain.router;

import io.gtrain.handler.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * @author William Gentry
 */
@Configuration
public class ApplicationRouterChain {

	private final List<ApplicationRouter> routers;

	public ApplicationRouterChain(ApplicationContext context) {
		List<ApplicationRouter> routers = new ArrayList<>();
		Arrays.stream(context.getBeanNamesForType(ApplicationRouter.class)).forEach(router -> {
			routers.add((ApplicationRouter) context.getBean(router));
		});
		this.routers = routers;
	}

	@Bean
	public RouterFunction<ServerResponse> applicationRouterBean() {
		RouterFunctions.Builder builder = RouterFunctions.route();
		routers.forEach(router -> builder.add(router.getRoutes()));
		return builder.build();
	}

}
