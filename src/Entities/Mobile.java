package Entities;

import java.awt.Graphics2D;

import Graphical.Animation;
import Graphical.Texture;
import LevelUtility.LevelMap;
import TownUtility.TownMap;

public class Mobile extends Entity {

	protected double VelX, VelY;
	protected double MAXVelY, GRAVITY;
	protected boolean isFalling, canJump, isMoving;
	protected Animation playerAnimationLeft, playerAnimationRight, mobAnimation;
	protected Animation playerAnimationAttackLeft, playerAnimationAttackRight;
	protected static int LIVES, HEALTH;
	protected boolean isDamaged, isAttacking, isEnteringDoor, inTown;
	public static int SCORE = 0;

	public Mobile(Texture texture, double x, double y, LevelMap levelMap, Animation animation) {
		super(texture, x, y, levelMap);
		this.mobAnimation = animation;
		isFalling = true;
		GRAVITY = .5;
		MAXVelY = 7;
		isDamaged = false;
		isAttacking = false;
		isEnteringDoor = false;
	}

	public Mobile(Texture textureleft, Texture textureright, double x, double y, LevelMap levelMap, Animation animation,
			Animation animation2, Animation animationattackleft, Animation animationattackright) {
		super(textureleft, textureright, x, y, levelMap);
		this.playerAnimationLeft = animation;
		this.playerAnimationRight = animation2;
		this.playerAnimationAttackLeft = animationattackleft;
		this.playerAnimationAttackRight = animationattackright;
		isFalling = true;
		GRAVITY = .5;
		MAXVelY = 7;
		isDamaged = false;
		isAttacking = false;
		isEnteringDoor = false;
	}

	public Mobile(Texture textureleft, Texture textureright, double x, double y, TownMap townMap, Animation animation,
			Animation animation2, Animation animationattackleft, Animation animationattackright) {
		super(textureleft, textureright, x, y, townMap);
		this.playerAnimationLeft = animation;
		this.playerAnimationRight = animation2;
		this.playerAnimationAttackLeft = animationattackleft;
		this.playerAnimationAttackRight = animationattackright;
		isFalling = true;
		GRAVITY = .5;
		MAXVelY = 7;
		isDamaged = false;
		isAttacking = false;
		isEnteringDoor = false;
	}

	public Mobile(Texture textureleft, Texture textureright, double x, double y, LevelMap levelMap, Animation animation,
			Animation animation2) {
		super(textureleft, textureright, x, y, levelMap);
		this.playerAnimationLeft = animation;
		this.playerAnimationRight = animation2;
		isFalling = true;
		GRAVITY = .5;
		MAXVelY = 7;
		isDamaged = false;
		isAttacking = false;
		isEnteringDoor = false;
	}

	public void move() {
		if (!inTown) {
			boolean hasHorizontalCollision = levelMap.getTileCollision(textureleft.getWidth(), x, y, x + VelX, y,
					false);
			boolean hasVerticalCollision = levelMap.getTileCollision(textureleft.getWidth(), x, y, x, y + VelY, true);
			if (!hasHorizontalCollision) {
				x += VelX;
			}
			if (!hasVerticalCollision) {
				y += VelY;
			}
		} else if (inTown) {
			boolean hasHorizontalCollision = townMap.getTileCollision(textureleft.getWidth(), x, y, x + VelX, y,
					false);
			boolean hasVerticalCollision = townMap.getTileCollision(textureleft.getWidth(), x, y, x, y + VelY, true);
			if (!hasHorizontalCollision) {
				x += VelX;
			}
			if (!hasVerticalCollision) {
				y += VelY;
			}
		}
	}

	public void townMove() {
		x += VelX;
		y += VelY;
	}

	public void EnemyMove() {
		boolean hasHorizontalCollision = levelMap.getTileCollisionEnemy(textureleft.getWidth(), x, y, x + VelX, y,
				false);
		boolean hasVerticalCollision = levelMap.getTileCollisionEnemy(textureleft.getWidth(), x, y, x, y + VelY, true);
		if (!hasHorizontalCollision) {
			x += VelX;
		}
		if (!hasVerticalCollision) {
			y += VelY;
		}
	}

	public void jump(double jumpHeight) {
		if (canJump) {
			VelY -= jumpHeight;
			canJump = false;
		}
	}

	protected void gravity() {
		VelY += GRAVITY;
		if (VelY > MAXVelY) {
			VelY = MAXVelY;
		}
	}

	public void setVelY(double VelY) {
		this.VelY = VelY;
	}

	public void setVelX(double VelX) {
		this.VelX = VelX;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public boolean isMovingLeft() {
		return VelX < 0;
	}

	public boolean isMovingRight() {
		return VelX > 0;
	}

	public boolean isFalling() {
		return isFalling;
	}

	@Override
	public void tick() {
		if (!inTown && isPlayer) {
			if (VelY > 0) {
				isFalling = true;
			} else if (VelY < 0) {
				isFalling = false;
			}
			if (isPlayer) {
				if (isAttacking) {
					if (facingLeft) {
						playerAnimationAttackLeft.tick();
					} else if (facingRight) {
						playerAnimationAttackRight.tick();
					}
				}
				move();
				gravity();
				if (VelX != 0)
					isMoving = true;
				else
					isMoving = false;
				if (isMoving) {
					if (VelX > 0 && !isAttacking)
						playerAnimationRight.tick();
					else if (VelX < 0 && !isAttacking)
						playerAnimationLeft.tick();
				}
			}
		} else if (inTown) {
			if (isPlayer) {
				if (isAttacking) {
					if (facingLeft) {
						playerAnimationAttackLeft.tick();
					} else if (facingRight) {
						playerAnimationAttackRight.tick();
					}
				}
				move();
				if (VelX != 0)
					isMoving = true;
				else
					isMoving = false;
				if (isMoving) {
					if (VelX > 0 && !isAttacking)
						playerAnimationRight.tick();
					else if (VelX < 0 && !isAttacking)
						playerAnimationLeft.tick();
				}
			}
		} else {
			if (VelY > 0) {
				isFalling = true;
			} else if (VelY < 0) {
				isFalling = false;
			}
			EnemyMove();
			gravity();
			if (VelX != 0)
				isMoving = true;
			else
				isMoving = false;
			if (isMoving) {
				if (VelX > 0)
					playerAnimationRight.tick();
				else if (VelX < 0)
					playerAnimationLeft.tick();
			}
		}
	}

	@Override
	public void render(Graphics2D g, int offsetX, int offsetY) {
		if (isPlayer) {
			super.render(g, offsetX, offsetY);
		} else if (!isPlayer) {
			if (!isMoving) {
				super.render(g, offsetX, offsetY);
			} else {
				if (isMovingRight())
					playerAnimationRight.render(g, x + offsetX, y + offsetY);
				else if (isMovingLeft())
					playerAnimationLeft.render(g, x + offsetX, y + offsetY);
			}
		}
	}

	public double getVelX() {
		return VelX;
	}

	public double getVelY() {
		return VelY;
	}

	public int getHealth() {
		return HEALTH;
	}

	public int getLives() {
		return LIVES;
	}

	public void setHealth(int newHealth) {
		this.HEALTH = newHealth;
	}

	public void setLives(int newLives) {
		this.LIVES = newLives;
	}

	public void setisAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}

	public boolean getisAttacking() {
		return isAttacking;
	}

	public void incrementScore(int newScore) {
		SCORE += newScore;
	}

	public int getSCORE() {
		return SCORE;
	}

	public boolean getTryingtoEnterDoor() {
		return isEnteringDoor;
	}

}
