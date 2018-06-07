package Entities;

import java.awt.Graphics2D;

import Graphical.Texture;
import LevelUtility.LevelMap;

public class Item extends Entity{
	
	private boolean pickedUp;
	private String itemName;
	private int itemID;
	private int itemCount = 0;

	public Item(Texture texture, double x, double y, LevelMap levelMap, String name, int itemID) {
		super(texture, x, y, levelMap);
		this.pickedUp = false;
		this.itemName = name;
		this.itemID = itemID;
		this.itemCount = 1;
	}

	@Override
	public void tick() {
	}
	
	public void render(Graphics2D g) {
		if (!pickedUp)
		textureleft.render(g, x, y);
	}
	
	public void setPickedUp(boolean newPickedUp) {
		this.pickedUp = newPickedUp;
	}
	
	public boolean isPickedUp() {
		return pickedUp;
	}
	
	public int getItemID() {
		return itemID;
	}
	
	public int getItemCount() {
		return itemCount;
	}
	
	public void setItemCount(int newCount) {
		this.itemCount = newCount;
	}
	
	public String getName() {
		return itemName;
	}

}
