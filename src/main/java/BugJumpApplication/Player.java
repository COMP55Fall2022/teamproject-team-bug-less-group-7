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
