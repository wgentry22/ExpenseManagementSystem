package io.gtrain.unit;

import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.mocking.AuthenticationBaseMock;
import io.gtrain.security.token.TokenFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author William Gentry
 */
@SpringBootTest
public class TokenFactoryTest extends AuthenticationBaseMock {

	@Autowired
	private TokenFactory tokenFactory;

	private final String IMPOSTER_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
	private final EmsAuthenticationToken authenticationToken = new EmsAuthenticationToken(VALID_USER);

	@Test
	void tokenFactory_ShouldGenerateToken() {
		final String token = tokenFactory.generateToken(authenticationToken);
		assertThat(token).isNotBlank();
	}

	@Test
	void tokenFactory_ShouldValidateOwnToken() {
		final String token = tokenFactory.generateToken(authenticationToken);
		assertThat(tokenFactory.isValid(token)).isTrue();
		assertThat(tokenFactory.isValid(IMPOSTER_TOKEN)).isFalse();
	}

	@Test
	void tokenFactory_ShouldParseAuthentication_FromToken() {
		final String token = tokenFactory.generateToken(authenticationToken);
		final Mono<Authentication> authentication = tokenFactory.getAuthentication(token);
		StepVerifier.create(authentication)
								.assertNext(auth -> {
									assertThat(auth).isInstanceOf(EmsAuthenticationToken.class);
									assertThat(auth.isAuthenticated()).isTrue();
									assertThat(auth.getName()).isNotBlank();
									assertThat(((String) auth.getCredentials())).isNotBlank();
									assertThat(auth.getAuthorities()).isNotEmpty();
								})
								.expectComplete()
								.verifyThenAssertThat()
								.hasNotDroppedElements()
								.hasNotDroppedErrors();
	}
}
