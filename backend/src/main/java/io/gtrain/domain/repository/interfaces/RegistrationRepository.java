package io.gtrain.domain.repository.interfaces;

import io.gtrain.domain.model.EmsUser;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public interface RegistrationRepository {

	Mono<UserDetails> attemptRegistration(EmsUser user);
}
