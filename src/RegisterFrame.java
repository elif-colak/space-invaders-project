import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class RegisterFrame extends JFrame implements ActionListener{
	private ImageIcon registerIcon;
	private JLabel username;
	private JLabel password;
	private JPanel registerPanel;
	private JPasswordField passwordTextField;
	private JTextField usernameTextField;
	private JButton submit;
	private  boolean isExisting;

	public RegisterFrame() {
		
		registerIcon = new ImageIcon(getClass().getResource("gameicon.png"));
		setIconImage(registerIcon.getImage());
		
		setLayout(null);
		
		getContentPane().setBackground(Color.decode("#800080"));
		
		username = new JLabel("Username:    ");
		username.setFont(new Font("SansSerif", Font.BOLD, 15));
		username.setForeground(Color.WHITE);
		password = new JLabel("Password:     ");
		password.setFont(new Font("SansSerif", Font.BOLD, 15));
		password.setForeground(Color.WHITE);
		usernameTextField = new JTextField(15);
		passwordTextField = new JPasswordField(15);
		
		registerPanel = new JPanel();
		registerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		registerPanel.add(username);
		registerPanel.add(usernameTextField);
		registerPanel.add(password);	
		registerPanel.add(passwordTextField);
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		submit.setPreferredSize(new Dimension(270, 30));
		submit.setBackground(Color.decode("#4CBB17"));
		registerPanel.add(submit);
		registerPanel.setBounds(50,80,300,300);
		registerPanel.setBackground(Color.decode("#800080"));
		
		add(registerPanel);
		
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == submit) {
			 char[] pass =  passwordTextField.getPassword(); 
		     String password = new String(pass);
			 String username = usernameTextField.getText();      

		     
		     //Checking if that username already exists or not
		     try (BufferedReader bufferReader = new BufferedReader(new FileReader("users.txt"))) {
		            isExisting = false;
		            String line;
		            while ((line = bufferReader.readLine()) != null) {
		                if (line.contains("Username: " + username)) {
		                    isExisting = true;
		                    break;
		                }
		            }
		     	} catch (IOException e1) {
		     		System.out.println("Error while reading.");
	                e1.printStackTrace();
	            } 
		     
		     
		     if(isExisting) {
		    	  if(username.isEmpty() || password.isEmpty() ) {
				    	 JOptionPane.showMessageDialog(null, "One of the fields is empty. Please register carefully."); //Error to inform people
				     }
				     else {
				    	 JOptionPane.showMessageDialog(null, "There is an exsisting user with that name. \nPlease select another username."); //Error to inform people
				     }
		     }   
		      
		     
		     //Not appending if one of the fields are empty.
		     if(username.isEmpty() == false && password.isEmpty() == false) {
		    	//Appending username and password if that username don't exists.
			     if(isExisting == false) {
			         try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter("users.txt", true))) {
			        	    //If username or password fiels is null not appending to the file.
			        	 		bufferWriter.append("Username: " + username + " ");
					            bufferWriter.append("Password: " + password);
					            bufferWriter.newLine();
				            
				        } catch (IOException e1) {
				            System.out.println("error while opening.");
				            e1.printStackTrace();
				        }
			         setVisible(false);
			     }	
		     }

	     	     
		}
		
	}
	
}
