package io.gtrain.mocking;

import io.gtrain.domain.model.EmsAuthority;
import io.gtrain.domain.model.EmsUser;
import io.gtrain.domain.repository.ReactiveEmsUserRepository;
import io.gtrain.security.authentication.EmsAuthenticationManager;
import io.gtrain.security.authentication.EmsUserDetailsService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;

/**
 * @author William Gentry
 */
@ExtendWith(MockitoExtension.class)
public class AuthenticationBaseMock {

	protected final String RAW_PASSWORD = "Password123!";
	private final String ENCODED_PASSWORD = "$2a$13$l3iT7lZ/LbeGZrCL1LGMV.Q8AyHbnim5.uKASwcj06rA0RpwZk6mS";

	protected EmsUserDetailsService userDetailsService;
	protected EmsAuthenticationManager authenticationManager;

	protected final EmsUser VALID_USER = new EmsUser(new ObjectId(), "test", "test@email.com", ENCODED_PASSWORD, Arrays.asList(new EmsAuthority("ROLE_TEST")), true, true, true, true);

	@BeforeEach
	void initUserDetailsService(@Mock ReactiveEmsUserRepository userRepository, @Mock PasswordEncoder passwordEncoder) {
		userDetailsService = new EmsUserDetailsService(userRepository);
		authenticationManager = new EmsAuthenticationManager(userDetailsService, passwordEncoder);

		Mockito.lenient().when(userRepository.findUserByUsername("test")).thenReturn(Mono.just(VALID_USER));
		Mockito.lenient().when(userRepository.findUserByUsername(not(eq("test")))).thenReturn(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))));

		Mockito.lenient().when(userRepository.findUserByEmail("test@email.com")).thenReturn(Mono.just(VALID_USER));
		Mockito.lenient().when(userRepository.findUserByEmail(not(eq("test@email.com")))).thenReturn(Mono.defer(() -> Mono.error(() -> new BadCredentialsException("Invalid Credentials"))));

		Mockito.lenient().when(passwordEncoder.matches(RAW_PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
	}

}
