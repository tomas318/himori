package Entities;

import java.awt.Graphics2D;

import Graphical.Animation;
import Graphical.Texture;
import LevelUtility.LevelMap;

public class levelDoor extends Entity{
	
	private Animation doorAnimation;
	private boolean isTouching = false;
	private Player whatPlayer;
	private boolean isOpened = false;

	public levelDoor(double x, double y, LevelMap levelMap, Player player) {
		super(new Texture("doortexture"), x, y, levelMap);
		this.doorAnimation = new Animation(30, new Texture(new Texture("dooranimation"), 1, 1, 64), 
				new Texture(new Texture("dooranimation"), 2, 1, 64), 
				new Texture(new Texture("dooranimation"), 3, 1, 64),
				new Texture(new Texture("dooranimation"), 4, 1, 64));
		this.whatPlayer = player;
	}

	@Override
	public void tick() {
		if (whatPlayer.getBounds().intersects(this.getBounds()) && (whatPlayer.getTryingtoEnterDoor() == true)) {
		isTouching = true;
		doorAnimation.tick();
		}else if(isTouching && (whatPlayer.getTryingtoEnterDoor() == false)) {
			isTouching = true;
		}
	}
	
	public void render(Graphics2D g) {
		if (whatPlayer.getBounds().intersects(this.getBounds()) && (whatPlayer.getTryingtoEnterDoor() == true)) {
			doorAnimation.render(g, x, y);
			isOpened = true;
		}else {
			textureleft.render(g, x, y);
		}
	}
	
	public boolean getIsOpened() {
		return isOpened;
	}
	
	public void setIsOpened(boolean newIsOpened) {
		isOpened = newIsOpened;
	}
	
	public void setisTouching(boolean newIsTouching) {
		isTouching = newIsTouching;
	}

}
