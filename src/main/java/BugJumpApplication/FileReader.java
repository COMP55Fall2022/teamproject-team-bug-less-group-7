package BugJumpApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import acm.graphics.GImage;

public class FileReader{

	private Player player;
	private GImage playerGImage;
	private HashMap<GImage, Collectable> collectablesMap;
	private HashMap<GImage, Enemy> enemiesMap;
	private HashMap<GImage, Terrain> terrainMap;
	
	
	public FileReader(int level) throws FileNotFoundException {
		readLevel();
	}

	private void readLevel() throws FileNotFoundException {
		File file = new File("/Users/jacobbejarano/Desktop/Programming Stuff/Java/FileReadingPractice/Media/BugJumpLevel1.txt");
		Scanner scanner = new Scanner(file);
		String currentLine;
		
		
		//player Reader
		currentLine = scanner.nextLine();
		for(int i = 0; i < Integer.parseInt(currentLine); i++) {
			scanner.nextLine();
			String[] content = scanner.nextLine().split("-");
			player = new Player(Integer.parseInt(content[0]), Integer.parseInt(content[1]));
			playerGImage = new GImage("/Images/rightPlayer.png", player.getX(), player.getY());
			
			
		}
		
		
		scanner.nextLine();
		currentLine = scanner.nextLine();

		
//		System.exit(0);
		// Enemy Reader
		for(int i = 0; i < Integer.parseInt(currentLine); i++) {
			System.out.println(currentLine);
		}		
		scanner.close();
		
		}
		
	
	public Map<GImage, Enemy> getEnemyMap() {
		return enemiesMap;
	}
	

	public static void main(String[] args) throws FileNotFoundException {
		FileReader file = new FileReader(0);
	}
	

}

