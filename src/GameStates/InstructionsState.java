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

public class InstructionsState implements State{

	private Texture screen;
	private Option[] instructionOptions;
	private int currentOption;
	
	@Override
	public void initialize() {
		screen = new Texture("instructions");
		instructionOptions = new Option[1];
		instructionOptions[0] = new Option("Go Back", 200 + 3 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
	}

	public void enterState() {
		
	}

	public void exitState() {
		
	}

	@Override
	public void tick(GameStateManager gameStateManager) {
		if(KeyInput.wasKeyPressed(KeyEvent.VK_UP)) {
			currentOption--;
			if(currentOption < 0) {
				currentOption = instructionOptions.length - 1;
			}
		}
		else if(KeyInput.wasKeyPressed(KeyEvent.VK_DOWN)) {
			currentOption++;
			if(currentOption >= instructionOptions.length) {
				currentOption = 0;
			}
		}
		boolean clickedOption = false;
		for (int i = 0; i < instructionOptions.length; i++) {
			if (instructionOptions[i].intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1, 1))) {
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
		for (int i = 0; i < instructionOptions.length; i++) {
			if (i == currentOption) {
				instructionOptions[i].setSelected(true);
				instructionOptions[i].render(g);
			} else {
				instructionOptions[i].setSelected(false);
				instructionOptions[i].render(g);
			}
		}
		
	}

	@Override
	public String getStateName() {
		return "Instructions";
	}
}
