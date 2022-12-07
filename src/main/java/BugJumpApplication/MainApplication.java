package BugJumpApplication;

import java.awt.Dimension;
import java.awt.Toolkit;

public class MainApplication extends GraphicsApplication {
	private static final int PROGRAMHEIGHT = 1080;
	private static final int PROGRAMWIDTH = 1920;
	private Dimension dimension;

	private AudioPlayer audio;
	private boolean hasMainGameStarted;

	
	public void init() {
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)dimension.getWidth(), (int)dimension.getHeight());
	}

	public void run() {	
		setupInteractions();
		switchToMenu();
		audio = AudioPlayer.getInstance();
		audio.playSound("music", "MENU_LEVEL_SELECT_BGM_MASTER.mp3", true);
		hasMainGameStarted = false;
		
	}
	
	private void startMusic() {
		if (hasMainGameStarted) {
			audio.stopSound("music", "LEVEL_BGM_MASTER.mp3");
			audio.playSound("music", "MENU_LEVEL_SELECT_BGM_MASTER.mp3", true);
			hasMainGameStarted = false;
		}
	}

	public void switchToMenu() {
		startMusic();
		switchToScreen(new MainMenu(this));
	}

	public void switchToLevelSelector() {
		startMusic();
		switchToScreen(new LevelSelector(this));
	}
	
	public void switchToGame(int level) {
		if (!hasMainGameStarted) {			
			audio.stopSound("music", "MENU_LEVEL_SELECT_BGM_MASTER.mp3");
			audio.playSound("music", "LEVEL_BGM_MASTER.mp3", true);
			hasMainGameStarted = true;
		}
		switchToScreen(new MainGame(this, level));
	}

	
	public static void main(String[] args) {
		new MainApplication().start();
	}

}
