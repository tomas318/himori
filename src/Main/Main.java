package Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import GameStates.GameOverState;
import GameStates.GameState;
import GameStates.InstructionsState;
import GameStates.MenuState;
import GameStates.WinState;
import Graphical.CustomFontLoader;
import Inputs.KeyInput;
import Inputs.MouseInput;
import LevelUtility.LevelMap;
import Managers.AudioManager;
import Managers.GameStateManager;
import Managers.LoadingScreenManager;
import Music.MusicPlayer;
import Utility.ThreadHandler;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1596975759887741171L;
	public static final String TITLE = "Himori's Adventure";
	public static final int WIDTH = 640, HEIGHT = WIDTH / 4 * 3;
	public static final boolean DEBUGMODE = false;
	public static Font EIGHTBIT20;
	private Thread thread;
	public static ThreadHandler Handler = new ThreadHandler(5);
	private boolean running = false;
	public static GameStateManager gsm;
	public static Main ACCESS;
	public static String currentState = "Menu";
	public static boolean hasRestarted = false;
	
	public Main() {
		new LoadingScreenManager();
		new Window(WIDTH, HEIGHT, TITLE, this);
		// To render first initialize here
		addKeyListener(new KeyInput());
		MouseInput mi = new MouseInput();
		addMouseListener(mi);
		addMouseMotionListener(mi);
		EIGHTBIT20 = CustomFontLoader.loadFont("./resources/fonts/EIGHTBIT.ttf", 20);
		gsm = new GameStateManager();
		gsm.addState(new MenuState());
		gsm.addState(new InstructionsState());
		gsm.addState(new GameState());
		gsm.addState(new GameOverState());
		gsm.addState(new WinState());
		ACCESS = this;
	}

	public synchronized void stop() {
		if (!running) {
			return;
		} else {
			System.exit(0);
		}
	}

	@Override
	public void run() {
		running = true;
		requestFocus();
		// GAME LOOP
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				KeyInput.update();
				MouseInput.update();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		if (!Utility.OperatingSystem.isUnix())
			g2d.translate(-6, -28);
		// RENDER INITIALIZED//
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		gsm.render(g2d);
		// END OF RENDER//
		g.dispose();
		bs.show();
	}

	private void tick() {
		gsm.tick();
	}

	private static void runHandler() {
		MusicPlayer level1music = new MusicPlayer("Johnny_B_Goode_BTTF");
		Main game = new Main();
		//Handler.runTask(level1music);
		Handler.runTask(game);
		Handler.joinHandler();
	}

	public static void main(String[] args) {
		runHandler();	
	}

}
