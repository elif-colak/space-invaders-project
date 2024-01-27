import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HighScores extends JPanel{
		private JLabel highscoretitle;
		private JTextArea scoresArea;
		public static int num = 1;
		private ArrayList<String> scores;
		private ImageIcon alien;
		private JLabel alienLabel1;
		private JLabel alienLabel2;
		private JLabel alienLabel3;
		private JLabel alienLabel4;
		
		public HighScores() {
			
			setLayout(null);
			
			//Highscores title
			highscoretitle = new JLabel("HIGH SCORES");
			highscoretitle.setForeground(Color.GREEN);
			highscoretitle.setFont(new Font("SansSerif", Font.BOLD, 45));
			highscoretitle.setBounds(300,20,370,60);
			add(highscoretitle);
			
			setBackground(Color.BLACK);
			
			//Highscores table
			scoresArea = new JTextArea();
			scoresArea.setBounds(200,100,500,450);
			scoresArea.setEditable(false);
			scoresArea.setBackground(Color.decode("#800080"));
			scoresArea.setForeground(Color.white);
			scoresArea.setFont(new Font("SansSerif", Font.BOLD, 25));
			add(scoresArea);
			
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
			add(alienLabel2);
			add(alienLabel1);

	        String filename = "highscores.txt"; 
	        
	        scores = new ArrayList<String>();

	        try (BufferedReader bufferReader = new BufferedReader(new FileReader(filename))) {
	            String line = null;
	            while ((line = bufferReader.readLine()) != null) {
	            	scores.add(line); //adding lines to scores list before sorting them
	            }
	            
	            
	            //Sorting scores inside the lines
	            Collections.sort(scores, new Comparator<String>() {    
					@Override
					public int compare(String a, String b) {
						int firstScore= Integer.parseInt(a.substring(a.lastIndexOf(" ") + 1));
		                int secondScore = Integer.parseInt(b.substring(b.lastIndexOf(" ") + 1));
						return Integer.compare(secondScore, firstScore);
					}
	            	
	            });	            
	           
	        	   int m = 0;
		            for (int i = 0; i < scores.size(); i++) {
		            	if(m < 13) {
		                scoresArea.append("      " + num + " " + scores.get(i) + "\n"); //Just adding the first 13 highscores
		                num++;
		            }
		            	m++;
	           }
	           
	            
	        } catch (IOException ex) {
	        	System.out.println("Error while reading.");
	            ex.printStackTrace();
	        
			
		}
}
}
		
