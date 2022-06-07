package ui.books.list;


import javax.swing.table.AbstractTableModel;

import controller.BookController;
import model.Book;
import model.BookCopy;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

class JTableBookModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private BookController controller = new BookController();
    private List<Book> books = controller.getAllBook();
    private List<Book> rows = books;

    private String[] columns = {"ISBN", "TITLE","No of Copies" ,""};

    public String getColumnName(int column) {
        return columns[column];
    }
    
    public int getRowCount() {
        return rows.size();
    }
    
    public int getColumnCount() {
        return columns.length;
    }
    
    public Object getValueAt(int row, int column) {
        Object value = "??";
        Book book = books.get(row);
        String isbn = book.getIsbn();
        switch (column) {
            case 0:
                value = isbn;
                break;
            case 1:
                value = book.getTitle();
                break;
            case 2:
                value = controller.getBookById(isbn).getCopies().length;
                break;
            case 3:
                value = "Add new copy";
                break;
        }
        return value;
    }
    
    public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public List<Book> getRows() {
		return rows;
	}

	public void setRows(List<Book> rows) {
		this.rows = rows;
	}

	public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }
    
    
    public void addRow(Book book) {
    	books.add(book);
    	fireTableDataChanged();
    }
    
    public Book getBookByISBN(String isbn) {
    	return books.stream().filter(b -> b.getIsbn() == isbn).collect(Collectors.toList()).get(0);
    }
}
