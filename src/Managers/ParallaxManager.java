package Managers;

import java.awt.Graphics2D;

import LevelUtility.ParallaxLayer;

public class ParallaxManager {

	private ParallaxLayer[] Layers;
	
	public ParallaxManager(ParallaxLayer... layers) {
		this.Layers = layers;
	}
	
	public void setRight() {
		for(int i = 0; i < Layers.length; i++){
			Layers[i].setRight();
		}
	}
	
	public void setLeft() {
		for(int i = 0; i < Layers.length; i++){
			Layers[i].setLeft();
		}
	}
	
	public void stopParallax() {
		for(int i = 0; i < Layers.length; i++){
			Layers[i].stopParallax();
		}
	}
	
	public void moveParallax() {
		for(int i = 0; i < Layers.length; i++){
			Layers[i].moveParallax();
		}
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < Layers.length; i++){
			Layers[i].render(g);
		}
	}
	
}
