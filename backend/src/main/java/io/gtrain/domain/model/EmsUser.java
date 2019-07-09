package io.gtrain.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.gtrain.domain.model.interfaces.EmsUserDetails;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

/**
 * @author William Gentry
 */
@Document(collection = "ems_user")
public class EmsUser implements EmsUserDetails {

	@Id
	private ObjectId id;

	@Indexed(unique = true)
	private final String username;

	@Indexed(unique = true)
	private final String email;

	@JsonIgnore
	private final String password;

	private final List<EmsAuthority> authorities;

	@JsonIgnore
	private final boolean enabled;

	@JsonIgnore
	private final boolean accountNonLocked;

	@JsonIgnore
	private final boolean accountNonExpired;

	@JsonIgnore
	private final boolean credentialsNonExpired;

	public EmsUser(ObjectId id, String username, String email, String password, List<EmsAuthority> authorities, boolean enabled, boolean accountNonLocked, boolean accountNonExpired, boolean credentialsNonExpired) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
		this.accountNonLocked = accountNonLocked;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@PersistenceConstructor
	public EmsUser(String username, String email, String password, List<EmsAuthority> authorities, boolean enabled, boolean accountNonLocked, boolean accountNonExpired, boolean credentialsNonExpired) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
		this.accountNonLocked = accountNonLocked;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<EmsAuthority> getAuthorities() {
		return authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmsUser emsUser = (EmsUser) o;
		return enabled == emsUser.enabled &&
				accountNonLocked == emsUser.accountNonLocked &&
				accountNonExpired == emsUser.accountNonExpired &&
				credentialsNonExpired == emsUser.credentialsNonExpired &&
				Objects.equals(id, emsUser.id) &&
				Objects.equals(username, emsUser.username) &&
				Objects.equals(email, emsUser.email) &&
				Objects.equals(password, emsUser.password) &&
				Objects.equals(authorities, emsUser.authorities);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email, password, authorities, enabled, accountNonLocked, accountNonExpired, credentialsNonExpired);
	}
}
