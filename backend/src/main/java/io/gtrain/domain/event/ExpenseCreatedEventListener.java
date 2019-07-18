package io.gtrain.domain.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.function.BooleanSupplier;


/**
 * @author William Gentry
 */
@Component
public class ExpenseCreatedEventListener implements ApplicationListener<ExpenseCreatedEvent> {

	private final BooleanSupplier REPEAT = () -> true;
	private final BooleanSupplier DO_NOT_REPEAT = () -> false;
	private boolean hasBeenCalled = false;

	@Override
	public void onApplicationEvent(ExpenseCreatedEvent event) {
		toggleBeenCalled();
	}

	public BooleanSupplier getIsReady() {
		try {
			if (hasBeenCalled) {
				return REPEAT;
			} else {
				return DO_NOT_REPEAT;
			}
		} finally {
			toggleBeenCalled();
		}
	}

	private void toggleBeenCalled() { this.hasBeenCalled = !this.hasBeenCalled; }
}
