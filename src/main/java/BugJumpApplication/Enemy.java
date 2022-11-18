package BugJumpApplication;

import javax.swing.Timer;
import acm.program.GraphicsProgram;
import java.awt.event.ActionEvent;
import acm.util.RandomGenerator;

public class Enemy extends GraphicsProgram{
	
	private Timer timer = new Timer(35, this);
	private int xAxis;
	private int yAxis;
	private int lastShotTimer;
	private int lives = 4;

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
		this.timer.start();
		lastShotTimer = 0;
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
	
	//TODO: find a way to get the enemies to notice the player and act accordingly
	public void switchAwareness(boolean input) {
		willAttack = input;
	}
	
	public void setIsRightOrientation(boolean condition) {
		isRightOrientation = condition;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (willAttack == false) {
			if (isRightOrientation == true) {
				xAxis = xAxis + 5;
			}
			else {
				xAxis = xAxis - 5;
			}
		}
	}
	
	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
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
			case BEATLE:
				bulletArr = new Bullet[1];
				if (isRightOrientation) {
					bulletArr[0] = new Bullet(xAxis,yAxis,5,0, false);
				} else {
					bulletArr[0] = new Bullet(xAxis,yAxis,5,180,false);
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
