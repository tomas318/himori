package LevelUtility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entities.Ale;
import Entities.Coin;
import Entities.Enemy;
import Entities.Entity;
import Entities.NPC;
import Entities.Player;
import Entities.levelDoor;
import GameStates.GameState;
import GameStates.LevelSelector;
import GameStates.PauseScreen;
import Graphical.Fonts;
import Graphical.Texture;
import Inputs.KeyInput;
import Managers.ParallaxManager;
import Music.MusicPlayer;

public class LevelMap{

	private static final int TILESIZE = 32;
	private static final int TILESIZEBITS = 5; // 2^5(bits) = TILESIZE (32)

	private String worldName;
	private int worldWidth, worldHeight;
	public static int currentLevel;
	private Tile[] Tiles;
	public static ArrayList<Entity> Entities;
	public static Player player;
	private Enemy enemy;
	private ParallaxManager parallaxManager;
	public MusicPlayer musicplayer;
	private Coin coin;
	private levelDoor door;
	private Ale ale;
	private NPC npc;
	private boolean collisionFlag;
	

	public LevelMap(String fileName, String worldName, MusicPlayer levelmusic, int currentLevel) {
		// if wanting to design custom levels just put these parameters inside the constructor
		this.worldName = worldName;
		this.currentLevel = currentLevel;
		musicplayer = levelmusic;
		Entities = new ArrayList<Entity>();
		ParallaxLayer backLayer = new ParallaxLayer(new Texture("parallax_back"), (int) ((16 * .25) * -0.15));
		ParallaxLayer middleLayer = new ParallaxLayer(new Texture("parallax_middle"), (int) ((16 * .25) * -0.25));
		ParallaxLayer frontLayer = new ParallaxLayer(new Texture("parallax_fronttest"), (int) ((16 * .25) * -0.4));
		this.parallaxManager = new ParallaxManager(backLayer, middleLayer, frontLayer);
		collisionFlag = false;
		loadMap(fileName);
	}
	
	public boolean getTileCollision(int size, double currentX, double currentY, double newX, double newY, boolean hasVerticalCollision) { //non-destructable terrain
		// gets range of from and where player is going and converts to tiles to check for collision
		double fromX = Math.min(currentX, newX);
		double fromY = Math.min(currentY, newY);
		double toX = Math.max(currentX, newX);
		double toY = Math.max(currentY, newY);
		int fromTileX = convertPixelstoTiles((int) fromX); //changes player positions (pixels) to tiles of x-axis
		int fromTileY = convertPixelstoTiles((int) fromY); // changes player positions (pixels) to tiles of y-axis
		int toTileX = convertPixelstoTiles((int) toX + size - 1); // checks part of tile after it
		int toTileY = convertPixelstoTiles((int) toY + size - 1); // checks part of tile after it
		
		for(int y = fromTileY; y <= toTileY; y++) {
			for(int x = fromTileX; x <= toTileX; x++) {
				if (x < 0 || x >= worldWidth || (getTile(x,y) != null && getTile(x, y).isSolid())) {
					if(hasVerticalCollision) {
						if (player.isFalling()) {
							player.setY(convertTilestoPixels(y) - size);
							player.setCanJump(true);
						}else {
							player.setY(convertTilestoPixels(y+1));
						}
						player.setVelY(0);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean getTileCollisionEnemy(int size, double currentX, double currentY, double newX, double newY, boolean hasVerticalCollision) { //non-destructable terrain
		// gets range of from and where enemy is going and converts to tiles to check for collision
		double fromX = Math.min(currentX, newX);
		double fromY = Math.min(currentY, newY);
		double toX = Math.max(currentX, newX);
		double toY = Math.max(currentY, newY);
		int fromTileX = convertPixelstoTiles((int) fromX); //changes enemy positions (pixels) to tiles of x-axis
		int fromTileY = convertPixelstoTiles((int) fromY); // changes enemy positions (pixels) to tiles of y-axis
		int toTileX = convertPixelstoTiles((int) toX + size - 1); // checks part of tile after it
		int toTileY = convertPixelstoTiles((int) toY + size - 1); // checks part of tile after it
		
		for(int y = fromTileY; y <= toTileY; y++) {
			for(int x = fromTileX; x <= toTileX; x++) {
				if (x < 0 || x >= worldWidth || (getTile(x,y) != null && getTile(x, y).isSolid())) {
					if(hasVerticalCollision) {
						if (enemy.isFalling()) {
							enemy.setY(convertTilestoPixels(y) - size);
							enemy.setCanJump(true);
						}else {
							enemy.setY(convertTilestoPixels(y+1));
						}
						enemy.setVelY(0);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	//public Tile getTileCollisionDestructable(int size, double currentX, double currentY, double newX, double newY, boolean vert) {
	//
	//}

	public static int convertPixelstoTiles(int pixels) {
		return pixels >> TILESIZEBITS; // pixels / TILESIZE to not have negative pixels (shifts) + faster performance
	}

	public static int convertTilestoPixels(int tiles) {
		return tiles << TILESIZEBITS; // tiles * TILESIZE
	}

	public void setTile(int x, int y, Tile tile) {
		Tiles[x + y * worldWidth] = tile;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= worldWidth || y < 0 || y >= worldHeight) {
			return null;
		} else {
			return Tiles[x + y * worldWidth];
		}
	}

	private void loadMap(String name) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("./resources/levels/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		worldName = name;
		worldWidth = image.getWidth();
		worldHeight = image.getHeight();
		Tiles = new Tile[worldWidth * worldHeight];
		int[] worldPixels = image.getRGB(0, 0, worldWidth, worldHeight, null, 0, worldWidth);
		for (int y = 0; y < worldHeight; y++) {
			for (int x = 0; x < worldWidth; x++) {
				int id = worldPixels[x + y * worldWidth];
				if (id == 0xFF0000FF) {
					player = new Player(convertTilestoPixels(x), convertTilestoPixels(y), this);
				}else if(id == 0xFFFF0000) {
					enemy = new Enemy(convertTilestoPixels(x), convertTilestoPixels(y), this, player);
				}else if (id == 0xFFF6FF00) {
					coin = new Coin(convertTilestoPixels(x), convertTilestoPixels(y), this);
				}else if (id == 0xFF946213) {
					door = new levelDoor(convertTilestoPixels(x), convertTilestoPixels(y), this, player);
				}else if (id == 0xFF0DDE12) {
					ale = new Ale(convertTilestoPixels(x), convertTilestoPixels(y), this, player);
				}else if (id == 0xFFFF00FC) {
					npc = new NPC(convertTilestoPixels(x), convertTilestoPixels(y), this, "Welcome to level 1 fucking cuck", player);
				}else if (Tile.getFromID(id) != null) {
					setTile(x, y, Tile.getFromID(id));
				}
			}
		}
		player.setHealth(3);
	}

	public void addEntity(Entity e) {
		if (!(e instanceof Player)) {
			Entities.add(e);
		}
	}

	public void removeEntity(Entity e) {
		if (!(e instanceof Player)) {
			Entities.remove(e);
		}
	}

	public ArrayList<Entity> getEntities() {
		return Entities;
	}

	public Player getPlayer() {
		return player;
	}

	public Tile[] getTiles() {
		return Tiles;
	}

	public void tick() {
		if (player.isMovingLeft()) {
			parallaxManager.setLeft();
		}else if (player.isMovingRight()) {
			parallaxManager.setRight();
		}if (player.isMoving()) {
			parallaxManager.moveParallax();
		}if (KeyInput.wasKeyPressed(KeyEvent.VK_ESCAPE)){
			player.setPaused(true);
		}
		for (int i = 0; i < Entities.size(); i++) {
			Entities.get(i).tick();
		}
		player.tick();
		door.tick();
		}
	
	
	public MusicPlayer getLevelMusic() {
		return musicplayer;
	}

	public String getCurrentWorld() {
		return worldName;
	}
	
	public int getLevel() {
		return currentLevel;
	}
	
	public void exitState() {
		
	}

	public void render(Graphics2D g, int screenWidth, int screenHeight) {
		int mapWidth = convertTilestoPixels(worldWidth);
		int mapHeight = convertTilestoPixels(worldHeight);
		int offsetX = (int) (screenWidth / 2 - player.getX() - TILESIZE);
		int offsetY = (int) (screenHeight / 2 - player.getY() - TILESIZE);
		offsetX = Math.min(offsetX, 0);
		offsetX = Math.max(offsetX, screenWidth - mapWidth);
		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, screenHeight - mapHeight);
		int firstTileX = convertPixelstoTiles(-offsetX);
		int lastTileX = firstTileX + convertPixelstoTiles(screenWidth) + 1;
		int firstTileY = convertPixelstoTiles(-offsetY);
		int lastTileY = firstTileY + convertPixelstoTiles(screenHeight) + 1;
		parallaxManager.render(g);
		for (int y = firstTileY; y <= lastTileY; y++) {
			for (int x = firstTileX; x <= lastTileX; x++) {
				Tile t = getTile(x, y);
				if (t != null) {
					t.render(g, convertTilestoPixels(x) + offsetX, convertTilestoPixels(y) + offsetY);
				}
			}
		}
		for (int j = Entities.size() - 1; j >= 0; j--) {
			Entity e = Entities.get(j);
			e.render(g, offsetX, offsetY);
			if (e instanceof Coin) {
				if (player.getBounds().intersects(e.getBounds())) {
					Entities.remove(j);
					player.incrementScore(2);
				  }
				}else if (e instanceof Enemy) {
					if (player.getBounds().intersects(enemy.getBounds()) && !player.getisAttacking() && !collisionFlag) {
						player.decrementHealth();
						collisionFlag = true;
					}else if (!player.getBounds().intersects(enemy.getBounds()) && collisionFlag) {
						collisionFlag = false;
					}else if (player.getBounds().intersects(enemy.getBounds()) && player.getisAttacking()) {
						Entities.remove(j);
						player.incrementScore(6);
					}
				}else if (e instanceof Ale) {
					if (player.getBounds().intersects(e.getBounds())) {
						player.getInventory().addItem(ale);
						ale.setPickedUp(true);
						Entities.remove(j);
					}
				}
			}
		player.render(g, offsetX, offsetY);
		player.postRender(g, offsetX - 800, offsetY);
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, 640, 90);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 640, 90);
		for (int p = 0; p <= player.getSCORE(); p = p + 2) {
			if (player.getSCORE() == p) {
				if (player.getSCORE() <= 100) {
					if (player.getSCORE() == 100) {
						player.setLives(player.getLives() + 1);
						player.incrementScore(0);
					}
				Texture score = new Texture("Score_" + p);
				score.render(g, 560, 30);
				}
			}
		}
	door.render(g);
	if (door.getIsOpened()) {
		player.incrementScore(0);
		door.render(g);
		Entities.clear();
		door.setIsOpened(false);
		door.setisTouching(false);
		Main.Main.unlockedLevel = Main.Main.currentLevel + 1;
		Main.Main.gsm.addState(new GameState());
		Main.Main.gsm.setState("LevelSelectorWorld1");
	}if (player.getHealth() == 3) {
			Texture fullhearts = new Texture("fullhearts");
			fullhearts.render(g, 30, 20);
		}if (player.getHealth() == 2) {
			Texture twohearts = new Texture("2hearts");
			twohearts.render(g, 30, 20);
		}if (player.getHealth() == 1) {
			Texture oneheart = new Texture("1heart");
			oneheart.render(g, 30, 20);
		}if (player.getHealth() == 0 && player.getLives() == 0) {
			Entities.clear();
			player.setHealth(3);
			player.incrementScore(0);
			player.setisAttacking(false);
			Main.Main.gsm.setState("GameOver");
		}if (player.getHealth() == 0 && player.getLives() > 0) {
			Entities.clear();
			Main.Main.playerLives -= 1;
			player.setHealth(3);
			player.incrementScore(0);
			player.setisAttacking(false);
			Main.Main.gsm.addState(new GameState());
			Main.Main.gsm.setState("Level" + Main.Main.currentLevel);
		}if (KeyInput.wasKeyPressed(KeyEvent.VK_ESCAPE)) {
			player.setPaused(true);
			Main.Main.gsm.addState(new PauseScreen());
			Main.Main.gsm.setState("Pause");
		}
		
	}

}
