package BugJumpApplication;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.time.Year;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class MainGame extends GraphicsProgram {
	private static final int RIGHT_VELOCITY = 10;
	private static final int LEFT_VELOCITY = -10;
	
	private Player player;
	private GRect playerRect;
	private Boolean isPrevOrientationRight = null;
	
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
		playerRect.setLocation(player.getX(), player.getY());
		//checkCollision();
		
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
		player.checkOrientation(xVel);
		
		if (keyList.contains(87)) {
			player.turnOnJumping();
		}
		
		if (checkCollision()) {
			if (isPrevOrientationRight == null) {
				xVel = 0;
				isPrevOrientationRight = player.isRightOrientation;
			}
			else if (isPrevOrientationRight == player.isRightOrientation) {
				xVel = 0;				
			} 
			else if (isPrevOrientationRight != player.isRightOrientation) {
				player.isOnWall = false;
			}
		}
		player.move(xVel, 0);
		
	}
	
	private boolean checkCollision() {

		// functionality for ground detection
		if(getElementAt(player.getX() + 5, player.getY() + 54) != null || getElementAt(player.getX() + playerRect.getWidth()-5, player.getY() + 54) != null) {
			GObject obj = getElementAt(player.getX() + 4, player.getY() + 52);
			player.isInAir = false;
			if (obj != null) {				
				player.setY((int)obj.getY()-51);
			}
		}
		else {
			player.isInAir = true;
		}
		
	// functionality for wall detection 
		if(getElementAt(player.getX()-6, player.getY()) != null || 
		   getElementAt(player.getX()+50+6, player.getY()) != null ||
		   getElementAt(player.getX()+50+6, player.getY() + 50) != null ||
		   getElementAt(player.getX()-6, player.getY()+50) != null)
		{
			player.isOnWall = true;
			return true;
		}
		else {
			player.isOnWall = false;
			isPrevOrientationRight = null;
			return false;
		}
	}
	
	
	private void setupTerrain() {
		add(new GRect(0, 500, PROG_WIDTH, PROG_HEIGHT));
		add(new GRect(900, 700, PROG_WIDTH, PROG_HEIGHT));
	}
	
	private void setupPlayer() {
		player = new Player(200, 300);
		playerRect = new GRect(player.getX(), player.getY(), 50, 50);
		add(playerRect);
		
	}
	
	public static void main(String[] args) {
		new MainGame().start();
	}
}


//
//// functionality for wall detection 
//if(getElementAt(player.getX()-6, player.getY()) != null || 
//   getElementAt(player.getX()+50+6, player.getY()) != null) {
//	player.isOnWall = true;
//	return true;
//}
//else {
//	player.isOnWall = false;
//	isPrevOrientationRight = null;
//	return false;
//}
//if (checkCollision()) {
//	if (isPrevOrientationRight == null) {
//		xVel = 0;
//		isPrevOrientationRight = player.isRightOrientation;
//	}
//	else if (isPrevOrientationRight == player.isRightOrientation) {
//		xVel = 0;				
//	} 
//	else if (isPrevOrientationRight != player.isRightOrientation) {
//		player.isOnWall = false;
//	}
//}

