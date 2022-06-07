package model;

import java.io.Serializable;
import java.util.UUID;


final public class Member extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord record;
	
	public Member(String fname, String lname, String tel, Address add) {
		super(fname,lname, tel, add);
		this.memberId = UUID.randomUUID().toString();
		this.record = new CheckoutRecord();
	}
	
	public Member(String fname, String lname) {
		super(fname, lname);
		this.memberId = UUID.randomUUID().toString();
	}
	
	public void setRecord(CheckoutRecord record) {
		this.record = record;
	}
	
	public CheckoutRecord getRecord() {
		return record;
	}

	public String getMemberId() {
		return memberId;
	}
	
	@Override
	public String toString() {
		return "ID: " + memberId + "\nname: " + getFirstName() + "\nlast name: " + getLastName() + 
				"\nphone number: " + getPhone() + "\naddress: " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
