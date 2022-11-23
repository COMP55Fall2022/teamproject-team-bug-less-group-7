package BugJumpApplication;

import java.awt.event.ActionEvent;

import acm.program.GraphicsProgram;

public class Bullet extends GraphicsProgram{
	private int runoutTimer;
	private int xAxis;
	private int yAxis;
	private int vel;
	private int theta;
	private boolean isFriendly;
	
	public Bullet(int x, int y, int vel, int theta, boolean isFriendly) {
		this.xAxis = x;
		this.yAxis = y;
		this.vel = vel;
		this.theta = theta;
		this.isFriendly = isFriendly;
		this.runoutTimer = 100;
	}
	
	public Bullet(int x, int y, int vel, int theta, boolean isFriendly, int timer) {
		this.xAxis = x;
		this.yAxis = y;
		this.vel = vel;
		this.theta = theta;
		this.isFriendly = isFriendly;
		this.runoutTimer = timer;
	}
	
	@Override
	public void run() {
		return;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {;
		runoutTimer--;
		
	}
	
	public int getX() {
		return this.xAxis;
	}
	
	public int getY() {
		return this.yAxis;
	}
	
	public int getTheta() {
		return this.theta;
	}
	
	public int getVelocity() {
		return this.vel;
	}
	
	public boolean isFriendly() {
		return this.isFriendly;
	}
	
	public boolean hasTimerRunout() {
		if (runoutTimer <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getTimer() {
		return runoutTimer;
	}
}
