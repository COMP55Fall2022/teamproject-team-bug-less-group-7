package BugJumpApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import acm.graphics.GImage;

public class FileReader{

	private Player player;
	private GImage playerGImage;
	private HashMap<GImage, Collectable> collectablesMap = new HashMap<>();;
	private HashMap<GImage, Enemy> enemiesMap = new HashMap<>();
	private HashMap<GImage, Terrain> terrainMap = new HashMap<>();;
	
	
	public FileReader(int level) throws FileNotFoundException {
		readLevel();
	}

	private void readLevel() throws FileNotFoundException {
		File file = new File("media/Bug Jump Level 1.txt");
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
		scanner.nextLine();
		currentLine = scanner.nextLine();

		
//		System.exit(0);
		// Enemy Reader
		for(int i = 0; i < Integer.parseInt(currentLine); i++) {
			Enemy enemy;
			String[] content = scanner.nextLine().trim().split("-");			
			enemy = new Enemy(Integer.parseInt(content[0]), Integer.parseInt(content[1]), EnemyType.NONE.getType(Integer.parseInt(content[2])));
			enemiesMap.put(new GImage(enemy.getEnemyType().toString(), enemy.getX(), enemy.getY()), enemy);
			
		}		
		scanner.nextLine();
		scanner.nextLine();
		currentLine = scanner.nextLine();
		
		for(int i = 0; i < Integer.parseInt(currentLine); i++) {
			Terrain terrain;
			GImage image;
			String[] content = scanner.nextLine().trim().split("-");			
			terrain = new Terrain(Integer.parseInt(content[0]), Integer.parseInt(content[1]),
								  Integer.parseInt(content[2]), Integer.parseInt(content[3]), 
								  TerrainType.NONE.getType(Integer.parseInt(content[4])));
			image = new GImage(terrain.getTerrainType().toString(), terrain.getX(), terrain.getY());
			image.setSize(terrain.getWidth(), terrain.getHeight());
			terrainMap.put(image, terrain);
			
			
		}		
		
		
		for (Entry<GImage, Terrain> entry : terrainMap.entrySet()) {
			GImage key = entry.getKey();
			Terrain val = entry.getValue();
			System.out.println(key);
			System.out.println(val);
		}
		scanner.close();

		}
		
	
	public HashMap<GImage, Enemy> getEnemyMap() {
		return enemiesMap;
	}
	

	public static void main(String[] args) throws FileNotFoundException {
		FileReader file = new FileReader(0);
		

	}
	

}

