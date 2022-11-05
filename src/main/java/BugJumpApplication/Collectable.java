package BugJumpApplication;

public class Collectable {
	int xAxis;
	int yAxis;
	CollectableType cType;
	
	public Collectable(int x, int y, CollectableType cType) {
		this.cType = cType;
		this.xAxis = x;
		this.yAxis = y;
	}
	
	public CollectableType getCType() {
		return cType;
	}
	
	public int getY() {
		return this.yAxis;
	}
	
	public int getX() {
		return this.xAxis;
	}

	
}
