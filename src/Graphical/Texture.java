package Graphical;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Texture {

	private BufferedImage image;
	private final static Map<String, BufferedImage> TextureMap = new HashMap<String, BufferedImage>(); //Creates the map for a specific texture and saves it as to not render same texture multiple times
	private String fileName;
	private int width, height;
	
	public Texture(String fileName) {
		this.fileName = fileName;
		BufferedImage oldTexture = TextureMap.get(fileName);
		if(oldTexture != null) {
			this.image = oldTexture;
		}else {
			try {
			this.image = (ImageIO.read(new File("./resources/textures/" + fileName + ".png"))); //reads file
			TextureMap.put(fileName, image); //puts the texture in the map to not re-load same texture
			System.out.println("LOADING TEXTURE: " + fileName + ".png");
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public Texture(Texture spritesheet, int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		String key = spritesheet.fileName + "_" + x + "_" + y;
		BufferedImage old = TextureMap.get(key);
		if (old != null) {
			this.image = old;
		}else {
			this.image = spritesheet.image.getSubimage(x * width - width, y * height - height, width, height);
		}
	}
	
	public Texture(Texture spritesheet, int x , int y ,int size) {
		this(spritesheet, size, size, x, y);
	}
	
	
	public BufferedImage getImage() {
		return this.image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void render(Graphics2D g, double x, double y) {
		g.drawImage(image, (int) x, (int) y, null);
	}
	
	public void render(Graphics2D g, int destinationX, int destinationX2, int sourceX, int sourceX2, int y) {
		g.drawImage(image, destinationX, y, destinationX2, y + height, sourceX, 0, sourceX2, height, null);
	}
}
	

