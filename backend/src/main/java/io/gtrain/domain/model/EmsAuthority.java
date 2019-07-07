package io.gtrain.domain.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author William Gentry
 */
public class EmsAuthority implements GrantedAuthority {

	private final String authority;

	public EmsAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
}
