package io.gtrain.domain.exception;

import io.gtrain.domain.dto.ValidationErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author William Gentry
 */
public class RegistrationFormValidationException extends EmsException {

	private final Errors errors;

	public RegistrationFormValidationException(Errors errors) {
		this.errors = errors;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNPROCESSABLE_ENTITY;
	}

	@Override
	public Mono<ValidationErrorMessage> getValidationMessage() {
		Map<String, List<String>> messages = new HashMap<>();
		errors.getFieldErrors().forEach(err -> messages.put(err.getField(), errors.getFieldErrors(err.getField()).stream().map(FieldError::getCode).collect(Collectors.toList())));
		return Mono.just(new ValidationErrorMessage(messages));
	}
}
