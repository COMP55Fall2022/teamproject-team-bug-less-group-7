package BugJumpApplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;


public class LevelSelector extends GraphicsPane {
	private MainApplication program;
	private final static double BUTTONWIDTH = 400;
	private final static double BUTTONHEIGHT = 100;
	
	private Dimension dimension;
	
	private GImage backArrow;
	private GImage pageButton;
	private GButton button1;
	private GButton button2;
	private GButton button3;
	private GButton button4;
	private Boolean isOnSecondPage; 

	
	public LevelSelector(MainApplication e) {
		super();
		program = e;
	}
	
	@Override
	public void showContents() {
		
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		isOnSecondPage = false;
					
		GLabel jGLabel = new GLabel("Level Selector", 0, 0);
		jGLabel.setFont("Arial-Bold-50");
		jGLabel.setLocation(dimension.getWidth()/2-jGLabel.getSize().getWidth()/2, 100);
		jGLabel.setColor(Color.white);
		program.add(jGLabel);
		
		
		button1 = new GButton("Level 1", dimension.getWidth()/2-BUTTONWIDTH/2-BUTTONWIDTH, 400, BUTTONWIDTH, BUTTONHEIGHT, Color.decode("#879383"));
		button1.setColor(Color.white);
		button2 = new GButton("Level 2", dimension.getWidth()/2-BUTTONWIDTH/2+BUTTONWIDTH, 400, BUTTONWIDTH, BUTTONHEIGHT, Color.decode("#879383"));
		button2.setColor(Color.white);
		button3 = new GButton("Level 3", dimension.getWidth()/2-BUTTONWIDTH/2-BUTTONWIDTH, 800, BUTTONWIDTH, BUTTONHEIGHT, Color.decode("#879383"));
		button3.setColor(Color.white);
		button4 = new GButton("Level 4", dimension.getWidth()/2-BUTTONWIDTH/2+BUTTONWIDTH, 800, BUTTONWIDTH, BUTTONHEIGHT, Color.decode("#879383"));
		button4.setColor(Color.white);
		program.add(button1);
		program.add(button2);
		program.add(button3);
		program.add(button4);
		
		pageButton = new GImage("/Images/Page1Button.png", 0, button4.getY());
		pageButton.setSize(150, 100);
		pageButton.setLocation(dimension.getWidth()/2-pageButton.getWidth()/2, pageButton.getY());
		program.add(pageButton);
		
		program.getGCanvas().setBackground(Color.decode("#5f6c5a"));
		backArrow = new GImage("/Images/backArrow.png", 10, 10);
		backArrow.setSize(100, 75);
		program.add(backArrow);
	}

	@Override
	public void hideContents() {
		program.getGCanvas().setBackground(Color.white);
		program.removeAll();
		backArrow = null;
		button1 = null;
		button2 = null;
		button3 = null;
		button4 = null;
		pageButton = null;
		isOnSecondPage = null;
		System.gc();
	}

	@Override
	public void performAction(ActionEvent e) {
		return;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == null) {return;}
		
		if (obj == pageButton) {
			switchPage();
			return;
		}
		
		if (obj == backArrow) {
			program.switchToMenu();
			return;
		}
		
		if (!isOnSecondPage) {			
			if (obj == button1) {
				program.switchToGame(1);
			}
			else if (obj == button2) {
				program.switchToGame(2);
			}
			else if (obj == button3) {
				program.switchToGame(3);
			}
			else if (obj == button4) {
				program.switchToGame(4);
			}
		}
		else {
			if (obj == button1) {
				program.switchToGame(5);
			}
			else if (obj == button2) {
				program.switchToGame(6);
			}
			else if (obj == button3) {
				program.switchToGame(7);
			}
			else if (obj == button4) {
				program.switchToGame(8);
			}
		}
	}
	
	private void switchPage() {
		if (isOnSecondPage) {		
			pageButton.setImage("/Images/Page1Button.png");
			pageButton.setSize(150, 100);
			pageButton.setLocation(dimension.getWidth()/2-pageButton.getWidth()/2, pageButton.getY());
			button1.setLabel("Level 1");
			button2.setLabel("Level 2");
			button3.setLabel("Level 3");
			button4.setLabel("Level 4");
			isOnSecondPage = false;
		}
		else {
			pageButton.setImage("/Images/Page2Button.png");
			pageButton.setSize(150, 100);
			pageButton.setLocation(dimension.getWidth()/2-pageButton.getWidth()/2, pageButton.getY());
			button1.setLabel("Level 5");
			button2.setLabel("Level 6");
			button3.setLabel("Level 7");
			button4.setLabel("Level 8");
			isOnSecondPage = true;
		}
	}


}