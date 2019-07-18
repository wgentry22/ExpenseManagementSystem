package io.gtrain.domain.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.gtrain.domain.model.Expense;

import java.io.IOException;

/**
 * @author William Gentry
 */
public class ExpenseSerializer extends StdSerializer<Expense> {

	public ExpenseSerializer() {
		this(null);
	}

	public ExpenseSerializer(Class<Expense> t) {
		super(t);
	}

	@Override
	public void serialize(Expense expense, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("id", expense.getId().toHexString());
		jsonGenerator.writeStringField("userId", expense.getUserId().toHexString());
		jsonGenerator.writeNumberField("amount", expense.getAmount());
		jsonGenerator.writeStringField("date", expense.getDate().toString());
		jsonGenerator.writeStringField("location", expense.getLocation());
		jsonGenerator.writeStringField("expenseType", expense.getExpenseType().toString());
		jsonGenerator.writeEndObject();
	}
}
