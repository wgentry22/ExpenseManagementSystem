package io.gtrain.domain.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.gtrain.domain.model.Account;
import io.gtrain.domain.model.AccountType;

import java.io.IOException;

/**
 * @author William Gentry
 */
public class AccountDeserializer extends StdDeserializer<Account> {

	public AccountDeserializer() {
		this(null);
	}

	public AccountDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Account deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode root = jsonParser.getCodec().readTree(jsonParser);
		return new Account(
			root.get("balance").asDouble(),
			AccountType.valueOf(root.get("type").asText()),
			root.get("name").asText(),
			root.get("monthlyDeposits").asDouble()
		);
	}
}
