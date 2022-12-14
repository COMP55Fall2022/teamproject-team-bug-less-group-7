package BugJumpApplication;

import acm.program.GraphicsProgram;
import java.awt.event.ActionEvent;

public class Enemy extends GraphicsProgram{
	
	private int xAxis;
	private int yAxis;
	private int xVel;
	private int lastShotTimer;
	private int lives = 3;

	private boolean isRightOrientation;
	private boolean willAttack;
	
	private EnemyType eType;

	
	
	// Enemy constructor; determines type, position, and default variables
	public Enemy(int x, int y, EnemyType eType) {
		this.xAxis = x;
		this.yAxis = y;
		this.eType = eType;
		this.willAttack = false;
		this.isRightOrientation = true;
		lastShotTimer = 0;
		determineSpeed();
	}
	
	private void determineSpeed() {
		switch (eType) {
		case BEETLE: {
			this.xVel = 8;
			break;
		}
		case SPIDER:
			this.xVel = 9;
			break;
		case WORM:
			this.xVel = 3;
			break;
		default:
			this.xVel = 5;
		}
	}
	
	@Override
	public void run() {
		return;
	}
	
	//getters and setters
	public EnemyType getEnemyType() {
		return eType;
	}
	
	public int getX() {
		return xAxis;
	}
	
	public void setX(int x) {
		xAxis = x;
	}
	
	public int getY() {
		return yAxis;
	}
	
	public boolean getOrientation() {
		return isRightOrientation;
	}
	
	public boolean getAwareness() {
		return willAttack;
	}
	
	public int getLastShot() {
		return lastShotTimer;
	}
	
	public void setLastShot(int lst) {
		lastShotTimer = lst;
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public boolean isDead() {
		if (lives <= 0) {return true;}
		return false;
	}
	//TODO: find a way to get the enemies to notice the player and act accordingly
	public void switchAwareness(boolean input) {
		willAttack = input;
	}
	
	public void setIsRightOrientation(boolean condition) {
		isRightOrientation = condition;
	}
	
	public void moveXAxis(int val) {
		xAxis += val;
	}
	public void actionPerformed(ActionEvent e) {
		if (willAttack == false) {
			if (isRightOrientation == true) {
				xAxis = xAxis + xVel;
			}
			else {
				xAxis = xAxis - xVel;
			}
		}
	}
	
	//TODO: implement attack (bullet)
	public Bullet[] attack() {
		//Array of bullets to be returned
		Bullet[] bulletArr;
		switch(eType) {
			case SPIDER:
				return null;
			case WORM:
				return null;
			//shoots 5 bullets in a flower shape, left, left-up, up, right-up, and right
			case FLOWER:
				bulletArr = new Bullet[5];
				for (int i = 0; i < bulletArr.length; i++) {
						bulletArr[i] = new Bullet(xAxis, yAxis, 5,180 - i*45,false);
				}
				break;
			//shoots 1 bullet in the direction its facing horizontally
			case BEETLE:
				bulletArr = new Bullet[1];
				if (isRightOrientation) {
					bulletArr[0] = new Bullet(xAxis,yAxis,10,0, false);
				} else {
					bulletArr[0] = new Bullet(xAxis,yAxis,10,180,false);
				}
				break;
			//default
			default:
				bulletArr = new Bullet[0];
				System.out.println("INVALID ENEMY TYPE");
		}
		return bulletArr;
	}
}
