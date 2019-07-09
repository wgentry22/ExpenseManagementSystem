package io.gtrain.domain.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
@Document
public class Account {

	@Id
	private ObjectId id;

	private final ObjectId userId;

	private final double balance;

	private final AccountType type;

	private final String name;

	@PersistenceConstructor
	public Account(ObjectId userId, double balance, AccountType type, String name) {
		this.userId = userId;
		this.balance = balance;
		this.type = type;
		this.name = name;
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

	public double getBalance() {
		return balance;
	}

	public AccountType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Account account = (Account) o;
		return Double.compare(account.balance, balance) == 0 &&
						Objects.equals(id, account.id) &&
						Objects.equals(userId, account.userId) &&
						type == account.type &&
						Objects.equals(name, account.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userId, balance, type, name);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
						.add("id=" + id)
						.add("userId=" + userId)
						.add("balance=" + balance)
						.add("type=" + type)
						.add("name='" + name + "'")
						.toString();
	}
}
