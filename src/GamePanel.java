import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, Runnable, MouseListener, MouseMotionListener{
	private JLabel bckg;
	private ImageIcon s;
	private Bullet bullet;
	private Alien alien;
	private ArrayList<Bullet> bullets;
	private Thread gameThread;
	private ArrayList<Alien> aliens;
    private GameInfo gameinfopanel;
    private BulletAlien bulletAlien;
    public ArrayList<BulletAlien> bulletAliens;
    private JLabel spaceship;
    private String username;
    private Clip sound;
    private Clip gameSound;
    private Timer leveltimer;
    private Timer alientimer;
    private JLabel gameFinishedLabel;
    private JLabel gameOverLabel;
    private int count = 100;
    private boolean finished;

    
	public GamePanel(String username)  {
		//Set username
		setUser(username); 
		
		setLayout(null);
		
		//Label for finishing the game successfully
		gameFinishedLabel = new JLabel();
		gameFinishedLabel  = new JLabel("GAME FINISHED");
		gameFinishedLabel.setForeground(Color.RED);
		gameFinishedLabel.setFont(new Font("SansSerif", Font.BOLD, 45));
	   	gameFinishedLabel.setBounds(270,270,500,70);
	   	gameFinishedLabel.setVisible(false);
	   	add(gameFinishedLabel);
	   	
	   	//Game over label
	   	gameOverLabel = new JLabel();
		gameOverLabel  = new JLabel("GAME OVER");
		gameOverLabel.setForeground(Color.RED);
		gameOverLabel.setFont(new Font("SansSerif", Font.BOLD, 45));
	   	gameOverLabel.setBounds(310,260,500,70);
	   	gameOverLabel.setVisible(false);
	   	add(gameOverLabel);
		
	   	//Spaceship
		spaceship = new JLabel();
		s = new ImageIcon(getClass().getClassLoader().getResource("spaceship.png"));
		spaceship.setIcon(s);
		spaceship.setBounds(325,460, 200,100);
		add(spaceship);
		
		//ADding listeners
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);		
		
		//Adding game info panel
		gameinfopanel = new GameInfo();
		gameinfopanel.setBounds(0,15,900,100);
		gameinfopanel.setVisible(true);
		add(gameinfopanel);	
				
		//Setting a space background
		ImageIcon game_back = new ImageIcon(getClass().getClassLoader().getResource("game_background.jpg"));
		bckg = new JLabel();
		bckg.setIcon(game_back);
		bckg.setBounds(0, 0, 900, 600);
		add(bckg);

		//Bullet list
		bullets = new ArrayList<Bullet>();
		
		//Alien bullet list
		bulletAliens = new ArrayList<BulletAlien>();
		
		//Created a thread for the game panel
		gameThread = new Thread(this);
        gameThread.start();       
        
        //Alien list
        aliens = new ArrayList<Alien>();     		
		
		//Game Sound
		  try {
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("gamemusic.wav"));
	            gameSound = AudioSystem.getClip();
	            gameSound.open(audioInputStream);
	            gameSound.loop(Clip.LOOP_CONTINUOUSLY);
	            gameSound.start();

	        } catch (UnsupportedAudioFileException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (LineUnavailableException ex) {
	            ex.printStackTrace();
	        }
		  

		  levelTimer(); //timer for level percentage
		  alienTimer(); //timer for creating alien and alien properties
		  
		  finished = false;
		
	}
	
	
    @Override
    public void run() {
    	
        while (gameinfopanel.getLive() > 0) {
        		
        		//All levels are finished
        		if (gameinfopanel.getLevel() > 3) {
        		     finished = true;
        		     gameFinishedLabel.setVisible(true);
        	         gameFinished();
        	         removeKeyListener(this);
        			 removeMouseListener(this);
        			 removeMouseMotionListener(this);
        	         stopTimers();
        		     gameinfopanel.setLive(0);
        		}      		
        		
        		//Moving every bullet up in the bullet list
        		for(int i=0; i< bullets.size(); i++) {
        			bullets.get(i).move();
        		}
        		
        		//Moving every alien in the list
        		for(int i=0; i< aliens.size(); i++) {
        			aliens.get(i).move();
        		}
        		
        		//Moving every alien bullet in the list
        		for(int i=0; i< bulletAliens.size(); i++) {
        			bulletAliens.get(i).move();
        			
        		}
        		
        		
        		if(count == 0) {
					Alien.speed++; //Increasing speed in each level
					count = 100;
				}


            		detectCollusionAB(); //detect alien and bullet collusion
            		detectCollusionABS(); //detect alien bullet and spaceship collusion
            		detectCollusionAS(); //detect alien and spaceship collusion


        		//Refreshing the screen
        		repaint();
        		
        		try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        
    		
        }	
        
        //Game Over
        if(gameinfopanel.getLive() <= 0 && finished == false) {

        	//After the game over
        	appendToHighScores();
			removeKeyListener(this);
			removeMouseListener(this);
			removeMouseMotionListener(this);
        	stopTimers();
	   	 	gameSound.stop();
	   	 	gameSound.close(); 
	   	 	
	   	 //Game finished sound
	   	 try {
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("gameover_sound.wav"));
	         sound = AudioSystem.getClip();
	         sound.open(audioInputStream);
	         sound.start();

	     } catch (UnsupportedAudioFileException ex) {
	         ex.printStackTrace();
	     } catch (IOException ex) {
	         ex.printStackTrace();
	     } catch (LineUnavailableException ex) {
	         ex.printStackTrace();
	     }
	   	 gameOverLabel.setVisible(true);	
        }



    }
    
    
    //Setter for username
    public void setUser(String username) {
    	this.username = username;
    }
  

    private void gameFinished() {
    	 // All three levels finished condition
        //Stopping game sound
   	 	gameSound.stop();
   	 	gameSound.close(); 
   	 	
   	 //Game finished sound
   	 try {
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("gamefinished_sound.wav"));
         sound = AudioSystem.getClip();
         sound.open(audioInputStream);
         sound.start();

     } catch (UnsupportedAudioFileException ex) {
         ex.printStackTrace();
     } catch (IOException ex) {
         ex.printStackTrace();
     } catch (LineUnavailableException ex) {
         ex.printStackTrace();
     }
   	  
   	 	appendToHighScores();
   	 	leveltimer.stop();
   	 	alientimer.stop();
    }
    
    
    //Increasing level percenatge with another timer
	private void levelTimer() {
		leveltimer = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameinfopanel.decreaseLevelTime();
				count--;

			}});
		leveltimer.start();
	}
	
	
    //Timer for sreating aliens and updating some alien properties
	private void alienTimer() {
	       alientimer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				createAlien(); //creating a alien		
				createAlienBullet();	//creating alien bullet
				
        		
			}
		});
		alientimer.start();
	}
	
	//Stopping alien and level timers
	private void stopTimers() {
	    if (leveltimer != null && leveltimer.isRunning()) {
	        leveltimer.stop();
	    }
	    if (alientimer != null && alientimer.isRunning()) {
	        alientimer.stop();
	    }
	}
        
    
    //Creating aliens with random x coordinates
    private void createAlien() {
    	   int alienNum = 0; 
    	    if (gameinfopanel.getLevel() == 1) {
    	        alienNum = 1;
    	    } else if (gameinfopanel.getLevel() == 2) {
    	        alienNum = 2; //Increasing alien numbers to 2 in level 2
    	    } else if (gameinfopanel.getLevel() == 3) {
    	        alienNum = 3; //Increasing alien numbers to 3 in level 3
    	    }
    	    
    	    
    	   for(int i=0; i<alienNum; i++ ) {
    	    	  Random random = new Random();
    	    	  int m = random.nextInt(10)+1;
    	    	  int choiceOfDirection  = random.nextInt(2)+1;
    	    	  
    	    	  //LEVEL 1
    	    	  if(gameinfopanel.getLevel() ==1) {
    	    		  if(m == 5 || m ==9) {
    	        		  int x = random.nextInt(900-0)+1;
    	                  int y = -20; 
    	                  int choice = random.nextInt(4) +1; //choosing different aliens
    	                  alien = new Alien(x, y, choice,choiceOfDirection );
    	                  aliens.add(alien);
    	                  alien = new Alien(x + 70, y , choice,choiceOfDirection );
    	                  aliens.add(alien);
    	        	  }
    	        	  else {
    	        		  int x = random.nextInt(900-0)+1;
    	                  int y = -20; 
    	                  int choice = random.nextInt(4) +1; //choosing different aliens
    	                  alien = new Alien(x, y, choice,choiceOfDirection );
    	                  aliens.add(alien);
    	        	  }  
    	    	  }
    	    	  
    	    	  //LEVEL 2
    	    	  else if(gameinfopanel.getLevel() ==2) {
    	    		  if(m == 5) {
    	        		  int x = random.nextInt(900-0)+1;
    	                  int y = -10; 
    	                  int choice = random.nextInt(4) +1; //choosing different aliens
    	                  alien = new Alien(x, y, choice,choiceOfDirection );
    	                  aliens.add(alien);
    	                  alien = new Alien(x + 70, y , choice,choiceOfDirection );
    	                  aliens.add(alien);
    	        	  }
    	    		  if(m == 10) {
    	    			  int x = random.nextInt(800-0)+1;
    	                  int y = -20; 
    	                  int choice = random.nextInt(3) +1; //choosing different aliens
    	                  if(choice == 1  || choice == 2 || choice == 3) {
    	                	   alien = new Alien(x, y, choice,choiceOfDirection );
    	                       aliens.add(alien);
    	                       alien = new Alien(x + 70, y , choice,choiceOfDirection );
    	                       aliens.add(alien);
    	                       alien = new Alien(x + 140, y , choice,choiceOfDirection );
    	                       aliens.add(alien);
    	                  }
    	                  else {
    	                	  alien = new Alien(x, y, choice,choiceOfDirection );
    	                      aliens.add(alien);
    	                      alien = new Alien(x + 130, y , choice,choiceOfDirection );
    	                      aliens.add(alien);
    	                      alien = new Alien(x + 260, y , choice,choiceOfDirection );
    	                      aliens.add(alien);
    	                  }
    	               
    	    		  }
    	        	  else {
    	        		  int x = random.nextInt(900-0)+1;
    	                  int y = -20; 
    	                  int choice = random.nextInt(3) +1; //choosing different aliens
    	                  alien = new Alien(x, y, choice,choiceOfDirection );
    	                  aliens.add(alien);
    	        	  }  
    	    	  }
    	    	  
    	    	  //LEVEL 3
    	    	  else if (gameinfopanel.getLevel() == 3){
    	    		  if(m == 5 || m == 7) {
    	        		  int x = random.nextInt(750-0)+1;
    	                  int y = -20; 
    	                  int choice = random.nextInt(4) +1; //choosing different aliens
    	                  alien = new Alien(x, y, choice,choiceOfDirection );
    	                  aliens.add(alien);
    	                  alien = new Alien(x + 70, y , choice,choiceOfDirection );
    	                  aliens.add(alien);
    	        	  }
    	    		  if(m == 10) {
    	    			  int x = random.nextInt(800-0)+1;
    	                  int y = -20; 
    	                  int choice = random.nextInt(3) +1; //choosing different aliens
    	                  if(choice == 1  || choice == 2 || choice == 3) {
    	                	   alien = new Alien(x, y, choice,choiceOfDirection );
    	                       aliens.add(alien);
    	                       alien = new Alien(x + 80, y , choice,choiceOfDirection );
    	                       aliens.add(alien);
    	                       alien = new Alien(x + 180, y , choice,choiceOfDirection );
    	                       aliens.add(alien);
    	                  }
    	                  else {
    	                	  alien = new Alien(x, y, choice,choiceOfDirection );
    	                      aliens.add(alien);
    	                      alien = new Alien(x + 130, y , choice,choiceOfDirection );
    	                      aliens.add(alien);
    	                      alien = new Alien(x + 260, y , choice,choiceOfDirection );
    	                      aliens.add(alien);
    	                  }
    	               
    	    		  }
    	    		  
    	        	  else {
    	        		  int x = random.nextInt(900-0)+1;
    	                  int y = -20; 
    	                  int choice = random.nextInt(3) +1; //choosing different aliens
    	                  alien = new Alien(x, y, choice,choiceOfDirection );
    	                  aliens.add(alien);
    	        	  }  
    	    	  }
    	   }

    }
    
    
    
    //Creates alien bullets by randomly choosing an alien
    private void createAlienBullet() {
        Random random = new Random();
        int num = random.nextInt(4) +1;
        if(num == 3) {
        	 int index = random.nextInt(aliens.size()); 
             Alien alienRandom = aliens.get(index);       
             bulletAlien = new BulletAlien(alienRandom.getX(), alienRandom.getY() +60); 
             bulletAliens.add(bulletAlien);
             
          
   		
        }
        
    }
    
    
    //Detection a collusion between aliens and bullets
    private void detectCollusionAB(){
    	if(aliens.size() != 0 && bullets.size() != 0) {   		
    		for(int i=0; i< aliens.size(); i++) {
    				for(int m=0; m< bullets.size(); m++) {
    					Rectangle ar = aliens.get(i).makeRectangle();
    					Rectangle br =  bullets.get(m).makeRectangle();
            			if(ar.intersects(br)) {
            				if( aliens.get(i).getKind() != 4) gameinfopanel.incrementAlienHitPoints(10); 
            				aliens.get(i).addHit();
            				bullets.remove(m);
            				
            				if(aliens.get(i).getHitCount() == 1 && gameinfopanel.getLevel() == 1 && aliens.get(i).getKind() != 4) aliens.remove(i); 
            				// Purple alien can be killed with 2 hits
            				else if(gameinfopanel.getLevel() == 1 && aliens.get(i).getKind() == 4 ) {
            					if(aliens.get(i).getHitCount() == 2) {
            						gameinfopanel.incrementAlienHitPoints(20);  // Purple alien gives more point
            						aliens.remove(i);
            					}
            					
            				}
            				else if(aliens.get(i).getHitCount() == 2 && gameinfopanel.getLevel() == 2) aliens.remove(i); 
            				else if(aliens.get(i).getHitCount() == 2 && gameinfopanel.getLevel() == 3) aliens.remove(i); 
            			    break; 
            			    
            			}  					
            		} 		
        	}	
    	} 
    } 
    
 
    
    //Detection a collusion between alien bullet and the spaceship
    private void detectCollusionABS(){
    	if(bulletAliens.size() != 0) {   		
    				for(int m=0; m< bulletAliens.size(); m++) {
    					Rectangle ar = bulletAliens.get(m).makeRectangle();
    					Rectangle sr =  new Rectangle(spaceship.getX(), spaceship.getY(), spaceship.getWidth() -20 , spaceship.getHeight());
            			if(ar.intersects(sr)) {
            				bulletAliens.remove(m);
            				gameinfopanel.decreaseLive();
            			 	 //Spaceship hitted sound
            			   	 try {
            			         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("hitted_sound.wav"));
            			         sound = AudioSystem.getClip();
            			         sound.open(audioInputStream);
            			         sound.start();

            			     } catch (UnsupportedAudioFileException ex) {
            			         ex.printStackTrace();
            			     } catch (IOException ex) {
            			         ex.printStackTrace();
            			     } catch (LineUnavailableException ex) {
            			         ex.printStackTrace();
            			     }
            				
            			    break;
	    
            			}  					
          		} 		
    	} 
    }
  
    
    
   //Detection a collusion between aliens and spaceship
    private void detectCollusionAS(){
    	if(aliens.size() != 0) {   		
    		for(int i=0; i< aliens.size(); i++) {			
    					Rectangle ar = aliens.get(i).makeRectangle();
    					Rectangle sr =  new Rectangle(spaceship.getX(), spaceship.getY(),  spaceship.getWidth(), spaceship.getHeight());
            			if(ar.intersects(sr)) {
            				aliens.remove(i);
            				gameinfopanel.decreaseLive();
            				break;
                    		}
            			              				
            			}  					
		
        	}	
    	} 
    

    


    //Appending to higscores.txt
    
    public void appendToHighScores() {

    String fileName = "highscores.txt";
    ArrayList<String> fileLines = new ArrayList<>();
    boolean isExisting = false;
    String name = null;
    String sc = null;
    
    while(gameinfopanel.getScore() != 0) {
    	
    	 try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileName))) {
    	        String line;
    	        while ((line = bufferReader.readLine()) != null) {
    	        	if (line.contains(username)) {
    	                line = line.replaceAll("\\s+", " ");
    	                String[] fields = line.split(" ");
    	                if (fields.length == 2) {
    	                    name = fields[0];
    	                    sc = fields[1];
    	                    int sci = Integer.parseInt(sc);

    	                    if (sci < gameinfopanel.getScore()) {
    	                        isExisting = true;
    	                        
    	                    }
    	                    else return;
    	                    
    	                }
    	            }
    	            fileLines.add(line);
    	        }
    	    } catch (IOException e) {
    	        System.out.println("Error while reading.");
    	        e.printStackTrace();
    	        return;
    	    }

    	    // If the user's score is already exist in highscores and is higher, update the score
    	    if (isExisting) {
    	        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(fileName))) {
    	            for (String l: fileLines) {
    	            	if(sc != "0" || gameinfopanel.getScore() != 0) {
    	            		if (l.contains(name)) {
       	                	 l = name + "                                  " + gameinfopanel.getScore();   
       	                }
       	                bufferWriter.write(l);
       	                bufferWriter.newLine();
    	            	}
    	                
    	            }
    	            return;
    	        } catch (IOException e) {
    	            System.out.println("Error while writing.");
    	            e.printStackTrace();
    	            return;
    	        }
    	    } 
    	   
    	    	// Append the new score to the end of the file
    	        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(fileName, true))) {
    	            bufferWriter.append(username + "                                  " + gameinfopanel.getScore());
    	            bufferWriter.newLine();
    	        } catch (IOException e) {
    	            System.out.println("Error while writing.");
    	            e.printStackTrace();
    	            return;
    	        }
    }


         
    }


    

	//Painting
	public void paint(Graphics g) {
		//getting our frames properties
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		//drawing bullets
		for(int i=0; i< bullets.size(); i++) {
			bullets.get(i).draw(g2D);
		}
		//drawing aliens
		for(int i=0; i< aliens.size(); i++) {
			aliens.get(i).draw(g2D);
		}
		//drawing alien bullets
		for(int i=0; i< bulletAliens.size(); i++) {
			bulletAliens.get(i).draw(g2D);
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		//Spaceship goes left
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				//Frame constraints
				
				if(spaceship.getX() != -55) {
					spaceship.setLocation(spaceship.getX()-10, spaceship.getY() );
				}							
		}
		
		//Spaeship goes right
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			//Frame constraints
			if(spaceship.getX() != 725) {
				spaceship.setLocation(spaceship.getX()+10, spaceship.getY() );
			}
		}
		
		//Spaeship goes up
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			
			//Frame constraints
			if(spaceship.getY() != -20) {
				spaceship.setLocation(spaceship.getX(), spaceship.getY()-10 );

			}
		}
		
		//Spaeship goes down
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//Frame constraints
			if(spaceship.getY() != 480) {
				spaceship.setLocation(spaceship.getX(), spaceship.getY()+10 );
			}
		}
		
		//Spaceship shooting bullets
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			 //Bullet sound when spaceship fires a bullet
			  try {
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("bullet_sound.wav"));
		            sound = AudioSystem.getClip();
		            sound.open(audioInputStream);
		            sound.start();

		        } catch (UnsupportedAudioFileException ex) {
		            ex.printStackTrace();
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        } catch (LineUnavailableException ex) {
		            ex.printStackTrace();
		        }
			  
			//When space is pressed this creates new bullet and adds it to bullet list
			bullet = new Bullet(spaceship.getX()+45,spaceship.getY()-25);
			bullets.add(bullet);
			
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		


		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		//Moving spaceship in the panel with mouse
	    int xSpaceship = spaceship.getX();
	    int ySpaceship = spaceship.getY();
	    int xMouse = e.getX();
	    int yMouse = e.getY();
	    int xNew = xSpaceship + (xMouse- xSpaceship) / 10;
	    int yNew = ySpaceship + (yMouse- ySpaceship) / 10;
	    spaceship.setLocation(xNew, yNew);
	  
}
			

	@Override
	public void mouseClicked(MouseEvent e) {
		 //Bullet sound when spaceship fires a bullet
		  try {
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("bullet_sound.wav"));
	            sound = AudioSystem.getClip();
	            sound.open(audioInputStream);
	            sound.start();

	        } catch (UnsupportedAudioFileException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (LineUnavailableException ex) {
	            ex.printStackTrace();
	        }
		  
		//When space is pressed this creates new bullet and adds it to bullet list
		bullet = new Bullet(spaceship.getX() +45,spaceship.getY()-25);
		bullets.add(bullet);
		
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
