package LevelUtility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import Graphical.Texture;

public class Tile {
	
	private static final Texture terrain = new Texture("test_maptiles3");
	private static final Map<Integer, Tile> tileMap = new HashMap<Integer, Tile>();
	protected Texture sprite;
	protected boolean solid;
	protected int ID;
	
	public static final Tile tile1 = new Tile(0xFFFFFFFF, new Texture(terrain, 1, 1, 32));
	public static final Tile tile2 = new Tile(0xFFFF0000, new Texture(terrain, 2, 1, 32));

	

	private Tile(int ID, Texture sprite) {
		this.ID = ID;
		this.sprite = sprite;
		solid = true;
		tileMap.put(ID, this);
	}
	
	public void render(Graphics2D g, int x, int y) {
		sprite.render(g, x, y);
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	//public abstract void updateTile();
	
	
	public static Tile getFromID(int ID) {
		return tileMap.get(ID);
	}
	
}
