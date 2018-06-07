package Entities;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Timer;

import GameStates.GameState;
import Graphical.Animation;
import Graphical.Texture;
import Inputs.KeyInput;
import Inputs.MouseInput;
import Inventory.Inventory;
import LevelUtility.LevelMap;

public class Player extends Mobile {

	private Enemy[] enemy;
	private boolean isInvincible = false;
	private Inventory playerInventory;

	public Player(double x, double y, LevelMap levelMap, Enemy... enemy) {
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
		HEALTH = 3;
		this.enemy = new Enemy[enemy.length];
		for (int i = 0; i < enemy.length; i++) {
			this.enemy[i] = enemy[i];
		}
	}

	@Override
	public void tick() {
		if (playerInventory.getIsOpen() == true) {
		if (KeyInput.isKeyDown(KeyEvent.VK_X)) {
			isAttacking = true;
		}if (KeyInput.wasKeyReleased(KeyEvent.VK_X)) {
			isAttacking = false;
		}if (KeyInput.isKeyDown(KeyEvent.VK_W) || KeyInput.isKeyDown(KeyEvent.VK_SPACE)
				|| KeyInput.isKeyDown(KeyEvent.VK_0)) {
			jump(10);
		}if (KeyInput.isKeyDown(KeyEvent.VK_A) || KeyInput.isKeyDown(KeyEvent.VK_LEFT)) {
			facingLeft = true;
			facingRight = false;
			VelX = -4;
		}if (KeyInput.isKeyDown(KeyEvent.VK_D) || KeyInput.isKeyDown(KeyEvent.VK_RIGHT)) {
			facingLeft = false;
			facingRight = true;
			VelX = 4;
		}if (KeyInput.wasKeyReleased(KeyEvent.VK_A) || KeyInput.wasKeyReleased(KeyEvent.VK_LEFT)
				|| KeyInput.wasKeyReleased(KeyEvent.VK_D) || KeyInput.wasKeyReleased(KeyEvent.VK_RIGHT)) {
			VelX = 0;
		}if (KeyInput.wasKeyPressed(KeyEvent.VK_UP)) {
			isEnteringDoor = true;
		}if (KeyInput.wasKeyReleased(KeyEvent.VK_UP)) {
			isEnteringDoor = false;
		}
	}
		playerInventory.tick();
		super.tick();
	}

	@Override
	public void render(Graphics2D g, int offsetX, int offsetY) {
	if (isPlayer) {
		if (!isMoving) {
			if (isAttacking) {
				if (facingRight) {
					playerAnimationAttackRight.render(g, x + offsetX, y + offsetY);
				}else if(facingLeft) {
					playerAnimationAttackLeft.render(g, x + offsetX, y + offsetY);
				}
			}else
			super.render(g, offsetX, offsetY);
		} else {
			if (isAttacking) {
				if (facingRight) {
					playerAnimationAttackRight.render(g, x + offsetX, y + offsetY);
				}else if(facingLeft) {
					playerAnimationAttackLeft.render(g, x + offsetX, y + offsetY);
				}
			}
			if (isMovingRight() && !isAttacking)
				playerAnimationRight.render(g, x + offsetX, y + offsetY);
			else if (isMovingLeft() && !isAttacking)
				playerAnimationLeft.render(g, x + offsetX, y + offsetY);
			}
		playerInventory.render(g, x, y);
		}
	}
	
	public void postRender(Graphics2D g, int offsetX, int offsetY) {
		playerInventory.render(g, offsetX, offsetY);
	}

	public void decrementHealth() {
		if (!isInvincible) {
			HEALTH--;
		}try {
			isInvincible = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		isInvincible = false;
	}
	
	public Inventory getInventory() {
		return playerInventory;
	}
}
