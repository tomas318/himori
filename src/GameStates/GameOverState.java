package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Graphical.Option;
import Graphical.Texture;
import Inputs.KeyInput;
import Inputs.MouseInput;
import Interfaces.State;
import Managers.GameStateManager;

public class GameOverState implements State{
	
	private Texture screen;
	private int currentOption;
	private Option[] gameOverOptions;

	@Override
	public void initialize() {
		screen = new Texture("gameover");
		gameOverOptions = new Option[2];
		gameOverOptions[0] = new Option("Retry", 200 + 1 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		gameOverOptions[1] = new Option("Quit", 200 + 2 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		
	}

	@Override
	public void enterState() {
		
	}

	@Override
	public void exitState() {
		
	}

	@Override
	public void tick(GameStateManager gameStateManager) {
		if(KeyInput.wasKeyPressed(KeyEvent.VK_UP)) {
			currentOption--;
			if(currentOption < 0) {
				currentOption = gameOverOptions.length - 1;
			}
		}
		else if(KeyInput.wasKeyPressed(KeyEvent.VK_DOWN)) {
			currentOption++;
			if(currentOption >= gameOverOptions.length) {
				currentOption = 0;
			}
		}
		boolean clickedOption = false;
		for (int i = 0; i < gameOverOptions.length; i++) {
			if (gameOverOptions[i].intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1, 1))) {
				currentOption = i;
				clickedOption = MouseInput.wasMouseButtonPressed(MouseEvent.BUTTON1);
			}
		}
		if (clickedOption || KeyInput.wasKeyPressed(KeyEvent.VK_ENTER)) {
			selectOption(gameStateManager);
		}
	}
	
	private void selectOption(GameStateManager gameStateManager) {
		if (currentOption == 0) {
			Main.Main.hasRestarted = true;
			Main.Main.playerLives = 3;
			Main.Main.currentLevel = 1;
			Main.Main.unlockedLevel = 1;
			
			
			
			
			
			
			
			
			
			Main.Main.gsm.addState(new GameState());
			Main.Main.gsm.addState(new MenuState());
			gameStateManager.setState("Menu");
		}else if (currentOption == 1) {
			Main.Main.ACCESS.stop();
			System.exit(1);
		}
	}

	@Override
	public void render(Graphics2D g) {
		screen.render(g, 0, 0);
		for (int i = 0; i < gameOverOptions.length; i++) {
			if (i == currentOption) {
				gameOverOptions[i].setSelected(true);
				gameOverOptions[i].render(g);
			} else {
				gameOverOptions[i].setSelected(false);
				gameOverOptions[i].render(g);
			}
		}
		
	}

	@Override
	public String getStateName() {
		return "GameOver";
	}

}
