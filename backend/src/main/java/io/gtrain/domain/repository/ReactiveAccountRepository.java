package io.gtrain.domain.repository;

import io.gtrain.domain.exception.AccountNotFoundException;
import io.gtrain.domain.model.Account;
import io.gtrain.domain.model.EmsUser;
import io.gtrain.domain.model.EmsUserInfo;
import io.gtrain.domain.repository.interfaces.AccountRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Repository
public class ReactiveAccountRepository implements AccountRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	public ReactiveAccountRepository(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<String> save(ObjectId userId, Account account) {
		account.setUserId(userId);
		return mongoTemplate.save(account, "account").map(Account::getName);
	}

	@Override
	public Mono<Account> getOne(ObjectId userId, String accountName) {
		return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), EmsUserInfo.class).map(userInfo -> {
			return userInfo.getAccounts().stream().filter(account -> account.getName().equals(accountName)).findFirst().orElseThrow(() -> new AccountNotFoundException(accountName));
		});
	}

	@Override
	public Mono<Boolean> remove(ObjectId userId, String accountName) {
		return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), EmsUserInfo.class).map(userInfo -> {
			return userInfo.getAccounts().removeIf(account -> account.getName().equals(accountName));
		});
	}

	@Override
	public Flux<Account> findAll(ObjectId userId) {
		return mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), EmsUserInfo.class).flatMap(userInfo -> {
			return Flux.fromIterable(userInfo.getAccounts());
		});
	}
}
