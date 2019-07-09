package io.gtrain.unit;

import io.gtrain.domain.dto.LoginForm;
import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.mocking.AuthenticationBaseMock;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
public class EmsAuthenticationManagerTest extends AuthenticationBaseMock {

	private final LoginForm VALID_USERNAME_FORM = new LoginForm("test", RAW_PASSWORD);
	private final LoginForm VALID_EMAIL_FORM = new LoginForm("test@email.com", RAW_PASSWORD);

	private final LoginForm INVALID_USERNAME_FORM = new LoginForm("invalid", RAW_PASSWORD);
	private final LoginForm INVALID_EMAIL_FORM = new LoginForm("invalid@test.com", RAW_PASSWORD);

	@Test
	void authenticationManager_ShouldReturnFullyAuthenticatedToken_ForValidUsernameAndPassword() {
		final Mono<Authentication> authentication = authenticationManager.authenticate(new EmsAuthenticationToken(VALID_USERNAME_FORM));
		StepVerifier.create(authentication)
								.assertNext(auth -> {
									assertThat(auth.isAuthenticated()).isTrue();
									assertThat(auth.getName()).isNotBlank();
									assertThat(((String) auth.getCredentials())).isBlank();
									assertThat(auth.getAuthorities()).isNotEmpty();
								})
								.expectComplete()
								.verifyThenAssertThat()
								.hasNotDroppedElements()
								.hasNotDroppedErrors();
	}

	@Test
	void authenticationManager_ShouldReturnFullyAuthenticatedToken_ForValidEmailAndPassword() {
		final Mono<Authentication> authentication = authenticationManager.authenticate(new EmsAuthenticationToken(VALID_EMAIL_FORM));
		StepVerifier.create(authentication)
						.assertNext(auth -> {
							assertThat(auth.isAuthenticated()).isTrue();
							assertThat(auth.getName()).isNotBlank();
							assertThat(((String) auth.getCredentials())).isBlank();
							assertThat(auth.getAuthorities()).isNotEmpty();
						})
						.expectComplete()
						.verifyThenAssertThat()
						.hasNotDroppedElements()
						.hasNotDroppedErrors();
	}

	@Test
	void authenticationManager_ShouldThrowBadCredentialsException_ForInvalidUsernameAndPassword() {
		final Mono<Authentication> authentication = authenticationManager.authenticate(new EmsAuthenticationToken(INVALID_USERNAME_FORM));
		StepVerifier.create(authentication)
								.consumeErrorWith(err -> {
									assertThat(err).isInstanceOf(BadCredentialsException.class);
									assertThat(err.getMessage()).isEqualTo("Invalid Credentials");
								})
								.verifyThenAssertThat()
								.hasNotDroppedErrors()
								.hasNotDroppedElements();
	}

	@Test
	void authenticationManager_ShouldThrowBadCredentialsException_ForInvalidEmailAndPassword() {
		final Mono<Authentication> authentication = authenticationManager.authenticate(new EmsAuthenticationToken(INVALID_USERNAME_FORM));
		StepVerifier.create(authentication)
						.consumeErrorWith(err -> {
							assertThat(err).isInstanceOf(BadCredentialsException.class);
							assertThat(err.getMessage()).isEqualTo("Invalid Credentials");
						})
						.verifyThenAssertThat()
						.hasNotDroppedErrors()
						.hasNotDroppedElements();
	}
}
