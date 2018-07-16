package Graphical;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Main;

public class Option extends Rectangle {

	private static final long serialVersionUID = 1L;
	private Font font, selectedFont;
	private Color color, selectedColor;
	private String text;
	private int textY, textX;
	private boolean selected;
	private boolean wantCentered;

	public Option(String text, int textY, Font font, Font selectedFont, Color color, Color selectedColor) {
		super();
		this.text = text;
		this.textY = textY;
		this.font = font;
		this.selectedFont = selectedFont;
		this.color = color;
		this.selectedColor = selectedColor;
		this.wantCentered = true;
	}

	public Option(String text, int textY, int textX, Font font, Font selectedFont, Color color, Color selectedColor) {
		super();
		this.text = text;
		this.textY = textY;
		this.textX = textX;
		this.font = font;
		this.selectedFont = selectedFont;
		this.color = color;
		this.selectedColor = selectedColor;
		this.wantCentered = false;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void render(Graphics g) {
		if (wantCentered) {
			if (selected) {
				Fonts.drawString(g, selectedFont, selectedColor, textY, text);
			} else {
				Fonts.drawString(g, font, color, textY, text);
			}
		}else if (!wantCentered) {
			if (selected) {
				Fonts.drawString(g, selectedFont, selectedColor, text, textX, textY);
			} else {
				Fonts.drawString(g, font, color, text, textX, textY);
			}
		}
	}

}
