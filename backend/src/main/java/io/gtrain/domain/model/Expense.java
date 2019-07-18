package io.gtrain.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gtrain.domain.json.ExpenseDeserializer;
import io.gtrain.domain.json.ExpenseSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author William Gentry
 */
@JsonDeserialize(using = ExpenseDeserializer.class)
@JsonSerialize(using = ExpenseSerializer.class)
@Document
public class Expense {

	@Id
	private ObjectId id;

	private ObjectId userId;

	private final double amount;

	private final LocalDateTime date;

	private final String location;

	private final ExpenseType expenseType;

	@Field("created_date")
	private Instant createdDate = Instant.now();

	public Expense(double amount, LocalDateTime date, String location, ExpenseType expenseType) {
		this.amount = amount;
		this.date = date;
		this.location = location;
		this.expenseType = expenseType;
	}

	@PersistenceConstructor
	public Expense(ObjectId userId, double amount, LocalDateTime date, String location, ExpenseType expenseType, Instant createdDate) {
		this.userId = userId;
		this.amount = amount;
		this.date = date;
		this.location = location;
		this.expenseType = expenseType;
		this.createdDate = createdDate;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Expense expense = (Expense) o;
		return Double.compare(expense.amount, amount) == 0 &&
						date == expense.date &&
						Objects.equals(id, expense.id) &&
						Objects.equals(userId, expense.userId) &&
						Objects.equals(location, expense.location) &&
						expenseType == expense.expenseType &&
						Objects.equals(createdDate, expense.createdDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userId, amount, date, location, expenseType, createdDate);
	}
}
