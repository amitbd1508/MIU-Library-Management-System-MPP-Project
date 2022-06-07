package datapersistant;

import java.util.HashMap;

import model.Book;
import model.Member;
import model.User;

public interface DataAccess {
	public HashMap<String, Book> readBooksMap();

	public HashMap<String, User> readUserMap();

	public HashMap<String, Member> readMemberMap();

	public void saveNewMember(Member member);

	public void saveNewBook(Book book);
}
