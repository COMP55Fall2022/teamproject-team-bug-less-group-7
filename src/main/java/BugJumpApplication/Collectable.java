package BugJumpApplication;

public class Collectable {
	static final private int width = 50;
	static final private int height = 50;
	
	private int xAxis;
	private int yAxis;
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

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	
}
