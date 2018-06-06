package GameStates;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Entities.Enemy;
import Entities.Entity;
import Interfaces.State;
import LevelUtility.LevelMap;
import LevelUtility.Tile;
import Main.Main;
import Managers.GameStateManager;
import Music.MusicPlayer;

public class GameState implements State{
	
	private LevelMap levelMap;

	@Override
	public void initialize() {
		
		levelMap = new LevelMap("newlevel1", "Level1", new MusicPlayer("Johnny_B_Goode_BTTF"));
		if (Main.hasRestarted) {
			levelMap = new LevelMap("newlevel1", "Level1", new MusicPlayer("Johnny_B_Goode_BTTF"));
		}
	}

	@Override
	public void enterState() {
		
	}

	@Override
	public void exitState() {
	}

	@Override
	public void tick(GameStateManager gameStateManager) {
		levelMap.tick();
	}

	@Override
	public void render(Graphics2D g) {
		levelMap.render(g, Main.WIDTH, Main.HEIGHT);
	}
	
	public String getStateName() {
		return "Level1";
	}
	
}
