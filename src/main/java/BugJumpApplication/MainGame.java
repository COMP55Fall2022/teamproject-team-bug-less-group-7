package BugJumpApplication;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class MainGame extends GraphicsProgram {
	private static final int RIGHT_VELOCITY = 10;
	private static final int LEFT_VELOCITY = -10;
	
	private Player player;
	private GRect playerRect;
	
	private ArrayList<Integer> keyList;
	private int xVel;
		
	private Timer timer = new Timer(30, this);
	
	@Override
	protected void init() {
		setSize(1080, 1920);
	}
	
	@Override
	public void run() {
		keyList = new ArrayList<Integer>();
		timer.start();
		addKeyListeners();
		setupTerrain();
		setupPlayer();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		/*
		if(keycode == 68) {
			player.move(10, 0);
		}
		else if (keycode == 65) {
			player.move(-10, 0);
		}
		System.out.println(player.getX() + " " + player.GetY());
		*/
		if (!keyList.contains(keyCode)) {
			keyList.add(keyCode);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (keyList.contains(e.getKeyCode())) {
			keyList.remove(keyList.indexOf(e.getKeyCode()));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (keyList.contains(68) && !keyList.contains(65)) {
			if (xVel < RIGHT_VELOCITY) {
				xVel+=2;
			}
		} else if (keyList.contains(65) && !keyList.contains(68)) {
			if (xVel > LEFT_VELOCITY) {
				xVel-=2;
			}
		} else {
			if (xVel != 0) {
				if (xVel > 0) {
					xVel-=2;
				} else {
					xVel+=2;
				}
			}
		}
		player.move(xVel, 0);
		checkCollision();
		playerRect.setLocation(player.getX(), player.GetY());
		
	}
	
	private void checkCollision() {
		GObject obj = getElementAt(player.getX() + 25, player.GetY() + 51);
		if (obj != null) {
			player.isJumping = false;
		}
		else {
			player.isJumping = true;
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
