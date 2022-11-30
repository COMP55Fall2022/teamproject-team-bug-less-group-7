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

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.cType.toString();
	}
	
}
