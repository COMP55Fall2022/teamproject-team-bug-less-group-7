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
	
	private GImage backArrow;
	private GButton level1;
	private GButton level2;
	private GButton level3;
	private GButton level4;
	private GButton level5;
	private GButton level6;
	private GButton level7;
	private GButton level8;
	
	public LevelSelector(MainApplication e) {
		super();
		program = e;
	}
	
	@Override
	public void showContents() {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		System.out.println(dimension);
		
		program.getGCanvas().setBackground(Color.decode("#5f6c5a"));
		backArrow = new GImage("/Images/backArrow.png", 10, 10);
		backArrow.setSize(100, 75);
		program.add(backArrow);
		
		level1 = new GButton("Level 1", dimension.getWidth()/2-BUTTONWIDTH/2-BUTTONWIDTH, 400, BUTTONWIDTH, BUTTONHEIGHT, Color.decode("#879383"));
		level1.setColor(Color.white);
		level2 = new GButton("Level 2", dimension.getWidth()/2-BUTTONWIDTH/2+BUTTONWIDTH, 400, BUTTONWIDTH, BUTTONHEIGHT, Color.decode("#879383"));
		level2.setColor(Color.white);
		level3 = new GButton("Level 3", dimension.getWidth()/2-BUTTONWIDTH/2-BUTTONWIDTH, 800, BUTTONWIDTH, BUTTONHEIGHT, Color.decode("#879383"));
		level3.setColor(Color.white);
		level4 = new GButton("Level 4", dimension.getWidth()/2-BUTTONWIDTH/2+BUTTONWIDTH, 800, BUTTONWIDTH, BUTTONHEIGHT, Color.decode("#879383"));
		level4.setColor(Color.white);
		program.add(level1);
		program.add(level2);
		program.add(level3);
		program.add(level4);
		
		GLabel jGLabel = new GLabel("Level Selector", 0, 0);
		jGLabel.setFont("Arial-Bold-50");
		jGLabel.setLocation(dimension.getWidth()/2-jGLabel.getSize().getWidth()/2, 100);
		jGLabel.setColor(Color.white);
		program.add(jGLabel);
				
		
	}

	@Override
	public void hideContents() {
		program.getGCanvas().setBackground(Color.white);
		program.removeAll();
		backArrow = null;
		level1 = null;
		level2 = null;
		level3 = null;
		level4 = null;
		level5 = null;
		level6 = null;
		level7 = null;
		level8 = null;
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
		
		if (obj == backArrow) {
			program.switchToMenu();
		}
		else if (obj == level1) {
			program.switchToGame();
		}
	}

}