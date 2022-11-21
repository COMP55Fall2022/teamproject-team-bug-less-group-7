package BugJumpApplication;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.program.Program;
import edu.pacific.comp55.starter.GraphicsPane;

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

@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		program.switchToGame();
	}

	@Override
	public void showContents() {
		program.add(new GRect(200, 200, 200, 200));
	}
	
	
	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.removeAll();
	}
	
	
	@Override
	public void performAction(ActionEvent e) {
		return;
	}
	
//	public static void main(String[] args) {
//		new MainMenu().start();
//	}
	
	
}
