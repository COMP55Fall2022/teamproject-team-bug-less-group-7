package BugJumpApplication;

import java.awt.event.MouseEvent;

import acm.program.GraphicsProgram;
import edu.pacific.comp55.starter.GraphicsPane;

public class MainMenu extends GraphicsProgram {
	private static final int PROGRAMWIDTH = 1920;
	private static final int PROGRAMHEIGHT = 1080;
	private MainGame main;

	
	@Override
	protected void init() {
		setSize(PROGRAMWIDTH, PROGRAMHEIGHT);
		requestFocus();
	}
	@Override
	public void run() {
		addMouseListeners();
		main = new MainGame();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("keyPressed");
		main.startGame();
	}
	
//	public static void main(String[] args) {
//		new MainMenu().start();
//	}
	
	
}
