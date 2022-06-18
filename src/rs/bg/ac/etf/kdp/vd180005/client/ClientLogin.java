package rs.bg.ac.etf.kdp.vd180005.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class ClientLogin {

	private JFrame frmLogIn;
	private JTextField txtusername;
	private JTextField textPassword;
	private JLabel lblNewLabel_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientLogin window = new ClientLogin();
					window.frmLogIn.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private ArrayList<String> users=new ArrayList<String>();
	/**
	 * Create the application.
	 */
	private void readData() {
	
			Scanner scanner;
			int i = 0;
			try {
				scanner = new Scanner(new File("users.csv"));
				while (scanner.hasNextLine()) {
					String values = scanner.nextLine();
					users.add(values);
				//	count++;
				}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	public ClientLogin() {
		initialize();
		readData();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogIn = new JFrame();
		frmLogIn.setTitle("Log In");
		frmLogIn.setBounds(100, 100, 450, 300);
		frmLogIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogIn.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(75, 108, 104, 13);
		frmLogIn.getContentPane().add(lblNewLabel);
		
		txtusername = new JTextField();
		txtusername.setBounds(157, 105, 96, 19);
		frmLogIn.getContentPane().add(txtusername);
		txtusername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setBounds(157, 140, 96, 19);
		frmLogIn.getContentPane().add(textPassword);
		textPassword.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(76, 143, 74, 13);
		frmLogIn.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(170, 189, 202, 45);
		frmLogIn.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user=txtusername.getText()+","+textPassword.getText();
				if(users.contains(user)) {
					new ClientGUI().setVisible(true);
					frmLogIn.setVisible(false);
				}
				else {
					lblNewLabel_2.setText("Korisnik ne postoji");
				}
			}
		});
		btnNewButton.setBounds(54, 201, 85, 21);
		frmLogIn.getContentPane().add(btnNewButton);
	}
}
