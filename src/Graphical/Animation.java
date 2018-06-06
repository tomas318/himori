package Graphical;

import java.awt.Graphics2D;

public class Animation {
	
	private int framesRendered;
	private int frameIndex;
	private int frameSpeed;
	private int totalFrames;
	private Texture currentFrame;
	private Texture[] Frames;

	public Animation(int frameSpeed, Texture... frames) {
		this.frameSpeed = frameSpeed;
		this.Frames = frames;
		this.totalFrames = frames.length;
	}
	
	private void nextFrame() {
		currentFrame = Frames[frameIndex++];
		if (frameIndex >= totalFrames) {
			frameIndex = 0;
		}
	}
	
	public void tick() {
		framesRendered++;
		if (framesRendered > frameSpeed) {
			framesRendered = 0;
			nextFrame();
		}
	}
	
	public void render(Graphics2D g, double x, double y) {
		if(currentFrame != null) {
			currentFrame.render(g, x, y);
		}
		
	}
}
