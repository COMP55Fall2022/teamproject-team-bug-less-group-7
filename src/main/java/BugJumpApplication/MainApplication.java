package BugJumpApplication;


public class MainApplication extends GraphicsApplication {
	private static final int PROGRAMHEIGHT = 1080;
	private static final int PROGRAMWIDTH = 1920;


	private MainMenu menu;
	private LevelSelector lSelector;
	private MainGame game;
	private AudioPlayer audio;

	
	public void init() {
		setSize(PROGRAMHEIGHT, PROGRAMWIDTH);
	}

	public void run() {	
		audio = audio.getInstance();
		audio.playSoundWithOptions("Music", "MainMenuMusic.mp3", true);
		setupInteractions();
		menu = new MainMenu(this);
		lSelector = new LevelSelector(this);

		switchToMenu();
	}

	public void switchToMenu() {
		switchToScreen(menu);
	}

	public void switchToGame(int level) {
		switchToScreen(new MainGame(this, level));
	}

	public void switchToLevelSelector() {
		switchToScreen(lSelector);
	}
	
	public static void main(String[] args) {
		new MainApplication().start();
	}

}
