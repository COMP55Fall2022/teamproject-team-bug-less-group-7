package BugJumpApplication;

import javax.swing.Timer;

import acm.program.GraphicsProgram;

import java.awt.event.ActionEvent;
public class Player extends GraphicsProgram {
	private static final int JUMP_HEIGHT = 84;
	//TODO: Needs weapon
	int yAxis;
	int xAxis;
	boolean isRightOrientation;
	boolean isInAir;
	boolean isJumping;
	private int initialHeight;
	private int timerCounter;
	Timer timer = new Timer(25, this);
	
	//Constructor for player.java that defaults the variables for the player and sets the x and y
	//to the inputed values.
	public Player(int x,int y) {
		//TODO: instantiate weapon
		xAxis = x;
		yAxis = y;
		isRightOrientation = true;
		isInAir = true;
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
		if(!isJumping) {
			return;
		}		
		//yAxis += Math.pow(35+timerCounter++, 2) + intialHeight;
		if (yAxis > initialHeight - JUMP_HEIGHT) {
			System.out.println("called");
			yAxis -= 12;
		} else {
			turnOffJumping();
		}
	}

	public void turnOnJumping() {
		if(!isJumping && !isInAir) {
			isJumping = true;
			initialHeight = yAxis;
		}
	}
	
	public void turnOffJumping() {
		initialHeight = 0;
		timerCounter = 0;
		isJumping = false;
	}
	
	public int GetY(){
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
		 return isJumping;
	 }
	
}
