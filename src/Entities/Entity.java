package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Graphical.Texture;
import LevelUtility.LevelMap;
import TownUtility.TownMap;

public abstract class Entity {

	protected double x, y;
	protected Texture textureleft;
	protected Texture textureright;
	protected LevelMap levelMap;
	protected TownMap townMap;
	protected boolean isPlayer;
	protected boolean isNPC;
	protected boolean facingRight;
	protected boolean facingLeft;

	public Entity(Texture texture, double x, double y, LevelMap levelMap) {
		this.textureleft = texture;
		this.x = x;
		this.y = y;
		this.levelMap = levelMap;
		this.levelMap.addEntity(this);
	}

	public Entity(Texture textureleft, Texture textureright, double x, double y, LevelMap levelMap) {
		this.textureleft = textureleft;
		this.textureright = textureright;
		this.x = x;
		this.y = y;
		this.levelMap = levelMap;
		this.levelMap.addEntity(this);
	}
	
	public Entity(Texture textureleft, Texture textureright, double x, double y, TownMap townMap) {
		this.textureleft = textureleft;
		this.textureright = textureright;
		this.x = x;
		this.y = y;
		this.townMap = townMap;
		this.townMap.addEntity(this);
	}
	
	public Entity(Texture texture) {
		this.textureleft = texture;
	}

	public void render(Graphics2D g, int offsetX, int offsetY) {
		if (isPlayer) {
			if (facingLeft) {
				textureleft.render(g, x + offsetX, y + offsetY);
			} else {
				textureright.render(g, x + offsetX, y + offsetY);
			}
		} else {
			textureleft.render(g, x + offsetX, y + offsetY);
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public Texture getTextures() {
		return textureleft;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, textureleft.getWidth(), textureleft.getHeight());
	}

	public Rectangle getTop() {
		return new Rectangle((int) x + 6, (int) y, textureleft.getWidth() - 10, 4);
	}

	public Rectangle getBottom() {
		return new Rectangle((int) x + 6, (int) y + textureleft.getHeight() - 4, textureleft.getWidth() - 6, 4);
	}

	public Rectangle getRight() {
		return new Rectangle((int) x + textureleft.getWidth() - 4, (int) y + 6, 4, textureleft.getHeight() - 6);
	}

	public Rectangle getLeft() {
		return new Rectangle((int) x, (int) y + 6, 4, textureleft.getHeight() - 6);
	}

	public abstract void tick();

}
