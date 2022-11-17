package BugJumpApplication;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.GraphicsProgram;

import java.awt.Color;

public class MainGame extends GraphicsProgram {
	private static final int PROGRAMHEIGHT = 1080;
	private static final int PROGRAMWIDTH = 1920;
	private static final int RIGHT_VELOCITY = 10;
	private static final int LEFT_VELOCITY = -10;
	
	
	private Player player;
	private GRect playerRect;
	private int xVel; //left and right velocity of the player object
	private int fireRate = 0;
	private Boolean isPrevOrientationRight = null; // used for wall detection
	private Boolean isDead = false;
		
	private Timer timer = new Timer(30, this);
	private int timerCount = 0;
	
	private HashMap<GImage, Collectable> collectablesMap = new HashMap<>();
	private HashMap<GImage, Enemy> enemiesMap = new HashMap<>();
	private HashMap<GImage, Terrain> terrainMap = new HashMap<>();
	private HashMap<GImage, Bullet> bulletMap = new HashMap<>();
	
	private ArrayList<Integer> keyList; //Arraylist of all keys pressed at once
	private ArrayList<Enemy> enemies; //ArrayList for all enemies
	private ArrayList<GRect> enemyRects; //ArrayList for all enemy Rects
	
	private GLabel starsGlable;
	private GLabel heartGLabel;
	
	private int stars = 0;
	
	@Override
	protected void init() {
		setSize(PROGRAMHEIGHT, PROGRAMWIDTH);
		requestFocus();
	}
	
	@Override
	public void run() {
		keyList = new ArrayList<Integer>();
		enemies = new ArrayList<Enemy>();
		enemyRects = new ArrayList<GRect>();
		
		addKeyListeners();
		setupTerrain();
		setupCollectables();
		setupPlayer();
		setupGUI();
		setupEnemies();
		getGCanvas().setBackground(Color.decode("#8addf2")); 
		timer.start();
		player.startTimer();
		
	}
	public int getStars() {
		return stars;
	}

	public void setHearts(int s) {
		stars = s;
	}
	
	/**
	 * Gets the keycode of the last key pressed by the player. 
	 * Adds key pressed to a list of all keys pressed on the keyboard at once
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (!keyList.contains(keyCode)) {
			keyList.add(keyCode);
		}
	}
	
	/**
	 * As soon as one key is released from the keyboard, it is removed from the list of all keys held down by the user
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (keyList.contains(e.getKeyCode())) {
			keyList.remove(keyList.indexOf(e.getKeyCode()));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timerCount++;
		playerRect.setLocation(player.getX(), player.getY());
		updateBullet();

//		// Moves all Enemy rects to follow all enemy objects
//		for (int i = 0; i < enemies.size(); i++) {
//			Enemy temp = enemies.get(i);
//			enemyRects.get(i).setLocation(temp.getX(), temp.getY());
//		}

		// If the d key is held and the a key is not
		if (keyList.contains(68) && !keyList.contains(65)) {
			if (xVel < RIGHT_VELOCITY) {
				xVel += 2;
			}
		}
		// If the a key is held and the d key is not
		else if (keyList.contains(65) && !keyList.contains(68)) {
			if (xVel > LEFT_VELOCITY) {
				xVel -= 2;
			}
			// Case for if no key is held or no specific key combination is found
		} else {
			// Slows momentum of player to a stop
			if (xVel != 0) {
				if (xVel > 0) {
					xVel -= 2;
				} else {
					xVel += 2;
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
			} else if (isPrevOrientationRight != player.isRightOrientation) {
				player.isOnWall = false;
			}
		}
		player.move(xVel, 0);
		
		// adding a bullet on the screen when pressing Space
		if (keyList.contains(32) && player.weapon != null && fireRate <= 0) {
			switch (player.weapon.wType) {
			case HANDHELD: {
				Bullet bullet = player.weapon.attack(new GPoint(player.getX(), player.getY()), player.isRightOrientation);
				GImage image =  new GImage("/Images/rightBullet.png", bullet.getX(), bullet.getY());
				if (!player.isRightOrientation) {image.setImage("/Images/leftBullet.png");}
				bulletMap.put(image, bullet);
				add(image);
				fireRate = 35;
				break;
			}
			case MELEE:
				Bullet bullet;
				GImage image; 
				if (player.isRightOrientation) {
					bullet = new Bullet(player.getX()+60, player.getY()-50, 15, 0, true, 15);
					image = new GImage("/Images/rightMeleeWave.png", bullet.getX(), bullet.getY());
				}
				else {
					bullet = new Bullet(player.getX()-150, player.getY()-50, 15, 180, true, 15);
					image = new GImage("/Images/leftMeleeWave.png", bullet.getX(), bullet.getY());
				}
				bulletMap.put(image, bullet);
				add(image);
				fireRate = 35;
				break;
			default:
				System.out.println("Unknown Weapon Type");
			}
		}
		if (player.weapon != null) {fireRate--;}
		enemyAwareness();
		doEnemyActions();
		
		if (player.getY() > PROGRAMHEIGHT || player.getHearts() < 1) {
			isDead = true;
		}
	}

	
	
	/**
	 * ONLY Checks player's left, right, top, and bottom collision. work hand-on-hand with objectPlayerCollision()
	 * @return true if player is colliding with a wall. False otherwise
	 */
	private boolean checkCollision() {
		if (objectPlayerCollision(new GObject[] {getElementAt(player.getX()+.333*50, player.getY()-6),
			getElementAt(player.getX()+.667*50, player.getY()-6)})) {
				GObject obj = getElementAt(player.getX() + 25, player.getY()-6);
				player.turnOffJumping();
				if (obj != null) {				
					player.setY((int)obj.getY()+(int)obj.getHeight()+1);
				}
		}
			
		
		// functionality for ground detection
		if(objectPlayerCollision(new GObject[]{getElementAt(player.getX()+2, player.getY() + 54), 
		   getElementAt(player.getX() + playerRect.getWidth()-2, player.getY() + 54)})) {
			
			player.isInAir = false;
			GObject obj = getElementAt(player.getX() + 5, player.getY() + 52);
			GObject obj2 = getElementAt(player.getX() + 50-5, player.getY()+52);
			if (obj != null) {				
				player.setY((int)obj.getY()-51);
			}
			else if (obj2 != null) {
				player.setY((int)obj2.getY()-51);
			} 
		}
		else {
			player.isInAir = true;
		}
			

	// functionality for wall detection 		
		if (objectPlayerCollision(new GObject[] {getElementAt(player.getX()-6, player.getY()),
		    getElementAt(player.getX()-6, player.getY()+50)})) {
			
			GObject obj = getElementAt(player.getX() - 6, player.getY()+25);
			if (obj != null) {				
				player.setX((int)obj.getX()+(int)obj.getWidth());
			}
			isPrevOrientationRight = false;
			
			player.isOnWall = true;
			return true;
		}
		else if(objectPlayerCollision(new GObject[] {getElementAt(player.getX()+50+6, player.getY()),
				getElementAt(player.getX()+50+6, player.getY() + 50)})) {
			
			GObject obj = getElementAt(player.getX()+50+6, player.getY()+25);
			if (obj != null) {				
				player.setX((int)obj.getX()-50);
			}
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
	
	
	/**
	 * 
	 * @param arr an array of GObjects used to check if they exist. These are our player collision points
	 * @return false if all GObjects are null or player is colliding with a enemy, or collectable. 
	 * True if one or more collision points aren't null and are colliding wih a wall
	 * 
	 */
	private boolean objectPlayerCollision(GObject[] arr) {	
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
						starsGlable.setLabel("Stars: " + ++stars);
						
						break;
					case HANDHELD:
						player.weapon = new Weapon(WeaponType.HANDHELD);
						break;
					case MELEE:
						player.weapon = new Weapon(WeaponType.MELEE);
						break;
					default:
						//Should not be called unless collectable has incorrect collectable type
						System.out.println("INVALID COLLECTABLE TYPE");
				}
				collectablesMap.remove(gImage);
				remove(gImage);
				return false;
			}
			else if (enemiesMap.containsKey(gImage)) {
				
			}
			else if (terrainMap.containsKey(gImage) && terrainMap.get(gImage).getTerrainType() == TerrainType.SPIKE) {
				player.setHearts(0);
			} 
		} 
		if (nullCount == arr.length) {return false;}	
		return true;
}
	
	private boolean checkBulletCollision(GImage key, Bullet val) {
		GObject obj1 = getElementAt(key.getX()-2, key.getY());
		GObject obj2 = getElementAt(key.getX()-2, key.getY()+key.getHeight());
		GObject obj3 = getElementAt(key.getX()+key.getWidth()+2, key.getY());
		GObject obj4 = getElementAt(key.getX()+key.getWidth()+2, key.getY()+key.getHeight());
		
		
		if (val.isFriendly() == false && (obj1 == playerRect || obj2 == playerRect || obj3 == playerRect || obj4 == playerRect)) {
			if (!(player.getHitCooldown() > 0)) {				
				player.setHearts(player.getHearts()-1);
				heartGLabel.setLabel("Hearts: " + player.getHearts());
				player.resetHitCooldown();
			}
			return true;
		}
//		if (val.isFriendly() && enemyRects.contains(obj4) enemiesMap.containsKey(obj1) || enemiesMap.containsKey(obj2) || enemiesMap.containsKey(obj3) || enemiesMap.containsKey(obj4)) {
//			return true;
//		}
		if (val.isFriendly() && (enemiesMap.containsKey(obj1)) || enemiesMap.containsKey(obj2)  || enemiesMap.containsKey(obj3)  || enemiesMap.containsKey(obj4)){
			
			return true;
		}
	
		if(terrainMap.containsKey(obj1) || terrainMap.containsKey(obj2) ||  terrainMap.containsKey(obj3) || terrainMap.containsKey(obj4)) {				
			return true;
		}
		return false;
		
	}
	
	/**
	 * updates bullet location on the GUI
	 */
	private void updateBullet() {
		if (bulletMap.isEmpty()) {return;}
		
		GImage key;
		Bullet val;
		ArrayList<GImage> keysToRemove = new ArrayList<>();
		for (Entry<GImage, Bullet> entry : bulletMap.entrySet()) { 
			key = entry.getKey();
			val = entry.getValue();
			
			key.movePolar(val.getVelocity(), val.getTheta());
			if(checkBulletCollision(key, val)) {keysToRemove.add(key); continue;}
			if (val.hasTimerRunout()) {keysToRemove.add(key);}		
		}
		
		for (GImage gImage : keysToRemove) {
			bulletMap.remove(gImage);
			remove(gImage);
		}
		
	}
	
	
	//For now just attacks but could do other stuff?
	private void doEnemyActions() {
		
		for (Entry<GImage, Enemy> entry : enemiesMap.entrySet()) {
			Enemy each = entry.getValue();
			GImage eachImage = entry.getKey();
			if (each.getAwareness()) {
				System.out.println("aware");
				if (timerCount - each.getLastShot() >= 150) {
					each.setLastShot(timerCount);
					Bullet[] bullets = each.attack();
					if (bullets != null) {
//						System.out.println("firing");
						for (int i = 0; i < bullets.length; i++) {
							Bullet b = bullets[i];
							GImage bImage = new GImage("/Images/rightBullet.png", b.getX(),b.getY());
							bulletMap.put(bImage,b);
							add(bImage);
						}
//						for (Bullet e : bullets) {
//							System.out.println(e.getTheta());
//						}
					} 
					else {
						//TODO: Melee attack
						
					}
				}
			}
			else {
				eachImage.setLocation(each.getX(),each.getY());
				
				if(getElementAt(each.getX()-2, each.getY()+52) == null || terrainMap.containsKey(getElementAt(each.getX()-2, each.getY()))) {
					each.setIsRightOrientation(true);
				}
				else if (getElementAt(each.getX()+52, each.getY()+52) == null || terrainMap.containsKey(getElementAt(each.getX()-2, each.getY()))) {
					each.setIsRightOrientation(false);
				}

			}
		}
	}

//	private void enemyAwareness() {
//		//System.out.println("Called Function");
//		for(Enemy all: enemies) {
//		double m, ePointx, ePointy, dx, dy; 
//		m = 0;
//			ePointx = all.getX(); 		
//			ePointy = all.getY();
//			
//			m = (ePointy - player.getY())/(ePointx - player.getX());
//			
////			if(m > 1) {
////				dy = m;
////				dx = +-1;
////			}
////			else {
////				dx = m;
////				dy = +-1;
////			}
//			if(ePointx > player.getX()) {
//				dx = -1;
//			} else {
//				dx = 1;
//			}
//			
//			dy = m;
//			int count = 0;
//			while(((ePointx != player.getX() || ePointy != player.getY())) && count < 500) {
//				count++;
//				ePointx += dx;
//				ePointy += dy;
//				System.out.println(dx + " = " + dy + " = " + m);
//				System.out.println("e" +ePointx + " : " + ePointy);
//				System.out.println("p" + player.getX()+ " : " + player.getY());
//			
//				GObject obj1 = getElementAt(ePointx, ePointy);
//				System.out.println("X: " + Math.abs(player.getX()-ePointx));
//				System.out.println("Y: " + Math.abs(player.getY()-ePointy));
//				if(terrainMap.containsKey(obj1)) {
//					all.switchAwareness(false);
//					//System.out.println("sees terrain");
//					return;
//				}
//				else if (Math.abs(player.getX()-ePointx) <= 20 && Math.abs(player.getY()-ePointy) <= 20) {
//					all.switchAwareness(true);
//					System.out.println("sees player");
//					return;
//				}
				
			
//			}	
			//all.switchAwareness(false);
//		}
//	}
	
	private void enemyAwareness() {
		int ePointx, ePointy;
		for(Entry<GImage,Enemy> entry : enemiesMap.entrySet()) {
			Enemy all = entry.getValue();
			ePointx = all.getX(); 		
			ePointy = all.getY();
 
			//Note: Make sure to change enemy isRightOrientation depending
			// on where the player is 
			System.out.println(Math.abs(player.getX()-ePointx));
			if (Math.abs(player.getX()-ePointx) <= 400 && Math.abs(player.getY()-ePointy) <= 150) {
				all.switchAwareness(true);
				System.out.println("sees player");
				break;
			}
			else {
				all.switchAwareness(false);
				System.out.println("Awareness : False");
			}
		}
	}
	
	/**
	 * Sets up the collectables on the main window
	 */
	private void setupGUI() {
		heartGLabel = new GLabel("Hearts: " + player.getHearts() , 50, 50);
		starsGlable = new GLabel("Stars: " + stars, 1400 , 50);
		add(heartGLabel);
		add(starsGlable);
		
	}
	
	/**
	 * Sets up the collectables on the main window
	 */
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
		
		terrain = new Terrain(700, 300, 200, 100, TerrainType.DIRT);
		image = new GImage(terrain.getTerrainType().toString(), terrain.getX(), terrain.getY());
		image.setSize((double)terrain.getWidth(), (double)terrain.getHeight());
		add(image);
		terrainMap.put(image, terrain);
	}
	
	// sets up the collectables on the main window
	private void setupCollectables() {
		Collectable collectable = new Collectable(300, 450, CollectableType.HEART);
		GImage image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
		add(image);
		collectablesMap.put(image, collectable);
		
		collectable = new Collectable(400, 450, CollectableType.HANDHELD);
		image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
		add(image);
		collectablesMap.put(image, collectable);
		
		collectable = new Collectable(800, 250, CollectableType.MELEE);
		image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
		add(image);
		
		collectable = new Collectable(800, 650, CollectableType.STAR);
		image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
		add(image);
		
		
		collectablesMap.put(image, collectable);
		add(new GImage(CollectableType.CHEESE.toString()));
	}
	
	private void setupPlayer() {
		player = new Player(200, 300);
		playerRect = new GRect(player.getX(), player.getY(), 50, 50);
		add(playerRect);
		
	}
	
	private void setupEnemies() {
		Enemy tempEnemy = new Enemy (500,450,EnemyType.FLOWER);
		GImage image = new GImage("/Images/sunflower.png",tempEnemy.getX(),tempEnemy.getY());
		enemiesMap.put(image, tempEnemy);
		add(image);
		
		
		tempEnemy = new Enemy (750,250,EnemyType.WORM);
		image = new GImage("/Images/worm.png",tempEnemy.getX(),tempEnemy.getY());
		
		enemiesMap.put(image, tempEnemy);
		add(image);
			
		tempEnemy = new Enemy (950,650,EnemyType.SPIDER);
		image = new GImage("/Images/spider.png",tempEnemy.getX(),tempEnemy.getY());
		enemiesMap.put(image, tempEnemy);
		add(image);
		
		System.out.println();
	}
	
//	private void setupEnemies() {
//		Enemy tempEnemy = new Enemy (500,450,EnemyType.FLOWER);
//		GRect enemyRect = new GRect(tempEnemy.getX(),tempEnemy.getY(),50,50);
//		enemyRect.setFillColor(Color.RED);
//		enemyRect.setFilled(true);
//		add(enemyRect);
//		enemiesMap.put(enemyRect, tempEnemy);
//	}

	public void startGame() {
		new MainGame().start();
	}
	
	public static void main(String[] args) {
		new MainGame().start();
	}
}


