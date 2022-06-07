package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import common.AppContext;
import controller.BookController;
import model.Author;
import model.Book;
import ui.books.list.ListAllBooksWindow;
import utils.Util;


public class NewBookWindow extends JPanel implements ActionListener {

	BookController bookCtl = new BookController();

	private JFrame parentFrame;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;

	private JLabel titleLabel;
	private JLabel isbnLabel;
	private JLabel authorLabel;
	private JLabel checkoutLengthLabel;
	private JLabel numberOfCopiesLabel;
	private JTextField titleTextField;
	private JFormattedTextField ISBNTextField;
	private JTextField authorTextField; // consider! if there is more than one author (add Author button)
	private JComboBox checkoutLengthTextField;
	private JFormattedTextField numberOfCopiesTextField;

	private JButton submitButton;
	private JButton addAuthorButton;

	private JLabel successLabel;

	//    private JComboBox<String> authorComboBox;
	private JList<String> authorJList;

	private static NewBookWindow nbw;

	public NewBookWindow() {
		setSize(600, 400);
		setLayout(new BorderLayout());
		nbw = this;

		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);
		setBorder(new EmptyBorder(10, 10, 10, 10));

	}

	public static NewBookWindow getNBW()
	{return nbw;}
	public void defineTopPanel() {
		topPanel = new JPanel();
		JLabel AddBookLabel = new JLabel("Add New Book");
		Util.adjustLabelFont(AddBookLabel, Util.DARK_BLUE, true);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton backButton = new JButton("< Back");
		backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		addBackButtonListener(backButton);
		topPanel.add(backButton);
		topPanel.add(AddBookLabel);
	}

	public void defineMiddlePanel() {
		middlePanel = new JPanel();

		BoxLayout bl = new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS);
		middlePanel.setLayout(bl);

		titleLabel = new JLabel("Title");
		isbnLabel = new JLabel("Book ISBN");
		authorLabel = new JLabel("Author Information");
		checkoutLengthLabel = new JLabel("Checkout Length (Days)");
		numberOfCopiesLabel = new JLabel("Number of Copies");

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);

		String phoneMask= "##-#####";
		String phoneNumber= "124237648";

		MaskFormatter maskFormatter= null;
		try {
			maskFormatter = new MaskFormatter(phoneMask);
			maskFormatter.setValueContainsLiteralCharacters(false);
			maskFormatter.valueToString(phoneNumber) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		formatter.setValueClass(Integer.class);
		formatter.setMaximum(100);
		formatter.setAllowsInvalid(false);

		titleTextField = new JTextField();
		ISBNTextField = new JFormattedTextField(maskFormatter);
		authorTextField = new JTextField();

		String[] checkoutTimes = {"7","21"};
		checkoutLengthTextField = new JComboBox(checkoutTimes);

		formatter.setMaximum(100000);
		numberOfCopiesTextField = new JFormattedTextField(formatter);

		addAuthorButton = new JButton("+ Add New Author");
		addAuthorListener(addAuthorButton);
		JPanel addAuthorPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addAuthorPanel.add(addAuthorButton);

		
		
		List<String> auNames = new ArrayList<String>();
		try {
			var authors = bookCtl.getAllAuthor();
			
			for(Author aut: authors) {
				auNames.add(aut.getFullName());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] authNames = auNames.stream().toArray(String[]::new);

		authorJList = new JList<>(authNames);
		authorJList.setFixedCellHeight(15);
		authorJList.setFixedCellWidth(80);
		authorJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		middlePanel.add(titleLabel);
		middlePanel.add(titleTextField);
		middlePanel.add(isbnLabel);
		middlePanel.add(ISBNTextField);
		middlePanel.add(authorLabel);

		middlePanel.add(new JScrollPane(authorJList));
		middlePanel.add(addAuthorPanel);
		middlePanel.add(checkoutLengthLabel);
		middlePanel.add(checkoutLengthTextField);
		middlePanel.add(numberOfCopiesLabel);
		middlePanel.add(numberOfCopiesTextField);


	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}


	private void addButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String title = titleTextField.getText();
			List<String> authorNames = new ArrayList<>();
			String checkoutInput = (String) checkoutLengthTextField.getSelectedItem();
			String copiesInput = numberOfCopiesTextField.getText();
			String isbnText =  ISBNTextField.getText();

			for (Object name: authorJList.getSelectedValues()){
				String s = (String) name;
				authorNames.add(s);
				System.out.println(s);
			}

			boolean arefilled = checkBookValues(authorNames, title, isbnText, checkoutInput, copiesInput);

			if(arefilled) {
				int checkoutLengthText = Integer.parseInt(checkoutInput);
				int numCopies = Integer.parseInt(copiesInput);

				Book addedBook = bookCtl.addBook(title, isbnText, checkoutLengthText, authorNames, numCopies);
			

				JOptionPane.showMessageDialog(parentFrame,"Book successfully added", "Message",  JOptionPane.INFORMATION_MESSAGE);
				ListAllBooksWindow.notifyTableChanged(addedBook);

				clearFields();

			}
			else{
				JOptionPane.showMessageDialog(parentFrame, "Please insert all values", "Error",  JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	public void updateAuthorList ()
	{
		List<String> auNames = new ArrayList<String>();
		try {
			var authors = bookCtl.getAllAuthor();
			
			for(Author aut: authors) {
				auNames.add(aut.getFullName());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(AppContext.author);
		System.out.println(auNames);
		
		String[] authNames = auNames.stream().toArray(String[]::new);
		authorJList.setListData(authNames);
	}
	private void addAuthorListener(JButton butn) {
		butn.addActionListener(evt -> {
			JFrame frame = new JFrame("Add New Author");

			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			AddNewAuthorWindow anaw = new AddNewAuthorWindow(true);
			frame.getContentPane().add(anaw, BorderLayout.CENTER);
			frame.pack();
			frame.setVisible(true);

		});
	}


	boolean checkBookValues(List<String> names, String ... inputs)
	{
		for (String s: inputs) {
			if (s.isEmpty())
				return false;
		}

		if (names.size() == 0) return false;
		return true;
	}

	void clearFields()
	{
		titleTextField.setText("");
		ISBNTextField.setText("");
		numberOfCopiesTextField.setValue(null);
		authorJList.clearSelection();
	}

	public void defineLowerPanel() {
		lowerPanel = new JPanel();

		BoxLayout bl = new BoxLayout(lowerPanel, BoxLayout.Y_AXIS);
		lowerPanel.setLayout(bl);

		JPanel buttonPanel = new JPanel();
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
		buttonPanel.setLayout(fl);

		submitButton = new JButton("ADD");
		addButtonListener(submitButton);
		buttonPanel.add(submitButton);


		lowerPanel.add(buttonPanel);

	}

	public void setParentJFrame(JFrame parent) {
		this.parentFrame = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
