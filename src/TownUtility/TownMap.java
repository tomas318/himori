package TownUtility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entities.Entity;
import Entities.Player;
import LevelUtility.Tile;

public class TownMap {

	private static final int TILESIZE = 32; // might to change to 64
	private static final int TILESIZEBITS = 5; // might change to 6

	private Tile[] Tiles;
	private int townWidth;
	private int townHeight;
	private String townName;
	private static ArrayList<Entity> Entities;
	private Player player;

	public TownMap(String fileName, String name) {
		this.townName = name;
		Entities = new ArrayList<Entity>();
		this.generateTown(fileName);
	}

	public void generateTown(String name) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("./resources/levels/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.townName = name;
		this.townWidth = image.getWidth();
		this.townHeight = image.getHeight();
		Tiles = new Tile[townWidth * townHeight];
		int[] worldPixels = image.getRGB(0, 0, townWidth, townHeight, null, 0, townWidth);
		for (int y = 0; y < townHeight; y++) {
			for (int x = 0; x < townWidth; x++) {
				int id = worldPixels[x + y * townWidth];
				if (Tile.getFromID(id) != null) {
					setTile(x, y, Tile.getFromID(id));
				} else if (id == 0xFF0000FF) {
					player = new Player(convertTilestoPixels(x), convertTilestoPixels(y), this, true);
				}
			}
		}
	}

	public void setTile(int x, int y, Tile tile) {
		Tiles[x + y * townWidth] = tile;
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || x >= townWidth || y < 0 || y >= townHeight) {
			return null;
		} else {
			return Tiles[x + y * townWidth];
		}
	}
	
	public void addEntity(Entity e) {
		if (!(e instanceof Player)) {
			Entities.add(e);
		}
	}

	public static int convertPixelstoTiles(int pixels) {
		return pixels >> TILESIZEBITS; // pixels / TILESIZE to not have negative pixels (shifts) + faster performance
	}

	public static int convertTilestoPixels(int tiles) {
		return tiles << TILESIZEBITS; // tiles * TILESIZE
	}

	public void render(Graphics2D g, int screenWidth, int screenHeight) {
		int mapWidth = convertTilestoPixels(townWidth);
		int mapHeight = convertTilestoPixels(townHeight);
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
		}
		player.render(g, offsetX, offsetY);
		player.postRender(g, offsetX - 800, offsetY);
	}

	public void tick() {
		for (int i = 0; i < Entities.size(); i++) {
			Entities.get(i).tick();
		}
		player.tick();
	}

	public boolean getTileCollision(int size, double currentX, double currentY, double newX, double newY, boolean hasVerticalCollision) {
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
				if (x < 0 || x >= townWidth || y < 0 || y >= townHeight) {
					if(hasVerticalCollision && y >= townHeight) {
						player.setY(convertTilestoPixels(y-2));
					}else if (hasVerticalCollision && y < 0) {
						player.setY(convertTilestoPixels(y+1));
					}
						player.setVelY(0);
						return true;
					}
				}
			}
		return false;
	}

}
