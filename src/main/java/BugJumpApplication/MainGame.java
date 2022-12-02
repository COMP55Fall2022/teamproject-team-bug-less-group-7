package BugJumpApplication;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


import acm.graphics.*;


public class MainGame extends GraphicsPane {
	
	/////////////////////////////////////////////////////////////

	/* All player's variables used in this class */
	private static final int RIGHT_VELOCITY = 10;
	private static final int LEFT_VELOCITY = -10;
	
	private Player player;
	private GImage playerImage;
	private int xVel; //left and right velocity of the player object
	private int fireRate;
	private Boolean isPrevOrientationRight; // used for wall detection
	private int playerWidth;
		
	/////////////////////////////////////////////////////////////

	/* Data Structures that holds all object in game scene */
	private HashMap<GImage, Collectable> collectablesMap;
	private HashMap<GImage, Enemy> enemiesMap;
	private HashMap<GImage, Terrain> terrainMap;
	private HashMap<GImage, Bullet> bulletMap;
	
	//Arraylist of all keys pressed at once
	private ArrayList<Integer> keyList;
	
	
	/////////////////////////////////////////////////////////////

	/* Variables that are convenient for the game */
	private int timerCount;
	private boolean isGamePaused;
	private int stars = 0;
	private MainApplication program;
	private Dimension dimension;
	private int level;
	
	//File Reader object to load levels
	private FileReader fileReader;
	
	/////////////////////////////////////////////////////////////
	
	private GImage starGImage;
	private GImage heartGImage;
	
	private GImage background;
	private AudioPlayer audio;
	private GRect victoryBorder;
	private GParagraph victory;
	private GRect gameOverBorder;
	private GParagraph over;
	
	private GButton nextLevelButton;
	private GButton restartButton;
	private GButton mainMenuButton;
	
	private GRect pauseBorder;
	private GParagraph pause;
	private GButton resumeButton;
	
	
	
	
	public MainGame(MainApplication e, int level) {
		//this.level = 0;

		//this.level = 3;

		//this.level = 0;
		
		this.level = 3;
	}

	@Override
	public void showContents() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		keyList = new ArrayList<Integer>();
		collectablesMap = new HashMap<>();
		enemiesMap = new HashMap<>();
		terrainMap = new HashMap<>();
		bulletMap = new HashMap<>();
		isPrevOrientationRight = null;
		fireRate = 0;
		timerCount = 0;
		isGamePaused = false;
		
		//audio = audio.getInstance();
		//audio.playSoundWithOptions("sounds", "r2d2.mp3", true);
		try {
			fileReader = new FileReader(level);
		} catch (FileNotFoundException e) {
			System.out.println("No Level File for Level: " + level);
		}
		
		setupTerrain();
		setupCollectables();
		setupPlayer();
		setupGUI();
		setupEnemies();
		program.setupTimer(30);
		player.startTimer();

		

		fileReader = null;
	//	setupGameOverScreen();
}

	@Override
	public void hideContents() {
		program.removeAll();
		player.deleteTimer();
		stars = 0;
		background = null;
		player = null;
		playerImage = null;
		dimension = null;
		collectablesMap = null;
		enemiesMap = null;
		terrainMap = null;
		bulletMap = null;
		keyList = null;
		victoryBorder = null;
		victory = null;
		nextLevelButton = null;
		restartButton = null;
		mainMenuButton = null;
		pauseBorder = null;
		pause = null;
		resumeButton = null;
		starGImage = null;
		heartGImage = null;
		System.gc();
	
		
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
		
		if (!keyList.contains(keyCode) && playerImage != null && isGamePaused == false) {
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
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == mainMenuButton) {
			program.switchToMenu();
		}
		else if(obj == nextLevelButton) {

	
			program.switchToGame(1);
	}
		else if(obj == restartButton) {

			
			System.out.println("Restart");
	program.switchToGame(level);
		}
		else if (obj == resumeButton) {
			unpauseGameScreen();
		}
	}
	
	@Override
	public void performAction(ActionEvent e) {
		if (playerImage != null && isGamePaused == false) { 
			timerCount++;
			playerImage.setLocation(player.getX(), player.getY());
			updateBullet();
			enemyAwareness();
			doEnemyActions();
			playerMovement();
			playerWeapon();
			
			
			if (keyList.contains(80)) {
				setupPauseGameScreen();
			}
			
			if (player.getY() + playerImage.getHeight()/2 > dimension.getHeight() || player.isDead()) {
				System.out.println("player is dead");
				setupGameOverScreen();
			}

			
		}
	}
	
	/**
	 * Changes the player's image depending on it orientation and weapon
	 */
	private void changePlayerImage() {
		if (player.weapon != null) {
			switch (player.weapon.getWeaponType()) {
			case MELEE: {
				if (player.isRightOrientation) {playerImage.setImage("/Images/rightPlayerSword.png");}
				else {playerImage.setImage("/Images/leftPlayerSword.png");}
				playerWidth = (int)playerImage.getWidth();
				return;
			}
			case HANDHELD:
				if (player.isRightOrientation) {playerImage.setImage("/Images/rightPlayerGun.png");}
				else {playerImage.setImage("/Images/leftPlayerGun.png");}
				playerWidth = (int)playerImage.getWidth();
				return;
			default:
				System.out.println("Invalid weapon type : changePlayerImage()");
				return;
			}
		}
		else {
			if (player.isRightOrientation) {
				playerImage.setImage("/Images/rightPlayer.png");
				playerWidth = (int)playerImage.getWidth();
				return;
			}
			else {
				playerImage.setImage("/Images/leftPlayer.png");
				playerWidth = (int)playerImage.getWidth();
				return;
			}
		}
	}
	
	/**
	 * Method for player's movement
	 */
	private void playerMovement() {
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
		if(player.checkOrientation(xVel)) {
			changePlayerImage();
		}
		
		//jumping
		if (keyList.contains(87)) {
			player.turnOnJumping();
		}
		
		// Method call for player Collision
		if (checkCollision()) {
			if (isPrevOrientationRight == player.isRightOrientation) {
				xVel = 0;
			} else if (isPrevOrientationRight != player.isRightOrientation) {
				player.isOnWall = false;
			}
		}
		
		// Move all objects right when player moves left
		if (player.getX() <= (int)dimension.getWidth()*.25 && xVel < 0) {
			for (Entry<GImage, Terrain> entry : terrainMap.entrySet()) {
				GImage key = entry.getKey();
				key.setLocation(key.getX()-xVel, key.getY());
			}
			for (Entry<GImage, Collectable> entry : collectablesMap.entrySet()) {
				GImage key = entry.getKey();
				key.setLocation(key.getX()-xVel, key.getY());
			}
			for (Entry<GImage, Enemy> entry : enemiesMap.entrySet()) {
				GImage key = entry.getKey();
				Enemy val = entry.getValue();
				
				val.moveXAxis(-xVel);
				if (val.getAwareness()) {key.setLocation(val.getX(), val.getY());}
			}
			for (Entry<GImage, Bullet> entry : bulletMap.entrySet()) {
				GImage key = entry.getKey();				
				key.move(-xVel, 0);
			}
			
		} // Move all objects left when player moves right
		else if (player.getX()+playerImage.getWidth() >= (int)dimension.getWidth()*.75 && xVel > 0) {
			for (Entry<GImage, Terrain> entry : terrainMap.entrySet()) {
				GImage key = entry.getKey();
				key.setLocation(key.getX()-xVel, key.getY());
			}
			for (Entry<GImage, Collectable> entry : collectablesMap.entrySet()) {
				GImage key = entry.getKey();
				key.setLocation(key.getX()-xVel, key.getY());
			}
			for (Entry<GImage, Enemy> entry : enemiesMap.entrySet()) {
				GImage key = entry.getKey();
				Enemy val = entry.getValue();
				val.moveXAxis(-xVel);
				if (val.getAwareness()) {
					key.setLocation(val.getX(), val.getY());
				}
			}
			for (Entry<GImage, Bullet> entry : bulletMap.entrySet()) {
				GImage key = entry.getKey();
				key.move(-xVel, 0);
			}
		}
		else {
			player.move(xVel, 0);
		}	
	}
	
	/**
	 * Checks if "Space" has been pressed to shoot a bullet/melee wave
	 */
	private void playerWeapon() {
		// adding a bullet on the screen when pressing Space
		if (keyList.contains(32) && player.weapon != null && fireRate <= 0) {
			switch (player.weapon.wType) {
			case HANDHELD: {
				Bullet bullet = player.weapon.attack(new GPoint(player.getX(), player.getY()), player.isRightOrientation);
				GImage image =  new GImage("/Images/rightBullet.png", bullet.getX(), bullet.getY());
				if (!player.isRightOrientation) {image.setImage("/Images/leftBullet.png");}
				bulletMap.put(image, bullet);
				program.add(image);
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
				program.add(image);
				fireRate = 35;
				break;
			default:
				System.out.println("Unknown Weapon Type");
			}
		}
		if (player.weapon != null) {fireRate--;}
	}
	
	/**
	 * ONLY Checks player's left, right, top, and bottom collision. work hand-on-hand with objectPlayerCollision()
	 * @return true if player is colliding with a wall. False otherwise
	 */
	private boolean checkCollision() {
		// was 6 before
		if (objectPlayerCollision(new GObject[] {program.getElementAt(player.getX()+.333*playerWidth, player.getY()-4),
			program.getElementAt(player.getX()+.667*playerWidth, player.getY()-4)})) {
				GObject obj = program.getElementAt(player.getX() + playerWidth/2, player.getY()-4);
				player.turnOffJumping();
				if (obj != null && obj != background) {				
					player.setY((int)obj.getY()+(int)obj.getHeight()+1);
				}
		}
			
		
		// functionality for ground detection
		if(objectPlayerCollision(new GObject[]{program.getElementAt(player.getX()+2, player.getY() + 52), // was 54 before
		   program.getElementAt(player.getX() + (playerWidth-2), player.getY() + 52)})) {
			

			player.isInAir = false;
			GObject obj = program.getElementAt(player.getX() + 5, player.getY() + 53); // was 55 before
			GObject obj2 = program.getElementAt(player.getX() + (playerWidth-5), player.getY()+53);
			

			
			//obj != null
			if (obj != null && obj != background) {				
				player.setY((int)obj.getY()-51);
			}
			//obj2 != null
			else if (obj2 != null && obj2 != background) {
				player.setY((int)obj2.getY()-51);
			} 
		}
		else {
			player.isInAir = true;
		}
			

	// functionality for wall detection 		
		if (objectPlayerCollision(new GObject[] {program.getElementAt(player.getX()-6, player.getY()),
		    program.getElementAt(player.getX()-6, player.getY()+50)})) {
			
			GObject obj = program.getElementAt(player.getX() - 6, player.getY()+25);
			if (obj != null && obj != background) {		
				player.setX((int)obj.getX()+(int)obj.getWidth());
			}
			isPrevOrientationRight = false;
			player.isOnWall = true;
			return true;
		}
		else if(objectPlayerCollision(new GObject[] {program.getElementAt(player.getX()+playerWidth+6, player.getY()),
				program.getElementAt(player.getX()+playerWidth+6, player.getY() + 50)})) {
			
			GObject obj = program.getElementAt(player.getX()+playerWidth+6, player.getY()+25);
			if (obj != null && obj != background) {				
				player.setX((int)obj.getX()-playerWidth);
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
			if(gImage == background) {nullCount++; continue;}

			if (collectablesMap.containsKey(gImage)) {
				//Checks Map for which collectable is associated to which gImage and switches to perform
				//effects accordingly
				switch(collectablesMap.get(gImage).getCType()) {
					case HEART:
						//Increases player hearts by 1 while hearts < 3 (The max amount of hearts)}
						player.setHearts(player.getHearts()+1);						
						heartGImage.setImage("/Images/heart UI_"+ player.getHearts() + ".png");
						break;
					case CHEESE:
						if (stars >= 1) {							
							setupWinningScreen();
						}
						return false;
					case STAR:	
						starGImage.setImage("/Images/star UI_" + ++stars + ".png");
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
				program.remove(gImage);
				return false;
			}
			else if (enemiesMap.containsKey(gImage)) {
				if (!(player.getHitCooldown() > 0)) {				
					player.setHearts(player.getHearts()-1);
					heartGImage.setImage("/Images/heart UI_" + player.getHearts() + ".png");
					player.resetHitCooldown();
				}
				return false;
			}
			else if (bulletMap.containsKey(gImage)) {
				return false;
			}
			else if (terrainMap.containsKey(gImage) && terrainMap.get(gImage).getTerrainType() == TerrainType.SPIKE) {
				player.setHearts(0);
			} 
		} 
		if (nullCount == arr.length) {return false;}	
		return true;
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
			val.actionPerformed(null);
		}
		
		for (GImage gImage : keysToRemove) {
			bulletMap.remove(gImage);
			program.remove(gImage);
		}
		
	}
	
	/**
	 * checks collision points of a bullet 
	 * @param key the GImage associated with the bullet object
	 * @param val the bullet object itself
	 * @return true if bullet hits a valid object
	 */
	private boolean checkBulletCollision(GImage key, Bullet val) {
		GObject obj1 = program.getElementAt(key.getX()-2, key.getY());
		GObject obj2 = program.getElementAt(key.getX()-2, key.getY()+key.getHeight());
		GObject obj3 = program.getElementAt(key.getX()+key.getWidth()+2, key.getY());
		GObject obj4 = program.getElementAt(key.getX()+key.getWidth()+2, key.getY()+key.getHeight());
		
		
		if (val.isFriendly() == false && (obj1 == playerImage || obj2 == playerImage || obj3 == playerImage || obj4 == playerImage)) {
			if (!(player.getHitCooldown() > 0)) {				
				player.setHearts(player.getHearts()-1);
				heartGImage.setImage("/Images/heart UI_" + player.getHearts() + ".png"); 
				player.resetHitCooldown();
			}
			return true;
		}

		if (val.isFriendly() && ((enemiesMap.containsKey(obj1)) || enemiesMap.containsKey(obj2)  || enemiesMap.containsKey(obj3)  || enemiesMap.containsKey(obj4))){
			Enemy enemy  = null;
			GObject temp = null;
			
			System.out.println(obj1);
			System.out.println(obj2);
			System.out.println(obj3);
			System.out.println(obj4);
			
			if (enemiesMap.containsKey(obj1)) {
				enemy = enemiesMap.get(obj1);
				temp = obj1;
			}
			else if (enemiesMap.containsKey(obj2)) {
				enemy = enemiesMap.get(obj2);
				temp = obj2;
			}
			else if (enemiesMap.containsKey(obj3)) {
				enemy = enemiesMap.get(obj3);
				temp = obj3;
			}
			else if (enemiesMap.containsKey(obj4)) {
				enemy = enemiesMap.get(obj4);
				temp = obj4;
			}
			
			enemy.setLives(enemy.getLives()-1);
			if (enemy.isDead()) {program.remove(temp); enemiesMap.remove(temp);}
			return true;
		}
	
		if(val.isMelee() == false && (terrainMap.containsKey(obj1) || terrainMap.containsKey(obj2) ||  terrainMap.containsKey(obj3) || terrainMap.containsKey(obj4))) {				
			return true;
		}
		return false;
		
	}
	
	
	private void doEnemyActions() {
		
		for (Entry<GImage, Enemy> entry : enemiesMap.entrySet()) {
			Enemy each = entry.getValue();
			GImage eachImage = entry.getKey();
			if (each.getAwareness() && (each.getEnemyType() == EnemyType.BEETLE || each.getEnemyType() == EnemyType.FLOWER)) {
				if (player.getX() <= each.getX()) {each.setIsRightOrientation(false);}
				else {each.setIsRightOrientation(true);}
				
				if (timerCount - each.getLastShot() >= 150) {
					each.setLastShot(timerCount);
					Bullet[] bullets = each.attack();
					if (bullets != null) {
						for (int i = 0; i < bullets.length; i++) {
							Bullet b = bullets[i];
							GImage bImage = new GImage("/Images/rightBullet.png", b.getX(),b.getY());
							bulletMap.put(bImage,b);
							program.add(bImage);
						}
					} 
				}
			}
			else {
				each.actionPerformed(null);
				eachImage.setLocation(each.getX(),each.getY());
				
				if(bulletMap.containsKey(program.getElementAt(each.getX()-2, each.getY()+52))|| program.getElementAt(each.getX()-2, each.getY()+52) == background || 
				   program.getElementAt(each.getX()-2, each.getY()+52) == null || terrainMap.containsKey(program.getElementAt(each.getX()-2, each.getY()))) {
					each.setIsRightOrientation(true);
				} 
				else if (bulletMap.containsKey(program.getElementAt(each.getX()+52, each.getY()+52)) || program.getElementAt(each.getX()+52, each.getY()+52) == background || program.getElementAt(each.getX()+52, each.getY()+52) == null || terrainMap.containsKey(program.getElementAt(each.getX()+52, each.getY()))) {
					each.setIsRightOrientation(false);
				}

			}
		}
	}
	
	private void enemyAwareness() {
		int ePointx, ePointy;
		for(Entry<GImage,Enemy> entry : enemiesMap.entrySet()) {

			Enemy all = entry.getValue();
			ePointx = all.getX(); 		
			ePointy = all.getY();
 
			if(all.getEnemyType() != EnemyType.BEETLE && all.getEnemyType() != EnemyType.FLOWER) {continue;}
 
			if (Math.abs(player.getX()-ePointx) <= 400 && Math.abs(player.getY()-ePointy) <= 150) {
				all.switchAwareness(true);
			}
			else {
				all.switchAwareness(false);
			}
		}
	}
	
	private void stopGame() {
		isGamePaused = true;
		player.stopTimer();
//		for(Entry<GImage,Enemy> entry : enemiesMap.entrySet()) {
//			entry.getValue().stopTimer();
//		}
//		
//		for (Entry<GImage, Bullet> entry : bulletMap.entrySet()) { 
//			entry.getValue().stopTimer();
//		}

	}
	private void continueGame() {
		isGamePaused = false;
		player.startTimer();
//		for(Entry<GImage,Enemy> entry : enemiesMap.entrySet()) {
//			entry.getValue().startTimer();
//		}
//		
//		for (Entry<GImage, Bullet> entry : bulletMap.entrySet()) { 
//			entry.getValue().startTimer();
//		}
	}
	
	public void unpauseGameScreen() {
		program.remove(pauseBorder);
		program.remove(pause);
		program.remove(resumeButton);
		program.remove(mainMenuButton);
		pauseBorder = null;
		pause = null;
		resumeButton = null;
		mainMenuButton = null;
		continueGame();
	}
	
	public void setupPauseGameScreen() {
		if (pauseBorder != null) {
			System.out.println("help");return;}
		stopGame();
		
		pauseBorder = new GRect(dimension.getWidth()/2-700/2, dimension.getHeight()/2-400/2, 700, 400);
		pauseBorder.setFillColor(Color.decode("#5f6c5a"));
		pauseBorder.setFilled(true);
		program.add(pauseBorder);
		
		pause = new GParagraph("Game Paused", 0, 0);
		pause.setFont("Arial-Bold-Italic-60");
		pause.setColor(Color.white);
		pause.setLocation(dimension.getWidth()/2-pause.getWidth()/2, pauseBorder.getY()+pause.getHeight());
		program.add(pause);
		
		resumeButton = new GButton("Resume", dimension.getWidth()/2-150, pause.getY()+75, 300, 75, Color.decode("#879383"));
		program.add(resumeButton);
		
		mainMenuButton = new GButton("Main Menu", dimension.getWidth()/2-150, resumeButton.getY()+100, 300, 75, Color.decode("#879383"));
		program.add(mainMenuButton);
		
	}
	
	public void setupWinningScreen() {
		stopGame();
		
		victoryBorder = new GRect(dimension.getWidth()/2-700/2, dimension.getHeight()/2-400/2, 700, 400);
		victoryBorder.setFillColor(Color.decode("#5f6c5a"));
		victoryBorder.setFilled(true);
		program.add(victoryBorder);
		
		victory = new GParagraph("Victory!" , 0, 0);
		victory.setFont("Arial-Bold-80");
		victory.setColor(Color.white);
		victory.setLocation(dimension.getWidth()/2-victory.getWidth()/2, victoryBorder.getY()+victory.getHeight());
		program.add(victory);
		
		nextLevelButton = new GButton("Next Level", dimension.getWidth()/2-110, victory.getY()+victory.getHeight()/2, 220, 70, Color.decode("#879383"));
		program.add(nextLevelButton);
		
		mainMenuButton = new GButton("Main Menu", dimension.getWidth()/2-187.5, nextLevelButton.getY()+nextLevelButton.getHeight()+10, 375, 90, Color.decode("#879383"));
		program.add(mainMenuButton);		
	}
	
	public void setupGameOverScreen() {
		stopGame();
		
		gameOverBorder = new GRect(dimension.getWidth()/2-700/2, dimension.getHeight()/2-400/2, 700, 400);
		gameOverBorder.setFillColor(Color.decode("#5f6c5a"));
		gameOverBorder.setFilled(true);
		program.add(gameOverBorder);
		
		//over = new GParagraph("You Suck!" , 0, 0);
		over = new GParagraph("Skill Issue Bruv" , 0, 0);
		over.setFont("Arial-Bold-80");
		over.setColor(Color.RED);
		over.setLocation(dimension.getWidth()/2-over.getWidth()/2, gameOverBorder.getY()+over.getHeight());
		program.add(over);
		
		restartButton = new GButton("Restart", dimension.getWidth()/2-110, over.getY()+over.getHeight()/2, 220, 70, Color.decode("#879383"));
		
		program.add(restartButton);
		
		mainMenuButton = new GButton("Main Menu", dimension.getWidth()/2-187.5, restartButton.getY()+restartButton.getHeight()+10, 375, 90, Color.decode("#879383"));
		program.add(mainMenuButton);		
	}
	
	
	/**
	 * Sets up the main GUI on the main window
	 */
	private void setupGUI() {
		heartGImage = new GImage("/Images/heart UI_3.png", 50, 50);
		starGImage = new GImage("/Images/star UI_0.png", 0, 50);
		starGImage.setLocation(dimension.getWidth()-starGImage.getWidth()-50, starGImage.getY());
				
		program.add(heartGImage);
		program.add(starGImage);
	}
	
	private void setupPlayer() {
		player = fileReader.getPlayer();
		playerImage = fileReader.getplayerImage();
		playerWidth = (int)playerImage.getWidth();
		program.add(playerImage);
	}
	
	/**
	 * Sets up the Terrain on level
	 */
	private void setupTerrain() {
		
		background = new GImage("/Images/forestBackground.jpeg");
		background.setSize(dimension.getWidth(), dimension.getHeight());
		program.add(background);
		
		
		terrainMap = fileReader.getTerrainMap();
		for (Entry<GImage, Terrain> entry : terrainMap.entrySet()) {
			GImage key = entry.getKey();
			program.add(key);
		}
//		Terrain terrain = new Terrain(0, 500, 800, 500, TerrainType.GRASS);
//		GImage image = new GImage(terrain.getTerrainType().toString(), terrain.getX(), terrain.getY());
//		image.setSize((double)terrain.getWidth(), (double)terrain.getHeight());
//		program.add(image);
//		terrainMap.put(image, terrain);
//		
//		terrain = new Terrain(900, 700, 800, 200, TerrainType.GRASS);
//		image = new GImage(terrain.getTerrainType().toString(), terrain.getX(), terrain.getY());
//		image.setSize((double)terrain.getWidth(), (double)terrain.getHeight());
//		program.add(image);
//		terrainMap.put(image, terrain);
//		
//		terrain = new Terrain(700, 300, 200, 100, TerrainType.DIRT);
//		image = new GImage(terrain.getTerrainType().toString(), terrain.getX(), terrain.getY());
//		image.setSize((double)terrain.getWidth(), (double)terrain.getHeight());
//		program.add(image);
//		terrainMap.put(image, terrain);
	}
	
	// sets up the collectables on level
	private void setupCollectables() {
		collectablesMap = fileReader.getCollectableMaps();
		
		for (Entry<GImage, Collectable> entry : collectablesMap.entrySet()) {
			GImage key = entry.getKey();
			program.add(key);
		}

		
//		Collectable collectable = new Collectable(300, 450, CollectableType.HEART);
//		GImage image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
//		collectablesMap.put(image, collectable);
//		program.add(image);
//		
//		collectable = new Collectable(400, 450, CollectableType.HANDHELD);
//		image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
//		collectablesMap.put(image, collectable);
//		program.add(image);
//		
//		collectable = new Collectable(800, 250, CollectableType.MELEE);
//		image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
//		collectablesMap.put(image, collectable);
//		program.add(image);
//		
//		collectable = new Collectable(800, 650, CollectableType.STAR);
//		image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
//		collectablesMap.put(image, collectable);
//		program.add(image);
//
//		collectable = new Collectable(1500, 600, CollectableType.CHEESE);
//		image = new GImage(collectable.toString(), collectable.getX(), collectable.getY());
//		collectablesMap.put(image, collectable);
//		program.add(image);
		
	}
	
	
	private void setupEnemies() {
		enemiesMap = fileReader.getEnemyMap();

		for (Entry<GImage, Enemy> entry : enemiesMap.entrySet()) {
			GImage key = entry.getKey();
			program.add(key);	
		}
		
//		Enemy tempEnemy;
//		tempEnemy = new Enemy (500,450,EnemyType.FLOWER);
//		GImage image = new GImage(tempEnemy.getEnemyType().toString(),tempEnemy.getX(),tempEnemy.getY());
//		enemiesMap.put(image, tempEnemy);
//		program.add(image);
//		
//		tempEnemy = new Enemy (750,250,EnemyType.WORM);
//		image = new GImage(tempEnemy.getEnemyType().toString(),tempEnemy.getX(),tempEnemy.getY());
//		program.add(image);
//		enemiesMap.put(image, tempEnemy);
//			
//		tempEnemy = new Enemy (950,650,EnemyType.SPIDER);
//		image = new GImage(tempEnemy.getEnemyType().toString(),tempEnemy.getX(),tempEnemy.getY());
//		enemiesMap.put(image, tempEnemy);
//		program.add(image);
		
		
	}


}


