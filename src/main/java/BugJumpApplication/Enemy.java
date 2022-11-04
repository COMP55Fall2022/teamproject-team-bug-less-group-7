package BugJumpApplication;

import javax.swing.Timer;
import acm.graphics.*;
import acm.program.GraphicsProgram;

public class Enemy {
	
	
	Timer time;
	int width;
	int height;
	int xAxis;
	int yAxis;
	boolean isRightOrientation;
	boolean willAttack;
	EnemyType type;
	
	public Enemy(EnemyType eType, int x, int y) {
		this.type = eType;
		xAxis = x;
		yAxis = y;
		isRightOrientation = true;
	}
}
