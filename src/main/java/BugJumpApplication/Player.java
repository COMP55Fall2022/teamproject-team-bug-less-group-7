package BugJumpApplication;


public class Player {
	int yAxis;
	int xAxis;
	boolean isRightOrientation;
	boolean isJumping;
	
	
	public void Player(int x,int y) {
		
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
	
}
