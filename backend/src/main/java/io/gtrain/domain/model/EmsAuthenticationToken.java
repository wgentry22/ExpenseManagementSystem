package io.gtrain.domain.model;

import io.gtrain.domain.dto.LoginForm;
import io.gtrain.domain.model.interfaces.EmsUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author William Gentry
 */
public class EmsAuthenticationToken extends UsernamePasswordAuthenticationToken {

	public EmsAuthenticationToken(LoginForm loginForm) {
		super(loginForm.getUserId(), loginForm.getPassword());
	}

	public EmsAuthenticationToken(EmsUserDetails userDetails) {
		super(userDetails, "", userDetails.getAuthorities());
	}
}
