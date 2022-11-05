package BugJumpApplication;

import javax.swing.Timer;
import acm.program.GraphicsProgram;
import java.awt.event.ActionEvent;

public class Enemy {
	
	
	Timer time;
	private int xAxis;
	private int yAxis;
	private boolean isRightOrientation;
	private boolean willAttack;
	EnemyType type;
	
	
	// Enemy constructor; determines type, position, and default variables
	public Enemy(EnemyType eType, int x, int y) {
		this.type = eType;
		xAxis = x;
		yAxis = y;
		isRightOrientation = true;
		willAttack = false;
	}
	//getters and setters
	public EnemyType getEnemyType() {
		return type;
	}
	
	public int getX() {
		return xAxis;
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
		//TODO: implement (player affects enemies
	}
	
	public void startTimer() {
		time.start();
	}
	
	public void stopTimer() {
		time.stop();
	}
	
	
	//TODO: implement attack (bullet)
}
