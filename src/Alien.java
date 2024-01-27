import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class Alien extends SpaceItems{
	private Image alien;
	private int hitCount = 0;
	private int alienKind;
	public static int speed = 1;
	private static int speedNeg = -1;
	private int speedChoice;
	private int choice;
	
	public Alien(int x, int y, int alienKind, int choice) {
		//Getting bullets starting points from parent class
		super(x,y);
		setAlienKind(alienKind);
		setChoice(choice);
		
		if( alienKind == 2) {
			alien = new ImageIcon(getClass().getClassLoader().getResource("alien1.png")).getImage().getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
		}
		else if( alienKind == 1) {
			alien = new ImageIcon(getClass().getClassLoader().getResource("alien2.png")).getImage().getScaledInstance(90, 120, java.awt.Image.SCALE_SMOOTH);
		}
		else if( alienKind == 3) {
			alien = new ImageIcon(getClass().getClassLoader().getResource("alien3.png")).getImage().getScaledInstance(140, 110, java.awt.Image.SCALE_SMOOTH);
		}
		else if( alienKind == 4) {
			alien = new ImageIcon(getClass().getClassLoader().getResource("purplealien.png")).getImage().getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
		}
		  speedChoice = speed;
		  
	      if(choice == 1) speedChoice = speed;
	      else if(choice == 2) speedChoice = speedNeg;
	}	
	
	  //Moving alien according to its speed
	   @Override
	   public void move() {
           if(choice == 1 ) {
        	   x = x + speedChoice;	
    		   y = y + speed;
    		   if (x <= -25 || x >= 780) { //Duvardan çarptırma
    			   speedChoice = speedNeg;
    	        }
           }
           else if(choice == 2) {
        	   x = x + speedChoice;	
    		   y = y + speed;
    		   if (x <= -25 || x >= 780) { //Duvardan çarptırma
    			   speedChoice = speed;
    	        }
           }	  
		  
	   }
	   
	   //Setter for alien kind
	   public void setAlienKind(int alienKind) {
		   this.alienKind = alienKind;
	   }
	   
	   //Setter for choice for left or right that alien came from
	   public void setChoice(int choice) {
		   this.choice = choice;
	   }
	   
	   //Drawing an alien according the positions
	   @Override
	   public void draw(Graphics2D g2D) {
		   g2D.drawImage(alien, x, y, null);
	    }

	   //Increasing hit count when there is a hit ffrom an alien
	   public void addHit() {
		   hitCount = hitCount +1;
	   }
	   
	   //Getting hit count
	   public int getHitCount() {
		   return hitCount;
	   }
	   
	   //Get the alien kind
	   public int getKind() {
		   return alienKind;
	   }
	   
	 //Rectangle object to detect collusions
	   @Override
	   public Rectangle makeRectangle() {
	        return new Rectangle(x, y, 40,  60);
	    }
	   
}