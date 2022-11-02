package BugJumpApplication;

import javax.swing.Timer;

import acm.program.GraphicsProgram;

import java.awt.event.ActionEvent;
public class Player extends GraphicsProgram {
	//TODO: Needs weapon
	int yAxis;
	int xAxis;
	boolean isRightOrientation;
	boolean isJumping;
	Timer timer = new Timer(25, this);
	
	//Constructor for player.java that defaults the variables for the player and sets the x and y
	//to the inputed values.
	public Player(int x,int y) {
		//TODO: instantiate weapon
		xAxis = x;
		yAxis = y;
		isRightOrientation = true;
		isJumping = false;
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
		if (isJumping) {
			yAxis += 5;
		}
	
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
