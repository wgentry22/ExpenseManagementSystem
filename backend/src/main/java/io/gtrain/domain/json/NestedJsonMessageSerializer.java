package io.gtrain.domain.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.gtrain.domain.dto.GenericMessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author William Gentry
 */
public class NestedJsonMessageSerializer<T extends GenericMessage<Map<String, List<String >>>> extends StdSerializer<T> {

	public NestedJsonMessageSerializer() {
		this(null);
	}

	public NestedJsonMessageSerializer(Class<T> t) {
		super(t);
	}

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
		gen.writeStartObject();
		value.getMessage().forEach((k, v) -> {
			try {
				gen.writeArrayFieldStart(k);
				v.forEach(el -> {
					try {
						gen.writeString(el);
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				});
				gen.writeEndArray();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
		gen.writeEndObject();
	}
}
