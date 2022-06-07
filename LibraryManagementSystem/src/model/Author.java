package model;

import java.io.Serializable;
import java.util.Objects;

final public class Author extends Person implements Serializable {
	private String bio;
	private String credintials;
	
	public String getBio() {
		return bio;
	}
	
	public String getCredintials() {
		return credintials;
	}
	
	public Author(String f, String l, String t, Address a, String bio, String crd) {
		super(f, l, t, a);
		this.bio = bio;
		this.credintials = crd;
	}

	private static final long serialVersionUID = 7508481940058530471L;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Author author = (Author) o;
		return getFullName().equals(author.getFullName());
	}

	@Override
	public int hashCode(){
		int hashcode = 0;
		hashcode = getAddress().getStreet().hashCode() + getFullName().hashCode();
		return hashcode;
	}


}
