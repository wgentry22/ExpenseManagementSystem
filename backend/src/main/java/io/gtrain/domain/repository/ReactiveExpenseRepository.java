package io.gtrain.domain.repository;

import io.gtrain.domain.model.Expense;
import io.gtrain.domain.model.ExpenseType;
import io.gtrain.domain.repository.interfaces.ExpenseRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * @author William Gentry
 */
@Repository
public class ReactiveExpenseRepository implements ExpenseRepository {

	private final ReactiveMongoTemplate mongoTemplate;
	private final ProjectionOperation dateProjection = Aggregation.project("id", "userId", "location", "amount", "expenseType", "date", "created_date")
																																.and("date").extractMonth().as("target_month")
																																.and("date").extractYear().as("target_year");

	public ReactiveExpenseRepository(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<String> save(Expense expense) {
		return mongoTemplate.save(expense).map(e -> e.getId().toHexString());
	}

	@Override
	public Flux<Expense> getAll(ObjectId userId, int limit, long page) {
		return mongoTemplate.aggregate(getExpenseAggregation(userId, limit, page), "expense", Expense.class);
	}

	@Override
	public Mono<Expense> getOne(ObjectId expenseId) {
		return mongoTemplate.findById(expenseId, Expense.class);
	}

	@Override
	public Mono<Map<ExpenseType, List<Expense>>> getAllSince(ObjectId userId, int month, int year) {
		final Map<ExpenseType, List<Expense>> expenseTypeListMap = getBaseMap();
		return mongoTemplate.aggregate(getExpenseInMonthAggregation(userId, month, year), "expense", Expense.class).collectList().flatMap(expenses -> {
			Stream<Expense> expenseStream = expenses.stream();
			expenseStream.forEach(expense -> expenseTypeListMap.get(expense.getExpenseType()).add(expense));
			return Mono.just(expenseTypeListMap);
		});
	}


	// I know, right. What a data structure
	// Used in particular for one chart on the client side, requires a specific format
	@Override
	public Mono<Map<DayOfWeek, Map<ExpenseType, List<Expense>>>> getAllSinceByDay(ObjectId userId, int month, int year) {
		final Map<DayOfWeek, Map<ExpenseType, List<Expense>>> dayOfWeekListMap = getByWeekMap();
		return mongoTemplate.aggregate(getExpenseInMonthAggregation(userId, month, year), "expense", Expense.class).collectList().flatMap(expenses -> {
			Stream<Expense> expenseStream = expenses.stream();
			expenseStream.forEach(expense -> dayOfWeekListMap.get(expense.getDate().getDayOfWeek()).get(expense.getExpenseType()).add(expense));
			return Mono.just(dayOfWeekListMap);
		});

	}

	private Aggregation getExpenseAggregation(ObjectId userId, int limit, long page) {
		return Aggregation.newAggregation(match(Criteria.where("userId").is(userId)), sort(Sort.Direction.ASC, "created_date"), skip(limit * page), limit(limit));
	}

	private Aggregation getExpenseInMonthAggregation (ObjectId userId, int month, int year) {
		return Aggregation.newAggregation(dateProjection, match(Criteria.where("userId").is(userId).and("target_month").is(month).and("target_year").is(year)));
	}

	private Map<ExpenseType, List<Expense>> getBaseMap() {
		Map<ExpenseType, List<Expense>> map = new TreeMap<>();
		Arrays.stream(ExpenseType.values()).forEach(expenseType -> map.put(expenseType, new ArrayList<>()));
		return map;
	}

	private Map<DayOfWeek, Map<ExpenseType, List<Expense>>> getByWeekMap() {
		Map<DayOfWeek, Map<ExpenseType, List<Expense>>> map = new LinkedHashMap<>();
		Arrays.stream(DayOfWeek.values()).forEach(day -> {
			map.put(day, getAllExpenseTypeMaps());
		});
		return map;
	}

	private Map<ExpenseType, List<Expense>> getAllExpenseTypeMaps() {
		Map<ExpenseType, List<Expense>> map = new HashMap<>();
		Arrays.stream(ExpenseType.values()).forEach(type -> {
			map.put(type, new ArrayList<>());
		});
		return map;
	}
}
