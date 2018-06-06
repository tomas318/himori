package Inventory;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entities.Item;
import Inputs.KeyInput;

public class Inventory {
	
	private boolean isOpen;
	private ArrayList<Item> inventoryItems;
	private double x, y;
	
	public Inventory(int x, int y) {
		this.x = x;
		this.y = y;
		inventoryItems = new ArrayList<Item>();
	}
	
	public void render(Graphics2D g, double x, double y) {
		if(!isOpen) {
			return;
		}else {
			
		}
	}
	
	public void tick() {
		if (KeyInput.wasKeyPressed(KeyEvent.VK_I))
			isOpen = !isOpen;
		if (!isOpen) {
			return;
		}
	}
	
	public void addItem(Item item) {
		for (Item i : inventoryItems) {
			if (i.getItemID() == item.getItemID()) {
				i.setItemCount(i.getItemCount() + item.getItemCount());
				return;
			}
		}
		inventoryItems.add(item);
	}
}
