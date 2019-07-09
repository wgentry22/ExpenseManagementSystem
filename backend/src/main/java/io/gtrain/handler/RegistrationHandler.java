package io.gtrain.handler;

import io.gtrain.domain.dto.RegistrationForm;
import io.gtrain.domain.dto.ValidationErrorMessage;
import io.gtrain.domain.exception.RegistrationFormValidationException;
import io.gtrain.security.authentication.EmsRegistrationService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
@Service
public class RegistrationHandler {

	private final EmsRegistrationService registrationService;

	public RegistrationHandler(EmsRegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public Mono<ServerResponse> attemptRegistration(ServerRequest request) {
		return request.bodyToMono(RegistrationForm.class)
				.flatMap(registrationService::attemptRegistration)
				.flatMap(details -> ServerResponse.ok().build())
				.onErrorResume(RegistrationFormValidationException.class, err -> ServerResponse.status(err.getHttpStatus()).body(err.getValidationMessage(), ValidationErrorMessage.class));
	}
}
