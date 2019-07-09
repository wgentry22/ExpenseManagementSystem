package io.gtrain.domain.repository;

import io.gtrain.domain.dto.RegistrationForm;
import io.gtrain.domain.model.EmsAuthority;
import io.gtrain.domain.model.EmsUser;
import io.gtrain.domain.repository.interfaces.RegistrationRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author William Gentry
 */
@Repository
public class ReactiveRegistrationRepository implements RegistrationRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	public ReactiveRegistrationRepository(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<UserDetails> attemptRegistration(EmsUser user) {
		return mongoTemplate.save(user, "ems_user");
	}
}
