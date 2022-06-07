package service;

import java.util.ArrayList;
import java.util.List;

import common.AppContext;
import dao.impl.BookDao;
import dao.impl.MemberDao;
import model.Author;
import model.Book;
import model.BookCopy;

public class BookService {

	private static BookDao bookDao;
	private static MemberDao memberDao;

	public BookService() {
		bookDao = new BookDao();
		memberDao = new MemberDao();

	}

	public BookCopy getAvailableCopy(Book book) {

		if (book != null) {
			for (BookCopy bc : book.getCopies()) {
				if (bc.isAvailable()) {
					return bc;
				}
			}
		}

		return null;
	}

	public Book getBookById(String isbn) {

		return bookDao.get(isbn);
	}

	public List<Book> getAllBook() {
		return bookDao.getAll();
	}

	public List<Author> getAllauthor(){
		List<Book> books = bookDao.getAll();
		List<Author> authors = new ArrayList<Author>();
		for(Book b: books) {
			authors.addAll(b.getAuthors());
			AppContext.author.addAll(b.getAuthors());
		}
		
		return AppContext.author.stream().toList();
	}

	public Book createNewBook(String title, String isbnText, int checkoutLengthText, List<String> authorNames,
			int numCopies) {
		List<Author> authors = new ArrayList<Author>();
		var ort = AppContext.author;
		var targetAutor = ort.stream().filter(au-> authorNames.stream().anyMatch(x-> x.equals(au.getFullName())));
		Book newBook = bookDao.createNewBook(title, isbnText, checkoutLengthText, authors);
		
		for(int i=0;i< numCopies-1;i++) {
			newBook.addCopy();
		}
		
		bookDao.save(newBook);
		return newBook;
	}
	

	public void addBookCopy(String isbn, int numberOfCopies) {
		Book book = getBookById(isbn);
		for (int i = 0; i < numberOfCopies; i++) book.addCopy();
		try {
			bookDao.save(book);
		}catch (Exception e) {
			System.out.println("ERROR WHILE TRYING TO UPDATE NUMBER OF COPIES: " + e.getMessage());
		}
	}
	public boolean addNewAuthor(String fName, String lName, String phNum, String street, String city, String state,
			String zip, String bio) {
		Author au = bookDao.getNewAuthor(fName,lName,phNum,street,city, state, zip, bio);
		int oldsize = AppContext.author.size();
		AppContext.author.add(au);
		int newSize = AppContext.author.size();
		return newSize > oldsize;
	}
}
