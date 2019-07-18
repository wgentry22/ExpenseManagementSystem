package io.gtrain.domain.repository.interfaces;

import io.gtrain.domain.model.Expense;
import io.gtrain.domain.model.ExpenseType;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author William Gentry
 */
public interface ExpenseRepository {

	Mono<String> save(Expense expense);
	Flux<Expense> getAll(ObjectId userId, int limit, long page);
	Mono<Map<ExpenseType, List<Expense>>> getAllSince(ObjectId userId, int month, int year);
	Mono<Map<DayOfWeek, Map<ExpenseType, List<Expense>>>> getAllSinceByDay(ObjectId userId, int month, int year);
	Mono<Expense> getOne(ObjectId expenseId);
}
