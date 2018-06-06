package Entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Graphical.Animation;
import Graphical.Texture;
import LevelUtility.LevelMap;

public class Coin extends Entity{
	
	private Animation coinAnimation;
	private Player player;
	private boolean pickedUp;
	private int coinCount = 0;

	public Coin(double x, double y, LevelMap levelMap) {
		super(new Texture("coin"), x, y, levelMap);
		pickedUp = false;
	}

	@Override
	public void tick() {
		pickedUp = true;
	}
	
	public boolean getPickedUp() {
		return pickedUp;
	}
	
	public void render(Graphics2D g) {
		textureleft.render(g, x, y);
	}
	
}
	
	


