package io.gtrain.authentication;

import io.gtrain.domain.dto.RegistrationForm;
import io.gtrain.domain.model.EmsAuthority;
import io.gtrain.domain.model.EmsUser;
import io.gtrain.domain.repository.ReactiveRegistrationRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author William Gentry
 */
@Service
public class EmsRegistrationService {

	private final ReactiveRegistrationRepository registrationRepository;
	private final PasswordEncoder encoder;

	public EmsRegistrationService(ReactiveRegistrationRepository registrationRepository, PasswordEncoder encoder) {
		this.registrationRepository = registrationRepository;
		this.encoder = encoder;
	}

	public Mono<UserDetails> attemptRegistration(RegistrationForm form) {
		return createUserFromRegistrationForm(form).flatMap(registrationRepository::attemptRegistration);
	}

	private Mono<EmsUser> createUserFromRegistrationForm(RegistrationForm form) {
		if (StringUtils.hasText(form.getUsername()) && StringUtils.hasText(form.getEmail()) && StringUtils.hasText(form.getPassword())) {
			return Mono.just(new EmsUser(form.getUsername(), form.getEmail(), encoder.encode(form.getPassword()), Arrays.asList(new EmsAuthority("ROLE_USER")),
					true, true, true, true));
		}
		return Mono.empty();
	}
}
