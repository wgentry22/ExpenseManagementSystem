package io.gtrain.router;

import io.gtrain.handler.ExpenseHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author William Gentry
 */
@Component
public class ExpenseRouter implements ApplicationRouter {

	private final ExpenseHandler expenseHandler;

	public ExpenseRouter(ExpenseHandler expenseHandler) {
		this.expenseHandler = expenseHandler;
	}

	@Override
	public RouterFunction<ServerResponse> getRoutes() {
		return RouterFunctions.route()
						.GET("/api/v1/expense/{id}", expenseHandler::getExpense)
						.GET("/api/v1/month/expense", expenseHandler::getAllExpenseInMonth)
						.GET("/api/v1/month/expense/days", expenseHandler::getAllExpensesInMonthByDay)
						.GET("/api/v1/expense", expenseHandler::getAllExpenses)
						.POST("/api/v1/expense", expenseHandler::createExpense)
						.build();
	}
}
