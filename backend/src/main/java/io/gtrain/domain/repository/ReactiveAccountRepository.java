package io.gtrain.domain.repository;

import io.gtrain.domain.exception.AccountNotFoundException;
import io.gtrain.domain.model.Account;
import io.gtrain.domain.model.EmsUser;
import io.gtrain.domain.model.EmsUserInfo;
import io.gtrain.domain.repository.interfaces.AccountRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.util.List;

/**
 * @author William Gentry
 */
@Repository
public class ReactiveAccountRepository implements AccountRepository {

	private final ReactiveMongoTemplate mongoTemplate;
	private final Logger logger = Loggers.getLogger(getClass());

	public ReactiveAccountRepository(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<String> save(ObjectId userId, Account account) {
		account.setUserId(userId);
		return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), EmsUserInfo.class).flatMap(userInfo -> {
			userInfo.getAccounts().add(account);
			return mongoTemplate.findAndReplace(Query.query(Criteria.where("userId").is(userId)), userInfo, new FindAndReplaceOptions().returnNew(), EmsUserInfo.class, "user_info")
							.flatMap(info -> Mono.just(account.getName()));
		});
	}

	@Override
	public Mono<Account> getOne(ObjectId userId, String accountName) {
		return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), EmsUserInfo.class).map(userInfo -> {
			return userInfo.getAccounts().stream().filter(account -> account.getName().equals(accountName)).findFirst().orElseThrow(() -> new AccountNotFoundException(accountName));
		});
	}

	@Override
	public Mono<Boolean> remove(ObjectId userId, String accountName) {
		return mongoTemplate.findOne(Query.query(Criteria.where("userId").is(userId)), EmsUserInfo.class).flatMap(userInfo -> {
			logger.info("Attempting to locate account by id {}", accountName);
			List<Account> accounts = userInfo.getAccounts();
			final boolean removed = accounts.removeIf(account -> account.getName().equals(accountName));
			logger.info("Was account removed? {}", removed);
			return mongoTemplate.update(EmsUserInfo.class).matching(Query.query(Criteria.where("userId").is(userId))).apply(getDeleteAccountUpdate(accounts)).first().map(result -> result.wasAcknowledged());
		});
	}

	@Override
	public Flux<Account> findAll(ObjectId userId) {
		return mongoTemplate.find(Query.query(Criteria.where("userId").is(userId)), EmsUserInfo.class).flatMap(userInfo -> {
			return Flux.fromIterable(userInfo.getAccounts());
		});
	}

	private Update getDeleteAccountUpdate(List<Account> accounts) {
		Update update = new Update();
		update.set("accounts", accounts);
		return update;
	}
}
