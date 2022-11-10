package BugJumpApplication;

public enum WeaponType {
	HANDHELD, MELEE, NONE;
	
	public String toString() {
		switch(this) {
		case HANDHELD: return "handheld";
		case MELEE: return "melee";
		case NONE: return "none";
		}
		return "n/a";
	}
	public WeaponType getType(int num) {
		switch(num) {
		case 0: return WeaponType.HANDHELD;
		case 2: return WeaponType.MELEE;
		case 3: return WeaponType.NONE;
		
		}
		return null;

}}
