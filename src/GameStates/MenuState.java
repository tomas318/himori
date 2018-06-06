package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Graphical.Fonts;
import Graphical.Option;
import Inputs.KeyInput;
import Inputs.MouseInput;
import Interfaces.State;
import Main.Main;
import Managers.GameStateManager;
import Music.MusicPlayer;

public class MenuState implements State {

	private Option[] MenuOptions;
	private int currentOption;
	private final String stateName = "Menu";
	
	@Override
	public void initialize() {
		MenuOptions = new Option[4];
		MenuOptions[0] = new Option("Start", 200 + 0 * 80, new Font("Century Gothic", Font.PLAIN, 30),
			new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		MenuOptions[1] = new Option("Options", 200 + 1 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		MenuOptions[2] = new Option("Instructions", 200 + 2 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		MenuOptions[3] = new Option("Exit", 200 + 3 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Fonts.drawString(g, new Font("Century Gothic", Font.BOLD, 50), Color.BLUE, 100, Main.TITLE);
		for (int i = 0; i < MenuOptions.length; i++) {
			if (i == currentOption) {
				MenuOptions[i].setSelected(true);
				MenuOptions[i].render(g);
			} else {
				MenuOptions[i].setSelected(false);
				MenuOptions[i].render(g);
			}
		}
	}
	
	@Override
	public void tick(GameStateManager gameStateManager) {
		if(KeyInput.wasKeyPressed(KeyEvent.VK_UP)) {
			currentOption--;
			if(currentOption < 0) {
				currentOption = MenuOptions.length - 1;
			}
		}
		else if(KeyInput.wasKeyPressed(KeyEvent.VK_DOWN)) {
			currentOption++;
			if(currentOption >= MenuOptions.length) {
				currentOption = 0;
			}
		}
		boolean clickedOption = false;
		for (int i = 0; i < MenuOptions.length; i++) {
			if (MenuOptions[i].intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1, 1))) {
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
			Main.currentState = "Level1";
			gameStateManager.setState("Level1");
		}else if (currentOption == 1) {
			gameStateManager.setState("Options");
		}else if (currentOption == 2) {
			gameStateManager.setState("Instructions");
		}else if (currentOption == 3) {
			System.out.println("Exit");
			Main.ACCESS.stop();
			System.exit(1);
		}
	}


	@Override
	public void enterState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitState() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return stateName;
	}

}
