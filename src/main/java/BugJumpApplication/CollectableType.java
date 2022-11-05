package BugJumpApplication;

public enum CollectableType {
HEART,STAR,CHEESE,HANDHELD,MELEE,NONE;
	
	public CollectableType getType(int num) {
	
	switch(num)	{
		case 0:
			return CollectableType.HEART; 
		case 1:
			return CollectableType.STAR; 
		case 2:
			return CollectableType.CHEESE; 
		case 3:
			return CollectableType.HANDHELD; 
		case 4:
			return CollectableType.MELEE; 	
		}
	return NONE;
		
	}
	public String toString() {
		switch(this) {
			case HEART: return "heart";
			case STAR: return "star";
			case CHEESE: return "cheese";
			case HANDHELD: return "handheld";
			case MELEE: return "melee";
		}
		return "n/a";
	}
	
}
