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
	
	private int initialHeight;
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
		initialHeight = y;
		timer.start();
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		return;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (isInAir) {
			yAxis += 6;
		}
		jump();
	}
	
	public void jump() {
		System.out.println(isInAir);
		if(!isJumping) {
			return;
		}		
//		System.out.println(yAxis);
//		System.out.println(initialHeight - JUMP_HEIGHT);
		if (yAxis > initialHeight - JUMP_HEIGHT) {
			yAxis -= 12;
		} else {
			turnOffJumping();
		}
	}

	public void turnOnJumping() {
		if(!isJumping && !isInAir || isOnWall) {
			isJumping = true;
			initialHeight = yAxis;
		}
	}
	
	public void turnOffJumping() {
		initialHeight = 0;
		isJumping = false;
		isOnWall = false;
	}
	
	public int getY(){
		return this.yAxis;
	}
	
	
	public int getX() {
		return this.xAxis;
	}
	
	public void move(int dx, int dy) {
		 if(dx < 0) {
			 isRightOrientation = false;
		 }
		 else {
			isRightOrientation = true;
		}
		 this.xAxis += dx;
		 this.yAxis += dy;
	 }
	 
	 public boolean getIsJumping() {
		 return this.isJumping;
	 }
	
}
