package BugJumpApplication;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class MainGame extends GraphicsProgram {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	private Player player;
	private GRect playerRect;
	
	//private Timer timer = new Timer(30, this);
	
	@Override
	protected void init() {
		setSize(1080, 1920);
	}
	
	@Override
	public void run() {
		addKeyListeners();
		//timer.start();
		setupPlayer();
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		
		if(keycode == 68) {
			player.move(10, 0);
			playerRect.setLocation(player.getX(), player.GetY());
		}
		else if (keycode == 65) {
			player.move(-10, 0);
			playerRect.setLocation(player.getX(), player.GetY());
		}
		System.out.println(player.getX() + " " + player.GetY());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		player.move(1, 0);
//		playerRect.setLocation(player.getX(), player.GetY());
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
