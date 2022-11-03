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
	
	private ArrayList<Integer> keyList; //Arraylist of all keys pressed at once
	private int xVel; //left and right velocity of the player object
		
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
		//Gets the keycode of the last key pressed by the player
		int keyCode = e.getKeyCode();
		
		//Adds key pressed to a list of all keys pressed on the keyboard at once
		if (!keyList.contains(keyCode)) {
			keyList.add(keyCode);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		//As soon as one key is released from the keyboard, it is removed from the list of all keys
		//held down by the user
		if (keyList.contains(e.getKeyCode())) {
			keyList.remove(keyList.indexOf(e.getKeyCode()));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//If the d key is held and the a key is not
		if (keyList.contains(68) && !keyList.contains(65)) {
			if (xVel < RIGHT_VELOCITY) {
				xVel+=2;
			}
		} 
		//If the a key is held and the d key is not
		else if (keyList.contains(65) && !keyList.contains(68)) {
			if (xVel > LEFT_VELOCITY) {
				xVel-=2;
			}
		//Case for if no key is held or no specific key combination is found
		} else {
			//Slows momentum of player to a stop
			if (xVel != 0) {
				if (xVel > 0) {
					xVel-=2;
				} else {
					xVel+=2;
				}
			}
		}
		
		if (keyList.contains(87)) {
			player.turnOnJumping();
		}
		
		player.move(xVel, 0);
		checkCollision();
		playerRect.setLocation(player.getX(), player.GetY());
		System.out.println(player.GetY());
		
	}
	
	private void checkCollision() {
		GObject obj = getElementAt(player.getX() + 25, player.GetY() + 51);
		if (obj != null) {
			player.isInAir = false;
		}
		else {
			player.isInAir = true;
			player.turnOffJumping();
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
