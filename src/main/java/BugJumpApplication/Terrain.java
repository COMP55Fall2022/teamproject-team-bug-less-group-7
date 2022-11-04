package BugJumpApplication;

public class Terrain {
	private int width;
	private int height;
	private int xAxis;
	private int yAxis;
	
	TerrainType tType;
	EnemyType eType;
		
	private void Terrain(int x, int y, int width, int height, TerrainType tType) {

		this.yAxis = y;
		this.xAxis = x;
		this.height = height;
		this.width = width;
		this.tType = tType;
	}
	
	public EnemyType getEnemyType() {
		return eType;
	}
	
	public int getY() {
		return this.yAxis;
	}
	
	public int getX() {
		return this.xAxis;
	}
}