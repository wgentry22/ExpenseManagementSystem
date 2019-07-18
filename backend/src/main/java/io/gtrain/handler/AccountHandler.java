package io.gtrain.handler;

import io.gtrain.domain.model.Account;
import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.service.interfaces.AccountService;
import org.bson.types.ObjectId;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class AccountHandler {

	private final AccountService accountService;

	public AccountHandler(AccountService accountService) {
		this.accountService = accountService;
	}

	public Mono<ServerResponse> handleCreateAccount(ServerRequest request) {
		return request.principal().cast(EmsAuthenticationToken.class)
						.map(EmsAuthenticationToken::getCredentials)
						.cast(String.class)
						.flatMap(userId -> request.bodyToMono(Account.class).flatMap(account -> {
							return accountService.createAccount(new ObjectId(userId), account);
						}));
	}

	public Mono<ServerResponse> handleGetAccount(ServerRequest request) {
		final String name = request.pathVariable("id");
		return request.principal().cast(EmsAuthenticationToken.class)
						.map(EmsAuthenticationToken::getCredentials)
						.cast(String.class)
						.flatMap(userId -> {
							return accountService.getAccount(new ObjectId(userId), name);
						});
	}

	public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
		return request.principal().cast(EmsAuthenticationToken.class)
						.map(EmsAuthenticationToken::getCredentials)
						.cast(String.class)
						.flatMap(userId -> {
							return accountService.getAccounts(new ObjectId(userId));
						});
	}

	public Mono<ServerResponse> handleDeleteAccount(ServerRequest request) {
		final String name = request.pathVariable("id");
		return request.principal().cast(EmsAuthenticationToken.class)
						.map(EmsAuthenticationToken::getCredentials)
						.cast(String.class)
						.flatMap(userId -> {
							return accountService.removeAccount(new ObjectId(userId), name);
						});
	}
}
