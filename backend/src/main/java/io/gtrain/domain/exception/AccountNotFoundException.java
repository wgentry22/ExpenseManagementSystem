package io.gtrain.domain.exception;

import io.gtrain.domain.dto.AccountNotFoundMessage;
import io.gtrain.domain.dto.GenericMessage;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

/**
 * @author William Gentry
 */
public class AccountNotFoundException extends EmsException {

	private final String accountName;

	public AccountNotFoundException(String accountName) {
		this.accountName = accountName;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

	@Override
	public Mono<AccountNotFoundMessage> getValidationMessage() {
		return Mono.just(new AccountNotFoundMessage(accountName));
	}
}
