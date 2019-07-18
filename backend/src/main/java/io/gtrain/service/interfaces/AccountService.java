package io.gtrain.service.interfaces;

import io.gtrain.domain.model.Account;
import org.bson.types.ObjectId;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface AccountService {

	Mono<ServerResponse> createAccount(ObjectId userId, Account account);
	Mono<ServerResponse> getAccounts(ObjectId userId);
	Mono<ServerResponse> getAccount(ObjectId userId, String name);
	Mono<ServerResponse> removeAccount(ObjectId userId, String name);
}
