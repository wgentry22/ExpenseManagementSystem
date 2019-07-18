package io.gtrain.domain.repository.interfaces;

import io.gtrain.domain.model.Account;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface AccountRepository {

	Mono<String> save(ObjectId userId, Account account);
	Mono<Account> getOne(ObjectId userId, String accountName);
	Mono<Boolean> remove(ObjectId userId, String accountName);
	Flux<Account> findAll(ObjectId userId);
}
