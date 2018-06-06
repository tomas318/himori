package Entities;

import Graphical.Animation;
import Graphical.Texture;
import LevelUtility.LevelMap;

public class Enemy extends Mobile{
	
	private Player whatPlayer;

	public Enemy(double x, double y, LevelMap levelMap, Player whatPlayer) {
		super(new Texture(new Texture("player_sheet"), 1, 1, 64), 
				new Texture(new Texture("player_sheetrightface"), 1, 1, 64), 
				x, y, levelMap, 
				new Animation(20, 
				new Texture(new Texture("player_sheetleft"), 1, 1, 64), 
				new Texture(new Texture("player_sheetleft"), 2, 1, 64)),
				new Animation(20,
				new Texture(new Texture("player_sheetright"), 1, 1, 64),
				new Texture(new Texture("player_sheetright"), 2, 1, 64))
				);
		this.whatPlayer = whatPlayer;
		HEALTH = 1;
	}
	//add another constructor later
	@Override
	public void tick() {
		if ((Math.abs(whatPlayer.getX() - this.getX()) < 500) && (Math.abs(whatPlayer.getY() - this.getY()) < 100)){
			if (whatPlayer.getVelX() > 0 && this.getX() > whatPlayer.getX()) {
				VelX = -3;
			}if (whatPlayer.getVelX() > 0 && this.getX() < whatPlayer.getX()) {
				VelX = 3;
			}if(whatPlayer.getVelX() < 0 && this.getX() > whatPlayer.getX()) {
				VelX = -3;
			}if(whatPlayer.getVelX() < 0 && this.getX() < whatPlayer.getX()) {
				VelX = 3;
			}if (whatPlayer.getVelX() == 0) {
				VelX = 0;
			}
		}
		super.tick();
	}
	
	public void damage() {
		HEALTH--;
	}

}
