package Inventory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entities.Item;
import Graphical.Fonts;
import Graphical.Texture;
import Inputs.KeyInput;

public class Inventory {
	
	private boolean isOpen;
	private ArrayList<Item> inventoryItems;
	private double x, y;
	private Texture inventoryScreen;
	private int invX = 64, invY = 48, 
			invWidth = 512, invHeight = 384, 
			invListCenterX = invX + 100, invListCenterY = invY + invHeight / 2 + 55,
			invListSpacing = 30;
	private int invImageX = 452, invImageY = 82, 
			invImageWidth = 64, invImageHeight = 64;
	private int invCountX = 484, invCountY = 172;
	private int currentOption = 0;
	
	
	public Inventory(int x, int y) {
		this.x = x;
		this.y = y;
		this.inventoryItems = new ArrayList<Item>();
		this.inventoryScreen = new Texture("inventoryscreen");
	}
	
	public void render(Graphics2D g, double offsetX, double offsetY) {
		if(!isOpen) {
			return;
		}else {
			//System.out.println("offsetX: "+ offsetX + " X: " + x + " offsetY: " + offsetY + " Y: " + y);
			inventoryScreen.render(g, x + offsetX / 2, offsetY - y / 2);
			int inventoryLength = inventoryItems.size();
			if (inventoryLength == 0) {
				return;
			}else {
				for (int i = -5; i <= 5; i++) {
					if (currentOption + i < 0 || currentOption + i >= inventoryLength) {
						continue;
					}
					if (i == 0) {
						Fonts.drawString(g, Main.Main.EIGHTBIT20, Color.YELLOW, ">" + inventoryItems.get(currentOption+i).getName() + "<", invListCenterX, invListCenterY + i * invListSpacing);
					}else {
						Fonts.drawString(g, Main.Main.EIGHTBIT20, Color.WHITE, inventoryItems.get(currentOption+i).getName(), invListCenterX, invListCenterY + i * invListSpacing);	
					}
				}
			}
		}
	}
		
	
	
	public void tick() {
		if (KeyInput.wasKeyPressed(KeyEvent.VK_E))
			isOpen = !isOpen;
		if (!isOpen) {
			return;
		}if (KeyInput.wasKeyPressed(KeyEvent.VK_UP) || KeyInput.wasKeyPressed(KeyEvent.VK_W)) {
			currentOption--;
		}if(KeyInput.wasKeyPressed(KeyEvent.VK_DOWN) || KeyInput.wasKeyPressed(KeyEvent.VK_S)) {
			currentOption++;
		}if (currentOption < 0) {
			currentOption = inventoryItems.size() - 1;
		}if (currentOption >= inventoryItems.size()) {
			currentOption = 0;
		}if (KeyInput.wasKeyPressed(KeyEvent.VK_F)) {
			useItem();
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
	
	public boolean getIsOpen() {
		return isOpen;
	}
	
	public void useItem() {
		if (inventoryItems.size() > 0) {
		inventoryItems.get(currentOption).tick();
		inventoryItems.get(currentOption).setPickedUp(false);
		inventoryItems.remove(currentOption);
		}
	}
}