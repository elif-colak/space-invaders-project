import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyFrame extends JFrame implements ActionListener,MouseListener {
	private ImageIcon gameIcon;
	private ImageIcon startpage;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenu help;
	private JMenuItem register;
	private JMenuItem playGame;
	private JMenuItem highScore;
	private JMenuItem quit;
	private JMenuItem about;
	private RegisterFrame registerframe;
	private JLabel startPic;
	private LoginPanel loginPanel;
	private GamePanel gamePanel;
	private HighScores highscores;
	private String userName;
	
	public MyFrame() {
		
		//Absolute Layout
		setLayout(null);
		
		getContentPane().setBackground( Color.black );
		gameIcon = new ImageIcon(getClass().getClassLoader().getResource("gameicon.png"));
		setIconImage(gameIcon.getImage());
		
		//Menu Barr
		menuBar = new JMenuBar();
		file = new JMenu("File");
		help = new JMenu("Help");
		
		//Menu Bar items
		register = new JMenuItem("Register");
		register.addActionListener(this);		
		playGame = new JMenuItem("Play Game");
		playGame.addActionListener(this);
		highScore = new JMenuItem("High Score");
		highScore.addActionListener(this);
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		about = new JMenuItem("About");
		about.addActionListener(this);
		
		menuBar.add(file);
		menuBar.add(help);
		file.add(register);
		file.add(playGame);
		file.add(highScore);
		file.add(quit);
		help.add(about);
		
		setJMenuBar(menuBar);	
		
		//Starting piture
		startPic = new JLabel();
		startpage = new ImageIcon(getClass().getClassLoader().getResource("startpage.png"));
		startPic.setIcon(startpage);
		startPic.setBounds(0,0,900,600);
		startPic.addMouseListener(this);
		startPic.setHorizontalAlignment(JLabel.CENTER);
		add(startPic);	

	}
	
	@Override
	
	public void actionPerformed(ActionEvent e) {
		
		//Quitting
		if(e.getSource() == quit) {
			System.exit(0);
		}
		
		//About
		else if(e.getSource() == about) {
			JOptionPane.showMessageDialog(null, "The Developer:\nName: Elif \nSurname: Çolak\nNumber: 20200702023 \nEmail: elif.colak@std.yeditepe.edu.tr \n");
		}
		
		//Register
		else if(e.getSource() == register) {
			
			//Creating Register Frame 
			registerframe = new RegisterFrame();
			registerframe.setSize(400,300);
			registerframe.setLocationRelativeTo(null);
			registerframe.setResizable(false);
			registerframe.setTitle("Register Page");
			registerframe.setVisible(true);
			
		}
		
		//Login
		else if(e.getSource() == playGame) {
			
			//Setting starting picture unvisible because login panel will open.
			startPic.setVisible(false);
			
			//Before starting the game user should login 
			loginPanel = new LoginPanel();
			loginPanel.setBounds(0,0,900,650);
			loginPanel.setVisible(true);
			loginPanel.setFocusable(true);
			loginPanel.addMouseListener(this);
			loginPanel.requestFocusInWindow();
			getContentPane().add(loginPanel);		
			
			
			//If users login succesfull starting picture will open and then user can press and play the game.
			if(loginPanel.isVisible()) {
				startPic.setVisible(true);
				startPic.addMouseListener(this);
				startPic.setHorizontalAlignment(JLabel.CENTER);
				startPic.setVisible(true);
				startPic.setFocusable(true);
			    startPic.requestFocusInWindow();			    
			    getContentPane().add(startPic);		    
			   
			}			
			
		}
		
	
		//HighScores 
		else if(e.getSource() == highScore) {	
			
			//Setting starting picture unvisible because login panel will open.
			startPic.setVisible(false);
			
			//Creating HighScores panel
			highscores = new HighScores();
			highscores.setBounds(0,0,900,650);
			highscores.setVisible(true);
			highscores.setFocusable(true);
			highscores.requestFocusInWindow();
			getContentPane().add(highscores);
		}	
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource() == startPic ) {
              
			//if login panel is opened then next game panel will open if an existing person login.
			if(loginPanel != null ) {			
			//Setting starting picture unvisible because game panel will open.
			startPic.setVisible(false);	
			userName = loginPanel.getUsername();
			//Creating game panel
			gamePanel = new GamePanel(userName);
			gamePanel.setBounds(0, 0, 900,650);
			gamePanel.setVisible(true);
			setContentPane(gamePanel);
			gamePanel.setFocusable(true);
	    	gamePanel.requestFocusInWindow();
			}
			
			else {
				JOptionPane.showMessageDialog(null, "You are not logged in. \nPlease login first. \nFile -> Playgame"); //Message pops up to warn user to login
			}
		
		}
		
		else if (e.getSource() == loginPanel) {
			//Empty
	    }	

			
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
