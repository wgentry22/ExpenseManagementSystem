package io.gtrain.domain.json;

import io.gtrain.domain.dto.ValidationErrorMessage;

/**
 * @author William Gentry
 */
public class ValidationErrorMessageSerializer extends NestedJsonMessageSerializer<ValidationErrorMessage> {

	public ValidationErrorMessageSerializer() {
		super(ValidationErrorMessage.class);
	}
}
