package BugJumpApplication;
import acm.graphics.*;
 import acm.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class PauseScreen extends GraphicsPane{
	
	boolean pause = false;
	MainApplication program;
	
	public PauseScreen(MainApplication e) {
		program = e;
	}
	
	GLabel title = new GLabel("Game Paused", 0, 200);
	GRect resume = new GRect(200,300,1120,400);
	GRect saveNQuit = new GRect(200, 600, 1120, 400);
	
	@Override
	public void showContents() {
		title.setFont("Arial-Bold-60");
		program.add (title);
		
		program.add(resume);
		program.add(saveNQuit);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(program.getElementAt(e.getX(), e.getY()) == resume) {
			program.switchToGame();
		}
		else if (program.getElementAt(e.getX(), e.getY()) == saveNQuit) {
			program.switchToMenu();
		}
	}

	@Override
	public void hideContents() {
		program.removeAll();
	}

	@Override
	public void performAction(ActionEvent e) {
		return;
	}
}