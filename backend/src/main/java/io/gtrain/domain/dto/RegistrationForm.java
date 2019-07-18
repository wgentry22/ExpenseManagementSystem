package io.gtrain.domain.dto;

import io.gtrain.domain.model.AccountType;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author William Gentry
 */
public class RegistrationForm {

	private String username;

	private String email;

	private String password;

	private String firstname;

	private String lastname;

	private String street;

	private String city;

	private String state;

	private String zipCode;

	private double balance;

	private AccountType type;

	private String accountName;

	private double monthlyDeposits;

	public RegistrationForm() {}

	public RegistrationForm(String username, String email, String password, String firstname, String lastname, String street, String city, String state, String zipCode, double balance, AccountType type, String accountName, double monthlyDeposits) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.balance = balance;
		this.type = type;
		this.accountName = accountName;
		this.monthlyDeposits = monthlyDeposits;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public double getMonthlyDeposits() {
		return monthlyDeposits;
	}

	public void setMonthlyDeposits(double monthlyDeposits) {
		this.monthlyDeposits = monthlyDeposits;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RegistrationForm that = (RegistrationForm) o;
		return Double.compare(that.balance, balance) == 0 &&
						Double.compare(that.monthlyDeposits, monthlyDeposits) == 0 &&
						Objects.equals(username, that.username) &&
						Objects.equals(email, that.email) &&
						Objects.equals(password, that.password) &&
						Objects.equals(firstname, that.firstname) &&
						Objects.equals(lastname, that.lastname) &&
						Objects.equals(street, that.street) &&
						Objects.equals(city, that.city) &&
						Objects.equals(state, that.state) &&
						Objects.equals(zipCode, that.zipCode) &&
						type == that.type &&
						Objects.equals(accountName, that.accountName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, email, password, firstname, lastname, street, city, state, zipCode, balance, type, accountName, monthlyDeposits);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", RegistrationForm.class.getSimpleName() + "[", "]")
						.add("username='" + username + "'")
						.add("email='" + email + "'")
						.add("password='" + password + "'")
						.add("firstname='" + firstname + "'")
						.add("lastname='" + lastname + "'")
						.add("street='" + street + "'")
						.add("city='" + city + "'")
						.add("state='" + state + "'")
						.add("zipCode='" + zipCode + "'")
						.add("balance=" + balance)
						.add("type=" + type)
						.add("accountName='" + accountName + "'")
						.add("monthlyDeposits=" + monthlyDeposits)
						.toString();
	}
}
