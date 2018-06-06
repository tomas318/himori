package LevelUtility;

import java.awt.Graphics2D;

import Graphical.Texture;

public class ParallaxLayer {

	private Texture texture;
	private int layerHeight, layerWidth;
	private int x, y;
	private int VelX;
	private int layerGap;
	private boolean movingLeft, movingRight;

	public ParallaxLayer(Texture texture, int VelX, int gap) {
		this.texture = texture;
		this.layerWidth = texture.getWidth();
		this.layerHeight = texture.getHeight();
		this.x = 0;
		this.y = 0;
		this.layerGap = gap;
		this.VelX = VelX;
	}

	public ParallaxLayer(Texture texture, int VelX) {
		this(texture, VelX, 0);
	}

	public ParallaxLayer(Texture texture, int x, int y, int layerHeight, int layerWidth, int VelX, int gap) {
		this(texture, VelX, gap);
		this.x = x;
		this.y = y;
	}

	public ParallaxLayer(Texture texture, int layerHeight, int layerWidth, int VelX, int gap) {
		this(texture, VelX, gap);
		this.x = 0;
		this.y = 0;
	}

	public void setRight() {
		movingRight = true;
		movingLeft = false;
	}

	public void setLeft() {
		movingLeft = true;
		movingRight = false;
	}

	public void stopParallax() {
		movingRight = false;
		movingLeft = false;
	}

	public void moveParallax() {
		if (movingRight) {
			x = (x + VelX) % (layerWidth + layerGap);
		} else {
			x = (x - VelX) % layerWidth;
		}
	}

	public void render(Graphics2D g) {
		if (x == 0) {
			texture.render(g, 0, Main.Main.WIDTH, 0, Main.Main.WIDTH, y);
		} else if (x > 0 && x < Main.Main.WIDTH) {
			texture.render(g, x, Main.Main.WIDTH, 0, Main.Main.WIDTH - x, y);
			texture.render(g, 0, x, layerWidth - x, layerWidth, y);
		} else if (x >= Main.Main.WIDTH) {
			texture.render(g, 0, Main.Main.WIDTH, layerWidth - x, layerWidth - x + Main.Main.WIDTH, y);
		} else if (x < 0 && x > -Main.Main.WIDTH) {
			texture.render(g, 0, Main.Main.WIDTH, -x, Main.Main.WIDTH - x, y);
		} else if (x < Main.Main.WIDTH - layerWidth) {
			texture.render(g, 0, layerWidth + x, -x, layerWidth, y);
			texture.render(g, layerGap + layerWidth + x, layerGap + Main.Main.WIDTH, 0, Main.Main.WIDTH - layerWidth - x, y);
		}
	}
}
