package io.gtrain.domain.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.gtrain.domain.model.Expense;
import io.gtrain.domain.model.ExpenseType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author William Gentry
 */
public class ExpenseDeserializer extends StdDeserializer<Expense> {

	public ExpenseDeserializer() {
		this(null);
	}

	public ExpenseDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Expense deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode root = jsonParser.getCodec().readTree(jsonParser);
		return new Expense(
			root.get("amount").asDouble(),
			ZonedDateTime.parse(root.get("date").asText(), DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime(),
			root.get("location").asText(),
			ExpenseType.valueOf(root.get("expenseType").asText())
		);
	}
}
