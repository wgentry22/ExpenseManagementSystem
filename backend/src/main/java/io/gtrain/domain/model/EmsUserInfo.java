package io.gtrain.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * @author William Gentry
 */
@Document(collection = "user_info")
public class EmsUserInfo {

	@JsonIgnore
	@Id
	private ObjectId id;

	@JsonIgnore
	private final ObjectId userId;

	private final Name name;

	private final Address address;

	private List<Account> accounts;

	public EmsUserInfo(ObjectId userId, Name name, Address address) {
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.accounts = new ArrayList<>();
	}

	@PersistenceConstructor
	public EmsUserInfo(ObjectId userId, Name name, Address address, List<Account> accounts) {
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.accounts = accounts;
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

	public Name getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmsUserInfo that = (EmsUserInfo) o;
		return Objects.equals(id, that.id) &&
						Objects.equals(userId, that.userId) &&
						Objects.equals(name, that.name) &&
						Objects.equals(address, that.address) &&
						Objects.equals(accounts, that.accounts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userId, name, address, accounts);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", EmsUserInfo.class.getSimpleName() + "[", "]")
						.add("id=" + id)
						.add("userId=" + userId)
						.add("name=" + name)
						.add("address=" + address)
						.add("accounts=" + accounts)
						.toString();
	}
}
