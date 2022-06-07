package model;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 3665880920647848288L;
	private String firstName;
	private String lastName;
	private String phone;
	private Address address;
	public Person(String f, String l, String t, Address a) {
		firstName = f;
		lastName = l;
		phone = t;
		address = a;
	}
	public Person(String f, String l) {
		firstName = f;
		lastName = l;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFullName() {
		return firstName + " " + lastName;
	}
	public String getPhone() {
		return phone;
	}
	public Address getAddress() {
		return address;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
