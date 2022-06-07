package controller;

import java.util.List;

import common.AppContext;
import common.Role;
import model.Author;
import model.Book;
import model.BookCopy;
import service.BookService;

public class BookController {
	private BookService bookSvc;
	
	public BookController() {
		this.bookSvc = new BookService();
	}
	
	public List<Book> getAllBook(){
		return this.bookSvc.getAllBook();
	}
	
	public List<Author> getAllAuthor() throws Exception{
		Role role = AppContext.loginUser.getUserRole();
			return this.bookSvc.getAllauthor();
		
		
	}

	public Book addBook(String title, String isbnText, int checkoutLengthText, List<String> authorNames,
			int numCopies) {
		Book b= this.bookSvc.createNewBook( title,  isbnText,  checkoutLengthText,  authorNames,
				 numCopies);
		return b;
	}

	public boolean addNewAuthor(String fName, String lName, String phNum, String street, String city, String state,
			String zip, String bio) {
		return this.bookSvc.addNewAuthor(fName, lName, phNum, street, city, state, zip, bio);
		
	}
	
	public void addBookCopy(String isbn, int numberOfCopies) {
		bookSvc.addBookCopy(isbn, numberOfCopies);
	}
	
	public Book getBookById(String isbn) {

		return bookSvc.getBookById(isbn);
	}
	
	public BookCopy[] getBookCopies(String isbn) {

		return bookSvc.getBookById(isbn).getCopies();
	}
	
	public BookCopy getAvailableCopy(Book foundBook) {
		// TODO Auto-generated method stub
		return this.bookSvc.getAvailableCopy(foundBook);
	} 

}
