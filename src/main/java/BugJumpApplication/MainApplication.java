package BugJumpApplication;

import edu.pacific.comp55.starter.GraphicsApplication;

public class MainApplication extends GraphicsApplication {
	private static final int PROGRAMHEIGHT = 1080;
	private static final int PROGRAMWIDTH = 1920;


	private MainMenu menu;
	private MainGame game;
	
	public void init() {
		setSize(PROGRAMHEIGHT, PROGRAMWIDTH);
	}

	public void run() {
		setupInteractions();
		menu = new MainMenu(this);
		switchToMenu();
	}

	public void switchToMenu() {
		switchToScreen(menu);
	}

	public void switchToGame() {
		switchToScreen(new MainGame(this));
	}

	//TODO: Implement switchToPause
	
	public static void main(String[] args) {
		new MainApplication().start();
	}
}
