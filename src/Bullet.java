import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bullet extends SpaceItems{
	private static int speed = 7;
	private Image bullet;
	
	public Bullet(int x, int y) {
		//Getting bullets starting points from parent class
		super(x,y);
		
		bullet = new ImageIcon(getClass().getClassLoader().getResource("bullet.png")).getImage(); //Bullet image
	}
	
	   //Updating bullet positions
	   @Override
	   public void move() {
		   y = y - speed;	  
	   }
	   
	   //Drawing the bullets according the positions
	   @Override
	   public void draw(Graphics2D g2D) {
		   g2D.drawImage(bullet, x, y, null);
	   }
	   
	   //Rectangle object to detect collusions
	   @Override
	   public Rectangle makeRectangle() {
	        return new Rectangle(x, y, 50, 20);
	    }
	   	   
}
