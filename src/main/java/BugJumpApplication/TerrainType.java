package BugJumpApplication;

public enum TerrainType {
	SPIKE, GRASS, DIRT;
	
	public String toString() {
		switch(this){
			case SPIKE: return "spike";
			case GRASS: return "grass";
			case DIRT: return "dirt";
		}

		return "n/a";
	}
		
	public TerrainType getType(int num) {
		switch(num) {
		case 0: return TerrainType.SPIKE;
		case 1: return TerrainType.GRASS;
		case 2: return TerrainType.DIRT;
		}
	return null;
		
	}
}
