import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class BulletAlien extends SpaceItems{
	private static int speed = 4;
	private Image bulletAlien;
	
	public BulletAlien(int x, int y) {
		//Getting bullets starting points from parent class
		super(x,y);
		bulletAlien = new ImageIcon(getClass().getClassLoader().getResource("bullet.png")).getImage(); //Bullet image
	}
	
	   //Updating bullet positions
	   @Override
	   public void move() {
		   y = y + speed;		  
	   }
	   
	   //Drawing the bullets  according the positions
	   @Override
	   public void draw(Graphics2D g2D) {
		   g2D.drawImage(bulletAlien, x, y, null);
	   }
	   
	   //Rectangle object to detect collusions
	   @Override
	   public Rectangle makeRectangle() {
	        return new Rectangle(x, y, 50, 1);
	    }
	   

}