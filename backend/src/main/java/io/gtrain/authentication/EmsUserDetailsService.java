package io.gtrain.authentication;

import io.gtrain.domain.repository.interfaces.EmsUserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class EmsUserDetailsService implements ReactiveUserDetailsService {

	private final EmsUserRepository userRepository;

	public EmsUserDetailsService(EmsUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return username.contains("@") ? userRepository.findUserByEmail(username) : userRepository.findUserByUsername(username);
	}
}
