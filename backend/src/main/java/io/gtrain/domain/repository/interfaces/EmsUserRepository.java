package io.gtrain.domain.repository.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface EmsUserRepository {

	Mono<UserDetails> findUserByUsername(String username);
	Mono<UserDetails> findUserByEmail(String email);
}
