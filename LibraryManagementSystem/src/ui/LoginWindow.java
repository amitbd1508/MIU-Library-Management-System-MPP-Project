package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.AuthController;
import utils.Util;

/**
 * 
 * @author amit
 */

public class LoginWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;

	public static final LoginWindow INSTANCE = new LoginWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	//private JPanel lowerHalf;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;

	private JTextField userid;
	private JTextField password;
	//private JLabel label;
	private JButton btnSubmit;

	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();
	public void clear() {
		messageBar.setText("");
	}

	/* This class is a singleton */
	private LoginWindow () {}

	public void init() {     		
		mainPanel = new JPanel();
		defineUpperHalf();
		defineMiddleHalf();
		//defineLowerHalf();
		BorderLayout bl = new BorderLayout();
		bl.setVgap(30);
		mainPanel.setLayout(bl);

		mainPanel.add(upperHalf, BorderLayout.NORTH);
		mainPanel.add(middleHalf, BorderLayout.CENTER);
		//mainPanel.add(lowerHalf, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);
		isInitialized(true);
		pack();
		Color color = Color.decode("#1DE9B6");
		mainPanel.setBackground(color);
		middlePanel.setBackground(color);
		upperHalf.setBackground(color);
		middleHalf.setBackground(color);

		rightTextPanel.setBackground(color);
		topPanel.setBackground(color);
		lowerPanel.setBackground(color);

		leftTextPanel.setBackground(color);



		lowerPanel.setBackground(color);

		setSize(500, 300);
	}

	private void defineUpperHalf() {

		upperHalf = new JPanel();
		upperHalf.setLayout(new BorderLayout());
		defineTopPanel();
		defineMiddlePanel();
		defineLowerPanel();
		upperHalf.add(topPanel, BorderLayout.NORTH);
		upperHalf.add(middlePanel, BorderLayout.CENTER);
		upperHalf.add(lowerPanel, BorderLayout.SOUTH);

	}
	private void defineMiddleHalf() {
		middleHalf = new JPanel();
		middleHalf.setLayout(new BorderLayout());
		JSeparator s = new JSeparator();
		s.setOrientation(SwingConstants.HORIZONTAL);
		middleHalf.add(s, BorderLayout.SOUTH);

	}

	private void defineTopPanel() {
		topPanel = new JPanel();
		JPanel intPanel = new JPanel(new BorderLayout());
		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
		JLabel logoLabel = new JLabel(new ImageIcon(System.getProperty("user.dir")+"/src/assets/logo.png"));
		System.out.print(new File("../assets/logo.png").isFile() + System.getProperty("user.dir"));

		
		JLabel loginLabel = new JLabel("LOGIN");
		Util.makeBigFont(loginLabel, Color.BLUE.darker());
		intPanel.add(loginLabel, BorderLayout.CENTER);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		intPanel.add(logoLabel, BorderLayout.CENTER);

		topPanel.add(intPanel);

	}

	private void defineMiddlePanel() {
		middlePanel=new JPanel();
		middlePanel.setLayout(new BorderLayout());
		deineUseridTextPanel();
		definePasswordTextPanel();
		middlePanel.add(leftTextPanel, BorderLayout.CENTER);
		middlePanel.add(rightTextPanel, BorderLayout.SOUTH);
	}
	private void defineLowerPanel() {
		lowerPanel = new JPanel();
		btnSubmit = new JButton("Login");
		addSubmitButtonListener(btnSubmit);
		lowerPanel.add(btnSubmit);
	}

	private void deineUseridTextPanel() {
		JPanel bottomText = new JPanel();
		bottomText.setLayout(new FlowLayout(FlowLayout.CENTER));		

		userid = new JTextField(15);
		new TextPrompt("Enter User ID", userid);
		bottomText.add(userid);

		leftTextPanel = new JPanel();
		leftTextPanel.setLayout(new BorderLayout());
		leftTextPanel.add(bottomText,BorderLayout.SOUTH);
	}
	private void definePasswordTextPanel() {
		JPanel bottomText = new JPanel();
		bottomText.setLayout(new FlowLayout(FlowLayout.CENTER));		

		password = new JPasswordField(15);
		new TextPrompt("Enter Password", password);
		bottomText.add(password);

		rightTextPanel = new JPanel();
		rightTextPanel.setLayout(new BorderLayout());
		rightTextPanel.add(bottomText,BorderLayout.SOUTH);
	}

	private void addSubmitButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			AuthController authCtl = new AuthController();
			String inputUserid = userid.getText();
			String inputPassword = password.getText();
			if(inputUserid.trim().length() == 0 || inputPassword.trim().length() == 0)
				JOptionPane.showMessageDialog(this,"User Id and Password must not be empty", "Message",  JOptionPane.WARNING_MESSAGE);
			else {
				
				if(authCtl.authinticate(inputUserid, inputPassword)) {
					LibrarySystem.hideAllWindows();
					JFrame frame = new SharedWindow();
					frame.setTitle("Library System");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Main.centerFrameOnDesktop(frame);
					frame.pack();
					frame.setVisible(true);
				} else  {
					JOptionPane.showMessageDialog(this, "Incorrect Username or password", "Message",  JOptionPane.ERROR_MESSAGE);

				}
			
			}
		});
	}
}
