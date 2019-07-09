package io.gtrain.security.authentication;

import io.gtrain.security.converter.JwtConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Service;

/**
 * @author William Gentry
 */
@Service
public class EmsTokenAuthenticationFilter extends AuthenticationWebFilter {

	public EmsTokenAuthenticationFilter(@Qualifier("tokenAuthenticationManager") TokenAuthenticationManager authenticationManager, JwtConverter converter) {
		super(authenticationManager);
		super.setServerAuthenticationConverter(converter);
		super.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/v1/**"));
	}
}
