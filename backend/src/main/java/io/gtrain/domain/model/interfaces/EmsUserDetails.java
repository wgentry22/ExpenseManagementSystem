package io.gtrain.domain.model.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author William Gentry
 */
public interface EmsUserDetails extends UserDetails {

	String getEmail();
}
