package BugJumpApplication;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class MainGame extends GraphicsProgram {
	private Player player;
	private GRect playerRect;
	
	private Timer timer = new Timer(30, this);
	
	@Override
	protected void init() {
		setSize(1080, 1920);
	}
	
	@Override
	public void run() {
		timer.start();
		addKeyListeners();
		setupTerrain();
		setupPlayer();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		
		if(keycode == 68) {
			player.move(10, 0);
		}
		else if (keycode == 65) {
			player.move(-10, 0);
		}
		System.out.println(player.getX() + " " + player.GetY());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		checkCollision();
		playerRect.setLocation(player.getX(), player.GetY());
	}
	
	private void checkCollision() {
		GObject obj = getElementAt(player.getX() + 25, player.GetY() + 51);
		if (obj != null) {
			player.isInAir = false;
		}
		else {
			player.isInAir = true;
		}
	}
	
	
	private void setupTerrain() {
		add(new GRect(0, 500, PROG_WIDTH, PROG_HEIGHT));
		add(new GRect(900, 700, PROG_WIDTH, PROG_HEIGHT));
	}
	
	private void setupPlayer() {
		player = new Player(200, 300);
		playerRect = new GRect(player.getX(), player.GetY(), 50, 50);
		add(playerRect);
		
	}
	
	public static void main(String[] args) {
		new MainGame().start();
	}
}
