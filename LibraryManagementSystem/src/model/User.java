package model;

import java.io.Serializable;

import common.Role;

final public class User extends Person implements Serializable {

	private static final long serialVersionUID = 5147265048973262104L;

	private String id;
	private Credintial crd;
	private Role userRole;

	public User(String id, String username, String pass, String f, String l, String p, Role r, Address a) {
		super(f, l, p, a);
		this.id = id;
		this.userRole = r;
		this.crd = new Credintial(username, pass);
	}

	public Role getUserRole() {
		return userRole;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}

	public Credintial getCrd() {
		return crd;
	}

}
