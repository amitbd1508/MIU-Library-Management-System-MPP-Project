package datapersistant;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import common.StorageType;
import model.Book;
import model.Member;
import model.User;

public class DataPersistantFacade {
	
	public static final String OUTPUT_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "dataaccess" + File.separator + "storage";
	
     /////load methods - these place test data into the storage area
	///// - used just once at startup
	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getCrd().getUsername(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<Member> memberList) {
		HashMap<String, Member> members = new HashMap<String, Member>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	public void saveNewMember(Member member) {
		HashMap<String, Member> mems = DataAccessFacade.readMemberMap();
		String memberId = member.getMemberId();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	public void saveNewBook(Book book) {
		HashMap<String, Book> bookMap = DataAccessFacade.readBooksMap();
		String isbn = book.getIsbn();
		bookMap.put(isbn, book);
		saveToStorage(StorageType.BOOKS, bookMap);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
