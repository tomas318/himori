package GameStates;

import java.awt.Graphics2D;

import Interfaces.State;
import LevelUtility.LevelMap;
import Main.Main;
import Managers.GameStateManager;
import Music.MusicPlayer;
import TownUtility.TownMap;

public class GameState implements State{
	
	private LevelMap level1;
	private LevelMap level2;
	private LevelMap level3;
	private TownMap town1;
	
	@Override
	public void initialize() {
		if (Main.currentLevel == 1) {
			level1 = new LevelMap("newlevel1", "Level1", new MusicPlayer("Johnny_B_Goode_BTTF"), 1);
		}else if (Main.currentLevel == 2) {
			level2 = new LevelMap("newlevel1", "Level2", new MusicPlayer("Johnny_B_Goode_BTTF"), 2);
		}else if (Main.currentLevel == 3) {
			level3 = new LevelMap("newlevel1", "Level1", new MusicPlayer("Johnny_B_Goode_BTTF"), 3);
		}else if (Main.currentLevel == 4) {
			town1 = new TownMap("town1", "Town1");
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
		if (Main.currentLevel == 1) {
			level1.tick();
		}else if (Main.currentLevel == 2) {
			level2.tick();
		}else if (Main.currentLevel == 3) {
			level3.tick();
		}else if (Main.currentLevel == 4) {
			town1.tick();
		}
	}

	@Override
	public void render(Graphics2D g) {
		if (Main.currentLevel == 1) {
			level1.render(g, Main.WIDTH, Main.HEIGHT);
		}else if (Main.currentLevel == 2) {
			level2.render(g, Main.WIDTH, Main.HEIGHT);
		}else if (Main.currentLevel == 3) {
			level3.render(g, Main.WIDTH, Main.HEIGHT);
		}else if (Main.currentLevel == 4) {
			town1.render(g, Main.WIDTH, Main.HEIGHT);
		}else {
			Main.gsm.addState(new WinState());
			Main.gsm.setState("Win");
		}
	}
	
	public String getStateName() {
		if (Main.currentLevel == 1) {
			return "Level1";
		}else if (Main.currentLevel == 2) {
			return "Level2";
		}else if (Main.currentLevel == 3) {
			return "Level3";
		}else if (Main.currentLevel == 4) {
			return "Town1";
		}else {
			return null;
		}
	}
}
