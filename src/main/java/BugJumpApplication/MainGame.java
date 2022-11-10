package BugJumpApplication;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Timer;
import acm.graphics.*;
import acm.program.GraphicsProgram;

public class MainGame extends GraphicsProgram {
	private static final int RIGHT_VELOCITY = 10;
	private static final int LEFT_VELOCITY = -10;
	
	private Player player;
	private GRect playerRect;
	private int xVel; //left and right velocity of the player object
	private Boolean isPrevOrientationRight = null; // used to wall detection
	
	private ArrayList<Integer> keyList; //Arraylist of all keys pressed at once
		
	private Timer timer = new Timer(30, this);
	
	private HashMap<GImage, Collectable> collectablesMap = new HashMap<>();
	private HashMap<GImage, Enemy> enemiesMap = new HashMap<>();
	private HashMap<GImage, Terrain> terrainMap = new HashMap<>();
	
	private GLabel starsGlable;
	private GLabel heartGLabel;
	
	private int stars = 0;
	
	@Override
	protected void init() {
		setSize(1080, 1920);
		requestFocus();
	}
	
	@Override
	public void run() {
		keyList = new ArrayList<Integer>();
		
		addKeyListeners();
		setupTerrain();
		setupCollectables();
		setupPlayer();
		setupGUI();
		timer.start();
		player.startTimer();
	}
	public int getStars() {
		return stars;
	}

	public void setHearts(int s) {
		stars = s;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//Gets the keycode of the last key pressed by the player
		int keyCode = e.getKeyCode();
		
		//Adds key pressed to a list of all keys pressed on the keyboard at once
		if (!keyList.contains(keyCode)) {
			keyList.add(keyCode);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		//As soon as one key is released from the keyboard, it is removed from the list of all keys
		//held down by the user
		if (keyList.contains(e.getKeyCode())) {
			keyList.remove(keyList.indexOf(e.getKeyCode()));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		playerRect.setLocation(player.getX(), player.getY());
		
		//If the d key is held and the a key is not
		if (keyList.contains(68) && !keyList.contains(65)) {
			if (xVel < RIGHT_VELOCITY) {
				xVel+=2;
			}
		} 
		//If the a key is held and the d key is not
		else if (keyList.contains(65) && !keyList.contains(68)) {
			if (xVel > LEFT_VELOCITY) {
				xVel-=2;
			}
		//Case for if no key is held or no specific key combination is found
		} else {
			//Slows momentum of player to a stop
			if (xVel != 0) {
				if (xVel > 0) {
					xVel-=2;
				} else {
					xVel+=2;
				}
			}
		}
		player.checkOrientation(xVel);
		
		if (keyList.contains(87)) {
			player.turnOnJumping();
		}
		
		if (checkCollision()) {
			if (isPrevOrientationRight == player.isRightOrientation) {
				xVel = 0;				
			} 
			else if (isPrevOrientationRight != player.isRightOrientation) {
				player.isOnWall = false;
			}
		}
//		System.out.println("isPrev:" + isPrevOrientationRight);
//		System.out.println("isRightOrientation: " + player.isRightOrientation);
		player.move(xVel, 0);
		
	}
	
	//Checks player's left, right, and bottom collision
	private boolean checkCollision() {
		// functionality for ground detection
		if(objectCheck(new GObject[]{getElementAt(player.getX()+2, player.getY() + 54), 
		   getElementAt(player.getX() + playerRect.getWidth()-2, player.getY() + 54)})) {
			
			GObject obj = getElementAt(player.getX() + 4, player.getY() + 52);
			player.isInAir = false;
			if (obj != null) {				
				player.setY((int)obj.getY()-51);
			}
		}
		else {
			player.isInAir = true;
		}
		
	// functionality for wall detection 		
		if (objectCheck(new GObject[] {getElementAt(player.getX()-6, player.getY()),
		    getElementAt(player.getX()-6, player.getY()+50)})) {
			
			isPrevOrientationRight = false;
			player.isOnWall = true;
			return true;
		}
		else if(objectCheck(new GObject[] {getElementAt(player.getX()+50+6, player.getY()),
				getElementAt(player.getX()+50+6, player.getY() + 50)})) {
			isPrevOrientationRight = true;
			player.isOnWall = true;
			return true;
		}
		else {
			player.isOnWall = false;
			isPrevOrientationRight = null;
			return false;
		}
	}
	
	// Checks for: (1) if all detection points are null
	// (2) if not, checks if they interact with a collectable, enemy, or bullet
	private boolean objectCheck(GObject[] arr) {	
		
	int nullCount = 0;
	for (GObject gImage : arr) {
		if(gImage == null) {nullCount++; continue;}
		
		if (collectablesMap.containsKey(gImage)) {
			
			//Checks Map for which collectable is associated to which gImage and switches to perform
			//effects accordingly
			switch(collectablesMap.get(gImage).getCType()) {
				case HEART:
					//Increases player hearts by 1 while hearts < 3 (The max amount of hearts)}
					player.setHearts(player.getHearts()+1);
					heartGLabel.setLabel("Hearts: " + player.getHearts());
					
					break;
				case STAR:
					//Increments total stars by 1;
					stars++;
					starsGlable.setLabel("Stars: " + stars);
					
					break;
				default:
					//Should not be called unless collectable has incorrect collectable type
					System.out.println("INVALID COLLECTABLE TYPE");
			}
			
			//Removes collected collectable
			collectablesMap.remove(gImage);
			remove(gImage);
			return false;
		}
	}	
	if (nullCount == arr.length) {return false;}
		
		return true;
	}
	
	private void setupGUI() {
		starsGlable = new GLabel("Stars: " + stars, 50, 50);
		heartGLabel = new GLabel("Hearts: " + player.getHearts() , 1300, 50);
		add(heartGLabel);
		add(starsGlable);
		
	}
	
	
	
	// sets up the collectables on the main window
	private void setupTerrain() {
		Terrain terrain = new Terrain(0, 500, 800, 500, TerrainType.GRASS);
		GImage image = new GImage(terrain.getTerrainType().toString(), terrain.getX(), terrain.getY());
		image.setSize((double)terrain.getWidth(), (double)terrain.getHeight());
		add(image);
		terrainMap.put(image, terrain);
		
		terrain = new Terrain(900, 700, 800, 200, TerrainType.GRASS);
		image = new GImage(terrain.getTerrainType().toString(), terrain.getX(), terrain.getY());
		image.setSize((double)terrain.getWidth(), (double)terrain.getHeight());
		add(image);
		terrainMap.put(image, terrain);

	}
	
	// sets up the collectables on the main window
	private void setupCollectables() {
		Collectable collectable = new Collectable(300, 450, CollectableType.HEART);
		GImage image = new GImage(collectable.getCType().toString(), collectable.getX(), collectable.getY());
		image.setSize((double)Collectable.getWidth(), (double)Collectable.getHeight());
		add(image);
		collectablesMap.put(image, collectable);
		
		collectable = new Collectable(400, 450, CollectableType.STAR);
		image = new GImage(collectable.getCType().toString(), collectable.getX(), collectable.getY());
		image.setSize((double)Collectable.getWidth(), (double)Collectable.getHeight());
		add(image);
		collectablesMap.put(image, collectable);
	}
	
	private void setupPlayer() {
		player = new Player(200, 300);
		playerRect = new GRect(player.getX(), player.getY(), 50, 50);
		add(playerRect);
		
	}
	
	public static void main(String[] args) {
		new MainGame().start();
	}
}


