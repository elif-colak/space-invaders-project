import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginPanel extends JPanel implements ActionListener{
	private ImageIcon alien;
	private JLabel alienLabel1;
	private JLabel alienLabel2;
	private JLabel alienLabel3;
	private JLabel alienLabel4;;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel emptyRow;
	private JPasswordField passwordTextField;
	private JTextField usernameTextField;
	private JButton loginButton;
	private JPanel loginPanel;
	private  boolean usernameAndPassFound;

	public LoginPanel() {		
		
		setLayout(null);		
		setBackground(Color.decode("#800080"));
		
		//Labels
		usernameLabel = new JLabel("Username:    ");
		usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
		usernameLabel.setForeground(Color.WHITE);
		passwordLabel = new JLabel("Password:    ");
		passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
		passwordLabel.setForeground(Color.WHITE);
		usernameTextField = new JTextField(15);
		passwordTextField = new JPasswordField(15);
		
		emptyRow = new JLabel();
		emptyRow.setPreferredSize(new Dimension(0,100));
		
		//Adding alien images
		alien = new ImageIcon(getClass().getClassLoader().getResource("gameicon.png")); 
	    alienLabel1 = new JLabel();
		alienLabel1.setIcon(alien);
		alienLabel1.setBounds(10,10,100,100);
		alienLabel2 = new JLabel();
		alienLabel2.setIcon(alien);
		alienLabel2.setBounds(770,10,100,100);
		alienLabel3 = new JLabel();
		alienLabel3.setIcon(alien);
		alienLabel3.setBounds(770,470,100,100);
		alienLabel4 = new JLabel();
		alienLabel4.setIcon(alien);
		alienLabel4.setBounds(10,470,100,100);
		add(alienLabel4);
		add(alienLabel3);
		add(alienLabel1);
		add(alienLabel2);
		
		//Login Panel
		loginPanel = new JPanel();
		loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameTextField);
		loginPanel.add(passwordLabel);	
		loginPanel.add(passwordTextField);
		
		//Login Button
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		loginButton.setPreferredSize(new Dimension(270, 40));
		loginButton.setBackground(Color.decode("#4CBB17"));
		loginPanel.setBounds(220,200,450,200);
		loginPanel.setBackground(Color.black);
		loginPanel.add(loginButton);
		loginPanel.add(emptyRow);
		
		add(loginPanel);
			
	}
	
	
	//Getting the username
	public String getUsername() {
		return usernameTextField.getText();
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == loginButton) {
			
		       
		        String searchUsername = usernameTextField.getText();
		        char[] pass =  passwordTextField.getPassword(); 
		        String searchPassword = new String(pass);

		        usernameAndPassFound = false;

		        try (BufferedReader bufferReader = new BufferedReader(new FileReader("users.txt"))) {
		            String line;
		            while ((line = bufferReader.readLine()) != null) {
		                if (line.equals("Username: " + searchUsername + " Password: " + searchPassword)) {
		                    usernameAndPassFound = true;	
		                    break;
		                }
		            }
		            
		        } catch (IOException ex) {
		        	System.out.println("Error while reading.");
		            ex.printStackTrace();
		        }

		        if (usernameAndPassFound) {
		        	setVisible(false);
					
		        } else {
		        	JOptionPane.showMessageDialog(null, "Username or password is wrong. Please try again.");
		        }
		    }
		}


		
	}

