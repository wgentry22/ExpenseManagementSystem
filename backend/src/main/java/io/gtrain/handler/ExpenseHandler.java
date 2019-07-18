package io.gtrain.handler;

import io.gtrain.domain.model.EmsAuthenticationToken;
import io.gtrain.domain.model.Expense;
import io.gtrain.domain.repository.interfaces.ExpenseRepository;
import io.gtrain.service.ExpenseService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;

/**
 * @author William Gentry
 */
@Service
public class ExpenseHandler {

	private final ExpenseService expenseService;

	public ExpenseHandler(ExpenseService expenseService) {
		this.expenseService = expenseService;
	}

	public Mono<ServerResponse> createExpense(ServerRequest request) {
		return request.principal().cast(EmsAuthenticationToken.class)
						.map(EmsAuthenticationToken::getCredentials)
						.cast(String.class)
						.flatMap(userId -> request.bodyToMono(Expense.class).flatMap(expense -> {
							expense.setUserId(new ObjectId(userId));
							return Mono.just(expense);
						}))
						.flatMap(expenseService::createExpense);
	}

	public Mono<ServerResponse> getAllExpenses(ServerRequest request) {
		final int limit = Integer.parseInt(request.queryParam("limit").orElse("10"));
		final long page = Long.parseLong(request.queryParam("page").orElse("0"));
		return request.principal().cast(EmsAuthenticationToken.class)
									.map(EmsAuthenticationToken::getCredentials)
									.cast(String.class)
									.flatMap(userId -> expenseService.getAllExpenses(new ObjectId(userId), limit, page));

	}

	public Mono<ServerResponse> getAllExpenseInMonth(ServerRequest request) {
		final int month = Integer.parseInt(request.queryParam("month").orElse(getCurrentMonth()));
		final int year = Integer.parseInt(request.queryParam("year").orElse(getCurrentYear()));
		return request.principal().cast(EmsAuthenticationToken.class)
						.map(EmsAuthenticationToken::getCredentials)
						.cast(String.class)
						.flatMap(userId -> expenseService.getAllExpenseInMonth(new ObjectId(userId), month, year));
	}

	public Mono<ServerResponse> getExpense(ServerRequest request) {
		final String expenseId = request.pathVariable("id");
		return expenseService.getExpense(new ObjectId(expenseId));
	}

	public Mono<ServerResponse> getAllExpensesInMonthByDay(ServerRequest request) {
		final int month = Integer.parseInt(request.queryParam("month").orElse(getCurrentMonth()));
		final int year = Integer.parseInt(request.queryParam("year").orElse(getCurrentYear()));
		return request.principal().cast(EmsAuthenticationToken.class)
						.map(EmsAuthenticationToken::getCredentials)
						.cast(String.class)
						.flatMap(userId -> expenseService.getAllExpenseInMonthByDay(new ObjectId(userId), month, year));
	}


	private String getCurrentMonth() {
		return Integer.toString(LocalDate.now().getMonthValue());
	}

	private String getCurrentYear() {
		return Integer.toString(LocalDate.now().getYear());
	}
}
