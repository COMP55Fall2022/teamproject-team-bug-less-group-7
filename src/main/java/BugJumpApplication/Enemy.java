package BugJumpApplication;

import javax.swing.Timer;
import acm.program.GraphicsProgram;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import acm.util.RandomGenerator;

public class Enemy {
	
	private Timer time;
	private int xAxis;
	private int yAxis;
	private boolean isRightOrientation;
	private boolean willAttack;
	private boolean hitBarrier;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private EnemyType eType;
	
	
	// Enemy constructor; determines type, position, and default variables
	public Enemy(int x, int y, EnemyType eType) {
		this.xAxis = x;
		this.yAxis = y;
		this.eType = eType;
		this.willAttack = false;
		this.isRightOrientation = true;
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
	
	//TODO: find a way to get the enemies to notice the player and act accordingly
	public void switchAwareness() {
		if (willAttack == false) {
			willAttack = true;
		}
		else {
			willAttack = false;
		}
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
		if (hitBarrier == true) {
			if (isRightOrientation == true) {
				isRightOrientation = false;
			}
			else {
				isRightOrientation = true;
			}
		}
	}
	//TODO: make barriers for enemy to hit.
	
	public void startTimer() {
		time.start();
	}
	
	public void stopTimer() {
		time.stop();
	}
	
	
	//TODO: implement attack (bullet)
}
