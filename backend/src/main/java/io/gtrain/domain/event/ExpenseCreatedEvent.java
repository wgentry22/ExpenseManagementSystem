package io.gtrain.domain.event;

import io.gtrain.domain.model.Expense;
import org.springframework.context.ApplicationEvent;

import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class ExpenseCreatedEvent extends ApplicationEvent {

	private final Expense expense;

	public ExpenseCreatedEvent(Object source, Expense expense) {
		super(source);
		this.expense = expense;
	}

	public Expense getExpense() { return expense; }

	@Override
	public String toString() {
		return new StringJoiner(", ", ExpenseCreatedEvent.class.getSimpleName() + "[", "]")
						.add("expense=" + expense)
						.add("source=" + source)
						.toString();
	}
}
