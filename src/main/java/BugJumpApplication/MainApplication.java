package BugJumpApplication;

import java.awt.Dimension;
import java.awt.Toolkit;

public class MainApplication extends GraphicsApplication {
	private static final int PROGRAMHEIGHT = 1080;
	private static final int PROGRAMWIDTH = 1920;
	private Dimension dimension;

	private AudioPlayer audio;

	
	public void init() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)dimension.getWidth(), (int)dimension.getHeight());
	}

	public void run() {	
		setupInteractions();
		switchToMenu();
	}

	public void switchToMenu() {
		switchToScreen(new MainMenu(this));
	}

	public void switchToGame(int level) {
		switchToScreen(new MainGame(this, level));
	}

	public void switchToLevelSelector() {
		switchToScreen(new LevelSelector(this));
	}
	
	public static void main(String[] args) {
		new MainApplication().start();
	}

}
