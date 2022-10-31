package BugJumpApplication;


public class Player {
	//TODO: Needs weapon
	int yAxis;
	int xAxis;
	boolean isRightOrientation;
	boolean isJumping;
	
	//Constructor for player.java that defaults the variables for the player and sets the x and y
	//to the inputed values.
	public void Player(int x,int y) {
		//TODO: instantiate weapon
		xAxis = x;
		yAxis = y;
		isRightOrientation = true;
		isJumping = false;
	}
	
}
