package datapersistant;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import common.StorageType;
import model.Book;
import model.BookCopy;
import model.Member;
import model.User;

public class DataAccessFacade {

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "dataaccess" + File.separator + "storage";

	public static final String DATE_PATTERN = "MM/dd/yyyy";

	@SuppressWarnings("unchecked")
	public static HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, Member> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, Member>) readFromStorage(StorageType.MEMBERS);
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	@SuppressWarnings("unchecked")
	public static User getUser(String username, String pass) {
		try {
			HashMap<String, User> users = (HashMap<String, User>) readFromStorage(StorageType.USERS);
			if (users.containsKey(username)) {
				var u = users.get(username);
				if (u.getCrd().getPassword().equals(pass)) {
					return u;
				}
			}

		} catch (Exception e) {
			return null;
		}

		return null;

	}
	
	public static Member getMember(String memberId) {
		HashMap<String, Member> members = (HashMap<String, Member>) readFromStorage(StorageType.MEMBERS);
		if (members.containsKey(memberId)) {
		   return members.get(memberId);
		}
		return null;
	}
	
	public static Book getBook(String isbn) {
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		if (books.containsKey(isbn)) {
		   return books.get(isbn);
		}
		return null;
	}
	
	public BookCopy getBookCopy(String isbn) {
		
		HashMap<String, Book> books = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		for(Book b : books.values()) {
			//System.out.println(b.getTitle() + "___"+ b.getIsbn()+"___"+b.getMaxCheckoutLength()+"___"+b.getCopies().length);
			if(b.getIsbn()!=null && b.getIsbn().equals(isbn.trim())) {
				for(BookCopy bc : b.getCopies()) {
					if(bc.isAvailable()) {
						return bc;
					}
				}
			}
		}
		
		return null;
	}

	public static List<Book> getAllBook() {
		HashMap<String, Book> allBookMap = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
		List<Book> books = allBookMap.values()
                .stream()
               .collect(Collectors.toList());
		return books;
	}
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

}
