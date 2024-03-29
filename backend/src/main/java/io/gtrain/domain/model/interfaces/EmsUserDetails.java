package io.gtrain.domain.model.interfaces;

import io.gtrain.domain.model.Account;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author William Gentry
 */
public interface EmsUserDetails extends UserDetails {

	ObjectId getId();
	String getEmail();
}
