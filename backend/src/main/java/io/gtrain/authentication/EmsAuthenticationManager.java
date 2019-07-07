package io.gtrain.authentication;

import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.domain.model.interfaces.EmsUserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class EmsAuthenticationManager implements ReactiveAuthenticationManager {

	private final EmsUserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	public EmsAuthenticationManager(EmsUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		final String username = authentication.getName();
		final String pwd = (String) authentication.getCredentials();
		if (StringUtils.hasText(pwd)) {
			return userDetailsService.findByUsername(username)
					.filter(details -> passwordEncoder.matches(pwd, details.getPassword()))
					.switchIfEmpty(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))))
					.cast(EmsUserDetails.class)
					.map(EmsAuthenticationToken::new);
		}
		return Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials")));
	}
}
