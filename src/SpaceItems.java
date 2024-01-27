import java.awt.Graphics2D;
import java.awt.Rectangle;

//An abstract class for Bullet Alien and BulletAlien classes becasue they have properties in common
public abstract class SpaceItems {
	
	public int x;
	public int y;
	
	public SpaceItems(int x, int y) {
		setX(x);
		setY(y);
	}	
	
	//X coordinate
	public int getX() {
		return x;
	}
	
	//Setter for x coordinate
	public void setX(int x) {
		this.x = x;
	}
	
	//Y coordinate
	public int getY() {
		return y;
	}
	
	//Setter for y coordinate
	public void setY(int y) {
		this.y = y;
	}	
	
	//Methods that common in subclasses
	public abstract Rectangle makeRectangle();
	public abstract void draw(Graphics2D g2D);
	public abstract void move();
	
}
