package dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import dao.Dao;
import datapersistant.DataAccessFacade;
import datapersistant.DataPersistantFacade;
import model.Address;
import model.Author;
import model.Book;

public class BookDao implements Dao<Book> {

	private static DataAccessFacade accessFacade;
	public static DataPersistantFacade persistantFacade;

	public BookDao() {
		persistantFacade = new DataPersistantFacade();
		accessFacade = new DataAccessFacade();
	}

	@SuppressWarnings("static-access")
	@Override
	public Book get(String isbn) {
		return accessFacade.getBook(isbn);
	}

	@Override

	public List<Book> getAll() {
		// TODO Auto-generated method stub
		return accessFacade.getAllBook();

	}

	@Override
	public void save(Book t) {
		persistantFacade.saveNewBook(t);

	}

	@Override
	public void update(Book t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Book t) {
		// TODO Auto-generated method stub

	}
	
	public Book createNewBook(String title, String isbnText, int checkoutLengthText, List<Author> authors) {
		return new Book(isbnText, title,checkoutLengthText, authors);
	}
	
	public Author getNewAuthor(String fName, String lName, String phNum, String street, String city, String state,
			String zip, String bio) {
		var address = new Address(street, city,state,zip);
		return new Author(fName, lName,phNum, address, bio, "Crd");
		
	}

}

	
