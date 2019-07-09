package io.gtrain.security.authentication;

import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.domain.model.interfaces.EmsUserDetails;
import io.gtrain.security.converter.JwtConverter;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service("tokenAuthenticationManager")
public class TokenAuthenticationManager implements ReactiveAuthenticationManager {

	private final EmsUserDetailsService userDetailsService;

	public TokenAuthenticationManager(EmsUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return userDetailsService.findByUsername(authentication.getName()).cast(EmsUserDetails.class).map(EmsAuthenticationToken::new);
	}
}
