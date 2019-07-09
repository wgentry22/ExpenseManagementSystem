package io.gtrain.domain.model;

import io.gtrain.domain.dto.LoginForm;
import io.gtrain.domain.model.interfaces.EmsUserDetails;
import io.jsonwebtoken.Claims;
import org.bson.types.ObjectId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author William Gentry
 */
public class EmsAuthenticationToken extends UsernamePasswordAuthenticationToken {

	public EmsAuthenticationToken(LoginForm loginForm) {
		super(loginForm.getUserId(), loginForm.getPassword());
	}

	public EmsAuthenticationToken(EmsUserDetails userDetails) {
		super(userDetails, userDetails.getId().toHexString(), userDetails.getAuthorities());
	}

	public EmsAuthenticationToken(Claims claimsSet) {
		super(claimsSet.getSubject(), claimsSet.get("uid"), Arrays.stream(((String) claimsSet.get("aty")).split(",")).map(EmsAuthority::new).collect(Collectors.toList()));
	}
}
