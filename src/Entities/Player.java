package Entities;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Graphical.Animation;
import Graphical.Texture;
import Inputs.KeyInput;
import Inventory.Inventory;
import LevelUtility.LevelMap;
import TownUtility.TownMap;

public class Player extends Mobile {

	private Enemy[] enemy;
	private boolean isPaused;
	private Inventory playerInventory;
	private int currentLevel;

	public Player(double x, double y, LevelMap levelMap, Enemy... enemies) {
		super(new Texture(new Texture("player_sheet"), 1, 1, 64),
				new Texture(new Texture("player_sheetrightface"), 1, 1, 64), x, y, levelMap,
				new Animation(20, new Texture(new Texture("player_sheetleft"), 1, 1, 64),
						new Texture(new Texture("player_sheetleft"), 2, 1, 64)),
				new Animation(20, new Texture(new Texture("player_sheetright"), 1, 1, 64),
						new Texture(new Texture("player_sheetright"), 2, 1, 64)),
				new Animation(10, new Texture(new Texture("player_attackleft"), 1, 1, 64),
						new Texture(new Texture("player_attackleft"), 2, 1, 64),
						new Texture(new Texture("player_attackleft"), 3, 1, 64),
						new Texture(new Texture("player_attackleft"), 4, 1, 64)),
				new Animation(10, new Texture(new Texture("player_attackright"), 1, 1, 64),
						new Texture(new Texture("player_attackright"), 2, 1, 64),
						new Texture(new Texture("player_attackright"), 3, 1, 64),
						new Texture(new Texture("player_attackright"), 4, 1, 64)));
		playerInventory = new Inventory(320, 240);
		isPlayer = true;
		isPaused = false;
		inTown = false;
		enemy = new Enemy[enemies.length];
		for (int i = 0; i < enemy.length; i++) {
			enemy[i] = enemies[i];
		}
		SCORE = 0;
		HEALTH = 3;
		LIVES = Main.Main.playerLives;
		currentLevel = levelMap.getLevel();
	}

	public Player(double x, double y, TownMap townMap, boolean town) {
		super(new Texture(new Texture("player_sheet"), 1, 1, 64),
				new Texture(new Texture("player_sheetrightface"), 1, 1, 64), x, y, townMap,
				new Animation(20, new Texture(new Texture("player_sheetleft"), 1, 1, 64),
						new Texture(new Texture("player_sheetleft"), 2, 1, 64)),
				new Animation(20, new Texture(new Texture("player_sheetright"), 1, 1, 64),
						new Texture(new Texture("player_sheetright"), 2, 1, 64)),
				new Animation(10, new Texture(new Texture("player_attackleft"), 1, 1, 64),
						new Texture(new Texture("player_attackleft"), 2, 1, 64),
						new Texture(new Texture("player_attackleft"), 3, 1, 64),
						new Texture(new Texture("player_attackleft"), 4, 1, 64)),
				new Animation(10, new Texture(new Texture("player_attackright"), 1, 1, 64),
						new Texture(new Texture("player_attackright"), 2, 1, 64),
						new Texture(new Texture("player_attackright"), 3, 1, 64),
						new Texture(new Texture("player_attackright"), 4, 1, 64)));
		playerInventory = new Inventory(320, 240);
		isPlayer = true;
		isPaused = false;
		inTown = town;
		SCORE = 0;
		HEALTH = 3;
		LIVES = Main.Main.playerLives;
	}

	@Override
	public void tick() {
		playerInventory.tick();
		if (!playerInventory.getIsOpen() && !isPaused && !inTown) {
			if (KeyInput.isKeyDown(KeyEvent.VK_X)) {
				isAttacking = true;
			}
			if (KeyInput.wasKeyReleased(KeyEvent.VK_X)) {
				isAttacking = false;
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_W) || KeyInput.isKeyDown(KeyEvent.VK_SPACE)
					|| KeyInput.isKeyDown(KeyEvent.VK_0)) {
				jump(10);
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_A) || KeyInput.isKeyDown(KeyEvent.VK_LEFT)) {
				facingLeft = true;
				facingRight = false;
				VelX = -4;
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_D) || KeyInput.isKeyDown(KeyEvent.VK_RIGHT)) {
				facingLeft = false;
				facingRight = true;
				VelX = 4;
			}
			if (KeyInput.wasKeyReleased(KeyEvent.VK_A) || KeyInput.wasKeyReleased(KeyEvent.VK_LEFT)
					|| KeyInput.wasKeyReleased(KeyEvent.VK_D) || KeyInput.wasKeyReleased(KeyEvent.VK_RIGHT)) {
				VelX = 0;
			}
			if (KeyInput.wasKeyPressed(KeyEvent.VK_UP)) {
				isEnteringDoor = true;
			}
			if (KeyInput.wasKeyReleased(KeyEvent.VK_UP)) {
				isEnteringDoor = false;
			}
		} else if (!playerInventory.getIsOpen() && !isPaused && inTown) {
			if (KeyInput.isKeyDown(KeyEvent.VK_A) || KeyInput.isKeyDown(KeyEvent.VK_LEFT)) {
				facingLeft = true;
				facingRight = false;
				// create facingUp and facingDown variables (for animations and sprite movement)
				VelX = -4;
			}
			if (KeyInput.isKeyDown(KeyEvent.VK_D) || KeyInput.isKeyDown(KeyEvent.VK_RIGHT)) {
				facingLeft = false;
				facingRight = true;
				// create facingUp and facingDown variables (for animations and sprite movement)
				VelX = 4;
			}
			if (KeyInput.wasKeyReleased(KeyEvent.VK_A) || KeyInput.wasKeyReleased(KeyEvent.VK_LEFT)
					|| KeyInput.wasKeyReleased(KeyEvent.VK_D) || KeyInput.wasKeyReleased(KeyEvent.VK_RIGHT)) {
				VelX = 0;
			}
			if (KeyInput.wasKeyPressed(KeyEvent.VK_UP) || KeyInput.wasKeyPressed(KeyEvent.VK_W)) {
				isEnteringDoor = true;
				VelY = -4;
			}
			if (KeyInput.wasKeyReleased(KeyEvent.VK_UP) || KeyInput.wasKeyReleased(KeyEvent.VK_W)) {
				isEnteringDoor = false;
				VelY = 0;
			}
			if (KeyInput.wasKeyPressed(KeyEvent.VK_DOWN) || KeyInput.wasKeyPressed(KeyEvent.VK_S)) {
				VelY = 4;
			}
			if (KeyInput.wasKeyReleased(KeyEvent.VK_DOWN) || KeyInput.wasKeyReleased(KeyEvent.VK_S)) {
				VelY = 0;
			}
		} else if (playerInventory.getIsOpen() || isPaused) {
			VelX = 0;
			VelY = 0;
			isAttacking = false;
		}
		super.tick();
	}

	@Override
	public void render(Graphics2D g, int offsetX, int offsetY) {
		if (isPlayer) {
			if (!isMoving) {
				if (isAttacking) {
					if (facingRight) {
						playerAnimationAttackRight.render(g, x + offsetX, y + offsetY);
					} else if (facingLeft) {
						playerAnimationAttackLeft.render(g, x + offsetX, y + offsetY);
					}
				} else
					super.render(g, offsetX, offsetY);
			} else {
				if (isAttacking) {
					if (facingRight) {
						playerAnimationAttackRight.render(g, x + offsetX, y + offsetY);
					} else if (facingLeft) {
						playerAnimationAttackLeft.render(g, x + offsetX, y + offsetY);
					}
				}
				if (isMovingRight() && !isAttacking)
					playerAnimationRight.render(g, x + offsetX, y + offsetY);
				else if (isMovingLeft() && !isAttacking)
					playerAnimationLeft.render(g, x + offsetX, y + offsetY);
			}
		}
	}

	public void decrementHealth() {
		HEALTH--;
	}

	public void postRender(Graphics2D g, int offsetX, int offsetY) {
		playerInventory.render(g, x + offsetX, y);
	}

	public Inventory getInventory() {
		return playerInventory;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean getPaused() {
		return isPaused;
	}

	public void setInventory(Inventory inventory) {
		this.playerInventory = inventory;
	}
}