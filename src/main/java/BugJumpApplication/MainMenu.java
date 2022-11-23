package BugJumpApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;


public class MainMenu extends GraphicsPane {

	private MainApplication program;
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	GLabel title;
	GButton playGButton;
	GButton tutorialGButton;
	GButton quitGButton;

	
	public MainMenu(MainApplication e) {
		super();
		program = e;
	}

@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == null) {return;}
		
		if (obj == playGButton) {
			program.switchToLevelSelector();			
		}
		else if (obj == quitGButton) {
			System.exit(0);
		}
	}

	@Override
	public void showContents() {
		title = new GLabel("Bug Jump", 0, 200);
		playGButton = new GButton("Play", 0, 430, 600, 100);
		tutorialGButton = new GButton("Tutorial", 0, 590, 600, 100);
		quitGButton = new GButton("Quit", 0, 750, 600, 100);
		
		title.setFont("Arial-Bold-100");
		title.setLocation(dimension.getWidth()/2-title.getWidth()/2, title.getY());
		program.add(title);
		
		playGButton.setLocation(dimension.getWidth()/2-playGButton.getWidth()/2, playGButton.getY());
		program.add(playGButton);
		
		tutorialGButton.setLocation(dimension.getWidth()/2-tutorialGButton.getWidth()/2, tutorialGButton.getY());
		program.add(tutorialGButton);
		
		quitGButton.setLocation(dimension.getWidth()/2-quitGButton.getWidth()/2, quitGButton.getY());
		program.add(quitGButton);
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.removeAll();
		title = null;
		playGButton = null;
		tutorialGButton = null;
		quitGButton = null;
		System.gc();
	}
	
	
	@Override
	public void performAction(ActionEvent e) {
		return;
	}
	
	
}
