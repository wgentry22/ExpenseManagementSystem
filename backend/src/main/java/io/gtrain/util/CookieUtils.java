package io.gtrain.util;

import org.springframework.http.ResponseCookie;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public class CookieUtils {

	public static final String API_TOKEN = "api_token";

	public static ResponseCookie getApiToken(String value) {
		return ResponseCookie.from(API_TOKEN, value).path("/api/v1").build();
	}

	public static Mono<String> getJwtFromExchange(ServerWebExchange exchange) {
		return Mono.just(exchange.getRequest().getCookies().getFirst(API_TOKEN).getValue());
	}
}
