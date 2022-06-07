package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{
	private static final long serialVersionUID = 1L;
    private Member libraryMember;
    private List<CheckoutEntry> entries = new ArrayList<CheckoutEntry>();

    void setLibraryMember(Member libraryMember) {
        this.libraryMember = libraryMember;
    }

    public void add(BookCopy book) {
        this.entries.add(new CheckoutEntry(book));
    }

    @Override
	public String toString() {
		return "CHECKOUT RECORD \nlibraryMember\n" + libraryMember + "\n entries: " + entries;
	}

	public Member getLibraryMember() {
		return libraryMember;
	}

	public List<CheckoutEntry> getEntries() {
		return entries;
	}
}
