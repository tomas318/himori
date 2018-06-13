package Entities;

import javax.swing.JOptionPane;

import Graphical.Texture;
import LevelUtility.LevelMap;

public class Ale extends Item{
	
	private Player player;

	public Ale(double x, double y, LevelMap levelMap, Player player) {
		super(new Texture("ale"), x, y, levelMap, "Ale", 0);
		this.player = player;
	}
	
	public void tick() {
		if (pickedUp && player.getInventory().getIsOpen() && player.getHealth() <= 2) {
			player.setHealth(player.getHealth() + 1);
		}else if (pickedUp && player.getInventory().getIsOpen() && player.getHealth() == 3) {
			JOptionPane.showMessageDialog(null, "Arrgh, you already had full health, there was no need to drink precious Ale!");
		}
	}
	
}
