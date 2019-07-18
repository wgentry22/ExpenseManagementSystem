package io.gtrain.service;

import io.gtrain.domain.model.Account;
import io.gtrain.domain.repository.interfaces.AccountRepository;
import io.gtrain.service.interfaces.AccountService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author William Gentry
 */
@Service
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Mono<ServerResponse> createAccount(ObjectId userId, Account account) {
		return accountRepository.save(userId, account)
						.flatMap(accountId -> ServerResponse.created(URI.create("/api/v1/account/" + accountId)).build())
						.switchIfEmpty(ServerResponse.badRequest().build());
	}

	@Override
	public Mono<ServerResponse> getAccounts(ObjectId userId) {
		Flux<Account> accountFlux = accountRepository.findAll(userId);
		return ServerResponse.ok().body(accountFlux, Account.class);
	}

	@Override
	public Mono<ServerResponse> getAccount(ObjectId userId, String name) {
		Mono<Account> accountMono = accountRepository.getOne(userId, name);
		return ServerResponse.ok().body(accountMono, Account.class).switchIfEmpty(ServerResponse.notFound().build());
	}

	@Override
	public Mono<ServerResponse> removeAccount(ObjectId userId, String name) {
		return accountRepository.remove(userId, name).filter(result -> result)
						.flatMap(wasRemoved -> {
							return ServerResponse.noContent().build();
						})
						.switchIfEmpty(ServerResponse.notFound().build());
	}
}
