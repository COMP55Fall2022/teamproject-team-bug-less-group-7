package BugJumpApplication;

import acm.graphics.GPoint;

public class Weapon {
	private static final int BULLETWIDTH = 96;
	private static final int BULLETHEIGHT = 96;
	WeaponType wType;
	
	public Weapon(WeaponType wType){
		this.wType = wType;
	}
	
	public WeaponType getWeaponType() {
		return wType;
	}
	
	public Bullet attack(GPoint point, boolean isRightOrientation) {
		
		if (isRightOrientation == true) {
			
			return new Bullet((int)point.getX() + 60, (int)point.getY(), 1, 0, true);
		}
		else {
			return new Bullet((int)point.getX() - 10, (int)point.getY()+25, -1, 0, true);
		}
	}
	
}
