package io.gtrain.handler;

import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.domain.model.EmsUserInfo;
import io.gtrain.security.authentication.EmsUserInfoService;
import io.gtrain.security.token.TokenFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class UserInfoHandler {

	private final TokenFactory tokenFactory;
	private final EmsUserInfoService userInfoService;

	public UserInfoHandler(TokenFactory tokenFactory, EmsUserInfoService userInfoService) {
		this.tokenFactory = tokenFactory;
		this.userInfoService = userInfoService;
	}

	public Mono<ServerResponse> handleUserInfoRequest(ServerRequest request) {
		final String token = request.cookies().getFirst("api_token").getValue();
		return tokenFactory.getAuthentication(token)
							.cast(EmsAuthenticationToken.class)
							.flatMap(userInfoService::attemptRetrieveUserInfo)
							.flatMap(userInfo -> ServerResponse.ok().body(Mono.just(userInfo), EmsUserInfo.class))
							.onErrorResume(Exception.class, err -> ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
	}
}
