package io.gtrain.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gtrain.domain.json.AccountDeserializer;
import io.gtrain.domain.json.AccountSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
@JsonSerialize(using = AccountSerializer.class)
@JsonDeserialize(using = AccountDeserializer.class)
public class Account {

	private ObjectId userId;

	private final double balance;

	private final AccountType type;

	private final String name;

	private final double monthlyDeposits;

	public Account(double balance, AccountType type, String name, double monthlyDeposits) {
		this.balance = balance;
		this.type = type;
		this.name = name;
		this.monthlyDeposits = monthlyDeposits;
	}

	@PersistenceConstructor
	public Account(ObjectId userId, double balance, AccountType type, String name, double monthlyDeposits) {
		this.userId = userId;
		this.balance = balance;
		this.type = type;
		this.name = name;
		this.monthlyDeposits = monthlyDeposits;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public double getBalance() {
		return balance;
	}

	public AccountType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public double getMonthlyDeposits() {
		return monthlyDeposits;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Account account = (Account) o;
		return Double.compare(account.balance, balance) == 0 &&
						Double.compare(account.monthlyDeposits, monthlyDeposits) == 0 &&
						Objects.equals(userId, account.userId) &&
						type == account.type &&
						Objects.equals(name, account.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, balance, type, name, monthlyDeposits);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
						.add("userId=" + userId)
						.add("balance=" + balance)
						.add("type=" + type)
						.add("name='" + name + "'")
						.add("monthlyDeposits=" + monthlyDeposits)
						.toString();
	}
}
