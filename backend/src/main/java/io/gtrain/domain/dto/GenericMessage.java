package io.gtrain.domain.dto;

import org.springframework.http.HttpStatus;

/**
 * @author William Gentry
 */
public class GenericMessage<T> {

	private final T message;

	public GenericMessage(T message) {
		this.message = message;
	}

	public T getMessage() {
		return message;
	}

}
