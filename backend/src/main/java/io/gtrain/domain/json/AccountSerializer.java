package io.gtrain.domain.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.gtrain.domain.model.Account;

import java.io.IOException;

/**
 * @author William Gentry
 */
public class AccountSerializer extends StdSerializer<Account> {

	public AccountSerializer() {
		this(null);
	}

	public AccountSerializer(Class<Account> t) {
		super(t);
	}

	@Override
	public void serialize(Account account, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("userId", account.getUserId().toHexString());
		jsonGenerator.writeNumberField("balance", account.getBalance());
		jsonGenerator.writeNumberField("monthlyDeposits", account.getMonthlyDeposits());
		jsonGenerator.writeStringField("name", account.getName());
		jsonGenerator.writeStringField("type", account.getType().toString());
		jsonGenerator.writeEndObject();
	}
}
