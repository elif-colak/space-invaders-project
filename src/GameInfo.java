import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//GameInfo panel for displaying scores lives level and etc.
public class GameInfo extends JPanel {
	private JLabel level;
	private int levelNum = 1;
	private JLabel percantage;
	private int levelPercentage  = 0;
	private int liveNum = 3;
	private JLabel lives;
	private JLabel alienHit;
	private int alienHitPoints = 0;
	
	public GameInfo() {
		
		//Absolute Layout
		setLayout(null);
		
		//Level
		level = new JLabel("LEVEL " + levelNum);
		level.setForeground(Color.RED);
		level.setFont(new Font("SansSerif", Font.BOLD, 30));
		level.setBounds(40,20,300,30);
		add(level);
		
		//Percenatage that done in the level
		String s = Integer.toString(levelPercentage);
		percantage = new JLabel(s + "%");
		percantage.setForeground(Color.RED);
		percantage.setFont(new Font("SansSerif", Font.BOLD, 30));
		percantage.setBounds(200,20,200,30);
		add(percantage);
		
		//Lives
		lives = new JLabel("x" + liveNum);
		lives.setForeground(Color.RED);
		lives.setFont(new Font("SansSerif", Font.BOLD, 30));
		lives.setBounds(280,20,200,30);
		ImageIcon heartIcon= new ImageIcon(getClass().getClassLoader().getResource("heart.png"));
		Image i = ((ImageIcon) heartIcon).getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		heartIcon = new ImageIcon(i);
		lives.setIcon(heartIcon);
		add(lives);
		
		//Hit Points
		String m = Integer.toString(alienHitPoints);
		alienHit = new JLabel(m);
		alienHit.setForeground(Color.RED);
		alienHit.setFont(new Font("SansSerif", Font.BOLD, 30));
		alienHit.setBounds(450,20,200,30);
		add(alienHit);
		
		setOpaque(false);
	}
	
	//Incrementing the score and refreshing the score in the gameinfo panel
	public void incrementAlienHitPoints(int x) {
		alienHitPoints = alienHitPoints + x;
		String m = Integer.toString(alienHitPoints);
		alienHit.setText(m);
	}
	
	//Increasing level percenatge that users plaed so far to move up to next level
	public void decreaseLevelTime() {
		levelPercentage = levelPercentage +1;
		String m = Integer.toString(levelPercentage);
		percantage.setText(m + "%");
		if(levelPercentage == 100 && levelNum !=4) {
			levelPercentage = 0;
			String k = Integer.toString(levelPercentage);
			percantage.setText(k + "%");
			 levelNum++;
			level.setText("LEVEL " + levelNum);
		}
	}
	
	//Decreasing a live
	public void decreaseLive() {
		liveNum--;
		lives.setText("x" + liveNum);		
	}
	
	//Get live number
		public int getLive() {
			return liveNum;
		}
		
	//Set live number
		public void setLive(int m) {
			liveNum = m;
		}	
	
	//Get level percentage
	public int getLevelPercentage() {
		return levelPercentage;
	}
	
	//Get level 
		public int getLevel() {
			return levelNum;
		}

	//Get score 
		public int getScore() {
			return alienHitPoints;
		}		
	

}
