package io.gtrain.security.authentication;

import io.gtrain.domain.dto.RegistrationForm;
import io.gtrain.domain.exception.RegistrationFormValidationException;
import io.gtrain.domain.model.*;
import io.gtrain.domain.repository.ReactiveEmsUserInfoRepository;
import io.gtrain.domain.repository.ReactiveRegistrationRepository;
import io.gtrain.domain.validation.RegistrationFormValidator;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author William Gentry
 */
@Service
public class EmsRegistrationService {

	private final ReactiveRegistrationRepository registrationRepository;
	private final Validator registrationFormValidator = new RegistrationFormValidator();
	private final PasswordEncoder encoder;
	private final ReactiveEmsUserInfoRepository userInfoRepository;

	public EmsRegistrationService(ReactiveRegistrationRepository registrationRepository, PasswordEncoder encoder, ReactiveEmsUserInfoRepository userInfoRepository) {
		this.registrationRepository = registrationRepository;
		this.encoder = encoder;
		this.userInfoRepository = userInfoRepository;
	}

	public Mono<UserDetails> attemptRegistration(RegistrationForm form) {
		Errors errors = new BeanPropertyBindingResult(form, RegistrationForm.class.getName());
		registrationFormValidator.validate(form, errors);
		if (errors.getFieldErrors().isEmpty()) {
			return createUserFromRegistrationForm(form).flatMap(registrationRepository::attemptRegistration)
							.compose(userDetails -> {
								return userDetails.flatMap(details -> {
									final EmsUser user = (EmsUser) details;
									return userInfoRepository.save(createUserInfoForUser(user.getId(), form));
								}).then(userDetails);
							});
		}
		return Mono.defer(() -> Mono.error(() -> new RegistrationFormValidationException(errors)));
	}

	private Mono<EmsUser> createUserFromRegistrationForm(RegistrationForm form) {
		if (StringUtils.hasText(form.getUsername()) && StringUtils.hasText(form.getEmail()) && StringUtils.hasText(form.getPassword())) {
			return Mono.just(new EmsUser(form.getUsername(), form.getEmail(), encoder.encode(form.getPassword()), Arrays.asList(new EmsAuthority("ROLE_ADMIN")),
					true, true, true, true));
		}
		return Mono.empty();
	}

	private EmsUserInfo createUserInfoForUser(ObjectId userId, RegistrationForm form) {
		return new EmsUserInfo(userId, new Name(form.getFirstname(), form.getLastname()), new Address(form.getStreet(), form.getCity(), form.getState(), form.getZipCode()), Arrays.asList(new Account(userId, form.getBalance(), form.getType(), form.getAccountName(), form.getMonthlyDeposits())));
	}
}
