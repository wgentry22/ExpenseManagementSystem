package io.gtrain.service;

import io.gtrain.domain.model.Expense;
import io.gtrain.domain.model.ExpenseType;
import io.gtrain.domain.repository.interfaces.ExpenseRepository;
import org.bson.types.ObjectId;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

/**
 * @author William Gentry
 */
@Service
public class ExpenseService {

	private final ExpenseRepository expenseRepository;

	public ExpenseService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public Mono<ServerResponse> createExpense(Expense expense) {
		return expenseRepository.save(expense)
						.flatMap(expenseId -> ServerResponse.created(URI.create("/api/v1/expense/" + expenseId)).build())
						.switchIfEmpty(ServerResponse.unprocessableEntity().build());
	}

	public Mono<ServerResponse> getAllExpenses(ObjectId userId, int limit, long page) {
		final Flux<Expense> expenseFlux = expenseRepository.getAll(userId, limit, page);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(expenseFlux, Expense.class);
	}

	public Mono<ServerResponse> getAllExpenseInMonth(ObjectId userId, int month, int year) {
		final Mono<Map<ExpenseType, List<Expense>>> expenseFlux = expenseRepository.getAllSince(userId, month, year);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(expenseFlux, new ParameterizedTypeReference<Map<ExpenseType, List<Expense>>>() {});
	}

	public Mono<ServerResponse> getAllExpenseInMonthByDay(ObjectId userId, int month, int year) {
		final Mono<Map<DayOfWeek, Map<ExpenseType, List<Expense>>>> expenseMono = expenseRepository.getAllSinceByDay(userId, month, year);
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(expenseMono, new ParameterizedTypeReference<Map<DayOfWeek, Map<ExpenseType, List<Expense>>>>() {});
	}

	public Mono<ServerResponse> getExpense(ObjectId expenseId) {
		return expenseRepository.getOne(expenseId)
						.flatMap(expense -> ServerResponse.ok().body(Mono.just(expense), Expense.class))
						.switchIfEmpty(ServerResponse.notFound().build());
	}
}
