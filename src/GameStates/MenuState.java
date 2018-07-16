package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import Graphical.Fonts;
import Graphical.Option;
import Graphical.Texture;
import Inputs.KeyInput;
import Inputs.MouseInput;
import Interfaces.State;
import LevelUtility.ParallaxLayer;
import Main.Main;
import Managers.GameStateManager;
import Managers.ParallaxManager;
import Managers.ResourceManager;
import Music.MusicPlayer;

public class MenuState implements State {

	private ParallaxManager parallaxManager;
	private ResourceManager fileLoader;
	private Option[] MenuOptions;
	private int currentOption;
	private final String stateName = "Menu";
	
	@Override
	public void initialize() {
		ParallaxLayer backLayer = new ParallaxLayer(new Texture("parallax_back"), (int) ((16 * .25) * -0.15));
		ParallaxLayer middleLayer = new ParallaxLayer(new Texture("parallax_middle"), (int) ((16 * .25) * -0.25));
		ParallaxLayer frontLayer = new ParallaxLayer(new Texture("parallax_fronttest"), (int) ((16 * .25) * -0.4));
		this.parallaxManager = new ParallaxManager(backLayer, middleLayer, frontLayer);
		MenuOptions = new Option[5];
		MenuOptions[0] = new Option("Start", 160 + 0 * 80, new Font("Century Gothic", Font.PLAIN, 30),
			new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		MenuOptions[1] = new Option("Options", 160 + 1 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		MenuOptions[2] = new Option("Instructions", 160 + 2 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		MenuOptions[3] = new Option("Load Save", 160 + 3 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		MenuOptions[4] = new Option("Exit", 160 + 4 * 80, new Font("Century Gothic", Font.PLAIN, 30),
				new Font("Century Gothic", Font.BOLD, 40), Color.BLUE, Color.RED);
		
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		parallaxManager.render(g);
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
		parallaxManager.moveParallax();
	}
	
	private void selectOption(GameStateManager gameStateManager) {
		if (currentOption == 0) {
			Main.currentLevel = 1;
			Main.unlockedLevel = 1;
			Main.currentState = "Level1";
			gameStateManager.setState("Level1");
		}else if (currentOption == 1) {
			gameStateManager.setState("Options");
		}else if (currentOption == 2) {
			gameStateManager.setState("Instructions");
		}else if (currentOption == 3) {
			String fileName = JOptionPane.showInputDialog("What is the name of your save file?");
			ResourceManager rm = new ResourceManager();
			rm.loadFile(fileName);
	    }else if (currentOption == 4) {
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
