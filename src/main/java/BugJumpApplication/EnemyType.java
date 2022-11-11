package BugJumpApplication;

public enum EnemyType {
	FLOWER,SPIDER,WORM,BEATLE;
	
	public String toString() {
		switch(this) {
			case FLOWER: return "flower";
			case SPIDER: return "spider";
			case WORM: return "worm";
			case BEATLE: return "beatle";
		}
		return "n/a";
	}
}
