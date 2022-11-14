package BugJumpApplication;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.program.GraphicsProgram;

public class Bullet extends GraphicsProgram{
	private Timer timer;
	private int runoutTimer = 100;
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
		this.timer = new Timer(25, this);
		timer.start();
	}
	
	public Bullet(int x, int y, int vel, int theta, boolean isFriendly, int timer) {
		this.xAxis = x;
		this.yAxis = y;
		this.vel = vel;
		this.theta = theta;
		this.isFriendly = isFriendly;
		this.timer = new Timer(25, this);
		runoutTimer = timer;
		this.timer.start();
	}
	
	@Override
	public void run() {
		System.out.println();
		return;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		xAxis += dx;
//		yAxis += dy;
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
	
	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
	}
	
}
