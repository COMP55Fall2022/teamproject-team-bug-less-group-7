package BugJumpApplication;

import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;


public class MainMenu extends GraphicsPane {
//	private static final int PROGRAMWIDTH = 1920;
//	private static final int PROGRAMHEIGHT = 1080;
	private MainApplication program;

	
//	@Override
//	protected void init() {
//		setSize(PROGRAMWIDTH, PROGRAMHEIGHT);
//		requestFocus();
//	}
	
//	@Override
//	public void run() {
//		addMouseListeners();
//		main = new MainGame();
//	}
	
	public MainMenu(MainApplication e) {
		super();
		program = e;
	}
	GLabel title = new GLabel("Bug Jump", 400, 200);
	GRect play = new GRect (200, 270, 600, 100);
	GLabel playButton = new GLabel("Play", 470, 330);
	GRect tutorial = new GRect(200, 430, 600, 100);
	GLabel tutorialButton = new GLabel("Tutorial", 430, 490);
	GRect quit = new GRect(200, 590, 600, 100);
	GLabel quitButton = new GLabel("Quit", 470, 650);

@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		program.switchToLevelSelector();
	}

	@Override
	public void showContents() {
		//GRect rect = new GRect(500, 300, 100, 100);
		//rect.setFillColor(Color.GREEN);
		//rect.setFilled(true);
		//add(rect);
		
		program.add(title);
		title.setFont("Arial-Bold-60");
		
		program.add(play);		
		program.add(playButton);
		playButton.setFont("Arial-Bold-40");
		
		program.add(tutorial);
		program.add(tutorialButton);
		tutorialButton.setFont("Arial-Bold-40");
		
		program.add(quit);
		program.add(quitButton);
		quitButton.setFont("Arial-Bold-40");
		
		
		//program.add(new GRect(500, 300, 100, 100));
		//program.add(new GLabel("Bug Jump",500, 200));
		
		
		
		
	
		
	}
	
<<<<<<< HEAD
	
	



	private void add(GRect rect) {
		// TODO Auto-generated method stub
		
	}

=======
>>>>>>> branch 'main' of https://github.com/COMP55Fall2022/teamproject-team-bug-less-group-7.git
	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.removeAll();
	}
	
	
	@Override
	public void performAction(ActionEvent e) {
		return;
	}
	
	
}
