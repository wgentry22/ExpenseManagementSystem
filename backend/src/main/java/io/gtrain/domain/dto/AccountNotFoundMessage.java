package io.gtrain.domain.dto;

import java.util.Collections;
import java.util.Map;

/**
 * @author William Gentry
 */
public class AccountNotFoundMessage extends GenericMessage<Map<String, String>> {

	public AccountNotFoundMessage(String accountName) {
		super(Collections.singletonMap("message", "Failed to locate '" + accountName + "'"));
	}

}
