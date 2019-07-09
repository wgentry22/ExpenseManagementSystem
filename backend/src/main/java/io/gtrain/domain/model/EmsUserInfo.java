package io.gtrain.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.StringJoiner;

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

	@PersistenceConstructor
	public EmsUserInfo(ObjectId userId, Name name, Address address) {
		this.userId = userId;
		this.name = name;
		this.address = address;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmsUserInfo that = (EmsUserInfo) o;
		return Objects.equals(id, that.id) &&
						Objects.equals(userId, that.userId) &&
						Objects.equals(name, that.name) &&
						Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userId, name, address);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", EmsUserInfo.class.getSimpleName() + "[", "]")
						.add("id=" + id)
						.add("userId=" + userId)
						.add("name=" + name)
						.add("address=" + address)
						.toString();
	}
}
