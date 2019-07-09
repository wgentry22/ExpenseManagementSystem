package io.gtrain.domain.exception;

import io.gtrain.domain.dto.GenericMessage;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public abstract class EmsException extends RuntimeException {

	public abstract HttpStatus getHttpStatus();

	public abstract Mono<? extends GenericMessage<?>> getValidationMessage();
}
