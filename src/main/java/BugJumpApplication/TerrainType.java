package BugJumpApplication;

public enum TerrainType {
	SPIKE, GRASS, DIRT,;
	int num;
	public TerrainType getType() {
		switch(num){
			case 0: return TerrainType.SPIKE;
			case 1: return TerrainType.GRASS;
			case 2: return TerrainType.DIRT;
		}
	
	return null;
		
	}
}
