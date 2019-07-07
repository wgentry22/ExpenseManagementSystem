package io.gtrain.domain.dto;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class LoginForm {

	private String userId;
	private String password;

	public LoginForm() {}

	public LoginForm(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LoginForm loginForm = (LoginForm) o;
		return Objects.equals(userId, loginForm.userId) &&
				Objects.equals(password, loginForm.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, password);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", LoginForm.class.getSimpleName() + "[", "]")
				.add("userId='" + userId + "'")
				.add("password='" + password + "'")
				.toString();
	}
}
