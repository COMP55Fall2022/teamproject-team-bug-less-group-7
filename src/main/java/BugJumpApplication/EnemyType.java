package BugJumpApplication;

public enum EnemyType {
	FLOWER,SPIDER,WORM,BEETLE, NONE;
	
	public String toString() {
		switch(this) {
			case FLOWER: return "/Images/sunflower.png";
			case SPIDER: return "/Images/spider.png";
			case WORM: return "/Images/rightWorm.png";
			case BEETLE: return "/Images/rightBeetle.png";
		}
		return "n/a";
	}
	
	public EnemyType getType(int num) {
		switch(num) {
			case 0: return EnemyType.FLOWER;
			case 1: return EnemyType.SPIDER;
			case 2: return EnemyType.WORM;
			case 3: return EnemyType.BEETLE;
		}
		return null;
		
	}
}
