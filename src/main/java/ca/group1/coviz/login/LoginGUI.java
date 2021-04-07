
package ca.group1.coviz.login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ca.group1.coviz.gui.CovizGUI;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.JPasswordField;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JLabel errorText = new JLabel("Invalid Credentials!");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LoginGUI frame = new LoginGUI();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(10, 21, 69, 14);
		contentPane.add(lblUsername);

		txtUsername = new JTextField();
		txtUsername.setBounds(89, 18, 185, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 46, 69, 14);
		contentPane.add(lblPassword);

		JButton btnSubmit = new JButton("Submit!");


		// Action listener for Submit Button
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// get username from username field
				String username = txtUsername.getText();
				// Get password from password field
				String password = txtPassword.getText();

				// JSON parser Object to parse JSON File
				try {
					// Parsing JSON file to JSON array
					JSONArray usersList = new JSONArray(String.join("\n", Files.readAllLines(Paths.get("users.json"))));
					// Iterating each JSON Object of array
					for(Object obj : usersList) {
						JSONObject user = (JSONObject) obj;
						// Validating Login
						if(validateLogin(user, username, password)) {
							// redirect to Main GUI
							JFrame frame = new CovizGUI("COVIZ");
					        frame.setVisible(true);
					        // disposing Login Frame
					        dispose();
						}
						else{
                            showMessageDialog(null, "Invalid Credentials");

                        }
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSubmit.setBounds(100, 74, 89, 23);
		contentPane.add(btnSubmit);
		errorText.setForeground(Color.RED);


		txtPassword = new JPasswordField();
		txtPassword.setBounds(89, 49, 185, 20);
		contentPane.add(txtPassword);
        contentPane.add(errorText);
	}

    /**
     * this method validates the login
     * @param user
     * @param username
     * @param password
     * @return true if the username and password are correct, false otherwise
     */
	private boolean validateLogin(JSONObject user, String username, String password) {
		// User details from JSON file
		JSONObject userObject = (JSONObject) user.get("user");
		// validating username and password in file
		if(userObject.get("username").equals(username) && userObject.get("password").equals(password)) {
			// returns true of matched
			return true;
		}
		// returns false if not matched
		return false;
	}

}
