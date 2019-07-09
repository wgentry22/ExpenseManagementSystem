package io.gtrain.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gtrain.domain.json.ValidationErrorMessageSerializer;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * @author William Gentry
 */
@JsonSerialize(using = ValidationErrorMessageSerializer.class)
public class ValidationErrorMessage extends GenericMessage<Map<String, List<String>>> {

	public ValidationErrorMessage(Map<String, List<String>> message) {
		super(message);
	}
}
