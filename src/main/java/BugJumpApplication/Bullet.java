package BugJumpApplication;

import java.awt.event.ActionEvent;

import javax.swing.Timer;

import acm.program.GraphicsProgram;

public class Bullet extends GraphicsProgram{
	private Timer timer = new Timer(25, this);
	private int xAxis;
	private int yAxis;
	private int dx;
	private int dy;
	private boolean isFriendly;
	
	public Bullet(int x, int y, int dx, int dy, boolean isFriendly) {
		this.xAxis = x;
		this.yAxis = y;
		this.dx = dx;
		this.dy = dy;
		this.isFriendly = isFriendly;
		timer.start();
	}
	
	@Override
	public void run() {
		return;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		xAxis += dx;
		yAxis += dy;
	}
	
	public int getX() {
		return this.xAxis;
	}
	
	public int getY() {
		return this.yAxis;
	}
	
	public boolean isFriendly() {
		return this.isFriendly;
	}
	
	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
	}
}
