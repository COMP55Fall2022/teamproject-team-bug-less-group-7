package BugJumpApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import acm.graphics.GImage;

public class FileReader{

	private Player player;
	private GImage playerGImage;
	private HashMap<GImage, Collectable> collectablesMap = new HashMap<>();;
	private HashMap<GImage, Enemy> enemiesMap = new HashMap<>();
	private HashMap<GImage, Terrain> terrainMap = new HashMap<>();;
	
	
	public FileReader(int level) throws FileNotFoundException {
		readLevel(level);
	}

	private void readLevel(int level) throws FileNotFoundException {
		File file = new File("media/Level" + level + ".txt");
		Scanner scanner = new Scanner(file);
		String currentLine;
		
		
		//player Reader
		scanner.nextLine();
		currentLine = scanner.nextLine();
		for(int i = 0; i < Integer.parseInt(currentLine); i++) {
			String[] content = scanner.nextLine().trim().split("-");
			player = new Player(Integer.parseInt(content[0]), Integer.parseInt(content[1]));
			playerGImage = new GImage("/Images/rightPlayer.png", player.getX(), player.getY());
		}
		
		
		scanner.nextLine();
		scanner.nextLine();
		currentLine = scanner.nextLine();

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
		
		// Terrain Reader
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
		
		scanner.nextLine();
		scanner.nextLine();
		currentLine = scanner.nextLine();
		
		// Collectable Reader
		for(int i = 0; i < Integer.parseInt(currentLine); i++) {
			Collectable collectable;
			GImage image;
			String[] content = scanner.nextLine().trim().split("-");			
			collectable = new Collectable(Integer.parseInt(content[0]), Integer.parseInt(content[1]), CollectableType.NONE.getType(Integer.parseInt(content[2])));
			image = new GImage(collectable.getCType().toString(), collectable.getX(), collectable.getY());
			collectablesMap.put(image, collectable);
		}	
		
		scanner.close();

	}
		
	public static void main(String[] args) throws FileNotFoundException {
		FileReader file = new FileReader(1);
	}
	public GImage getplayerImage() {
		// TODO Auto-generated method stub
		return playerGImage;
	}
	
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}
	
	public HashMap<GImage, Enemy> getEnemyMap() {
		return enemiesMap;
	}
	
	public HashMap<GImage, Collectable> getCollectableMaps() {
		// TODO Auto-generated method stub
		return collectablesMap;
	}


	public HashMap<GImage, Terrain> getTerrainMap() {
		// TODO Auto-generated method stub
		return terrainMap;
	}


	

}

