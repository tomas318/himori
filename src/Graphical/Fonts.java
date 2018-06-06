package Graphical;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import Main.Main;

public class Fonts {
	
	public static void drawString(Graphics g, Font f, Color c, String str, int x, int y) {
		g.setColor(c);
		g.setFont(f);
		g.drawString(str, x, y);
	}
	
	public static void drawString(Graphics g, Font f, Color c, String str) {
		FontMetrics fm = g.getFontMetrics(f);
		int x = ((Main.WIDTH - fm.stringWidth(str)) / 2);
		int y = ((Main.HEIGHT - fm.getHeight()) / 2) + fm.getAscent(); // Center on the y-axis
		drawString(g, f, c, str, x, y);
	}
	
	public static void drawString(Graphics g, Font f, Color c, String str, int x) {
		FontMetrics fm = g.getFontMetrics(f);
		int y = ((Main.HEIGHT - fm.getHeight()) / 2) + fm.getAscent(); // Center on the y-axis
		drawString(g, f, c, str, x, y);
	}
	
	public static void drawString(Graphics g, Font f, Color c, int y, String str) {
		FontMetrics fm = g.getFontMetrics(f);
		int x = ((Main.WIDTH - fm.stringWidth(str)) / 2);
		drawString(g, f, c, str, x, y);
	}
}
