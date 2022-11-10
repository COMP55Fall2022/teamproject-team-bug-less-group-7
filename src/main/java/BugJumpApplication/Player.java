package BugJumpApplication;

import javax.swing.Timer;

import acm.program.GraphicsProgram;

import java.awt.event.ActionEvent;
public class Player extends GraphicsProgram {
	private static final int JUMP_HEIGHT = 84;
	//TODO: Needs weapon
	private int yAxis;
	private int xAxis;
	boolean isRightOrientation;
	boolean isInAir;
	boolean isJumping;
	boolean isOnWall;
	
	private int initialTime;
	
	private int hearts;
	
	private int dy;

	private int timerCount;
	Timer timer = new Timer(25, this);
	//Constructor for player.java that defaults the variables for the player and sets the x and y
	//to the inputed values.
	public Player(int x,int y) {
		//TODO: instantiate weapon
		xAxis = x;
		yAxis = y;
		isRightOrientation = true;
		isInAir = true;
		isOnWall = false;
		initialTime = -1;
		timerCount = 0;
		
		hearts = 2;
		dy = 0;
		}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		return;
	}
	
	public void startTimer() {
		timer.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		timerCount++;
		if (isInAir && !isJumping) {
			if (initialTime == -1) {				
				initialTime = timerCount;
			}
			dy = (timerCount - initialTime);
		} 
		jump();
		yAxis += dy;
		if (!isInAir) {

			initialTime = -1;
			turnOffJumping();
			dy = 0;
			
		}
	}
	
	public void jump() {
		if(!isJumping) {
			return;
		}		
		
		dy = (timerCount - initialTime) - 15;
//		System.out.println("jump dy"+dy);
	}

	public void turnOnJumping() {
		if(!isJumping && !isInAir || isOnWall) {
			isJumping = true;
//			initialHeight = yAxis;
			initialTime = timerCount;
		}
	}
	
	public void turnOffJumping() {
//		initialHeight = 0;
		isJumping = false;
		isOnWall = false;
	}
	
	public int getY(){
		return this.yAxis;
	}
	
	public int getX() {
		return this.xAxis;
	}
	
	public void setY(int y) {
		yAxis = y;
	}
	
	public void setX(int x) {
		xAxis = x;
	}
	
	public int getHearts() {
		return hearts;
	}

	public void setHearts(int h) {
		hearts = h;
		if (hearts > 3) {
			hearts = 3;
		}
	}
	
	public void move(int dx, int dy) {
		checkOrientation(dx);
		 this.xAxis += dx;
		 this.yAxis += dy;
	 }
	 
	public void checkOrientation(int dx) {
	 if(dx < 0) {
		 isRightOrientation = false;
	 }
	 else if (dx > 0){
		isRightOrientation = true;
	}
}
	 public boolean getIsJumping() {
		 return this.isJumping;
	 }
	
}
