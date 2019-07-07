package io.gtrain.domain.repository;

import io.gtrain.domain.model.EmsUser;
import io.gtrain.domain.repository.interfaces.EmsUserRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Repository
public class ReactiveEmsUserRepository implements EmsUserRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	public ReactiveEmsUserRepository(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<UserDetails> findUserByUsername(String username) {
		return mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), EmsUser.class)
					.switchIfEmpty(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))))
					.cast(UserDetails.class);
	}

	@Override
	public Mono<UserDetails> findUserByEmail(String email) {
		return mongoTemplate.findOne(Query.query(Criteria.where("email").is(email)), EmsUser.class)
				.switchIfEmpty(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))))
				.cast(UserDetails.class);
	}
}
