package jogo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Enemy extends Renderable {

	private Game game;
	private BufferedImage enemy;
	private int leftLimit;
	private int rightLimit = 10;
	public boolean dead;
	public int enemyType;
	public int direction = 0;
	private float xshot;
	private float yshot;
	private int cont3 = 0;

	Audio audio = new Audio();

	public Enemy(int x, int y, int height, int width, Game game, int enemyType) {
		super(x, y, height, width, false, false, false);

		this.game = game;
		this.enemyType = enemyType;
		dead = false;
		if (enemyType == 1) {
			SpriteSheet ss = new SpriteSheet(game.getEnemyImage());
			this.enemy = ss.grabImage(1, 1, 38, 68);
		}
		else if (enemyType == 2) {
			SpriteSheet ss = new SpriteSheet(game.getEnemyImage2());
			this.enemy = ss.grabImage(1, 1, 82, 26);
		}
		else {
			// Nothing to do.
		}
	}

	public void deathSound() {
		audio.setFileName("C:/Users/Jota/Desktop/som de morte.wav");
		audio.playSong();
	}

	public void render(Graphics g) {
		g.drawImage (this.enemy, (int) x, (int) y, null);
		if (shoot) {
			if (direction == 0 && x > 0 && x < Game.WIDTH * Game.SCALE) {
				shoot(xshot += 3, yshot);
			}	
			else if (direction == 1 && x > 0 && x < Game.WIDTH * Game.SCALE) {
				shoot(xshot -= 3, yshot);
			} else {
				// Nothing to do
			}
			if (xshot < -2000) {
				xshot = x;
			} else {
				// Nothing to do
			}
			if (xshot > Game.WIDTH * Game.SCALE + 2000) {
				xshot = x;	
			} else {
				// Nothing to do
			}
		}
	}

	public void shoot(float x, float y) {
		BufferStrategy bs = game.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(game.getShotImage(), (int) x, (int) y + 20, 20, 20, game);
	}

	public Rectangle getshotBounds() {
		return new Rectangle((int) xshot, (int) yshot, 20, 20);
	}

	public void tick() {
		if (enemyType == 1) {
			if (x < rightLimit) {
				cont3 = 0;
				x += 1;
			}
			else if (x >= Game.WIDTH * Game.SCALE - leftLimit || cont3 != 0) {
				cont3++;
				x -= 1;
			}
			else if (cont3 == 0) {
				x += 1;
			}
			else {
				// Nothing to do
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x + 5, (int) y + 5, width - 10, height - 10);
	}

	public void setImage() {
		if (enemyType == 1) {
			SpriteSheet ss = new SpriteSheet(game.getEnemyImage());
			this.enemy = ss.grabImage(1, 1, 38, 68);
		}
		else if (enemyType == 2) {
			SpriteSheet ss = new SpriteSheet(game.getEnemyImage2());
			this.enemy = ss.grabImage(1, 1, 82, 26);
		} else {
			// Nothing to do
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getLeftLimit() {
		return leftLimit;
	}

	public void setLeftLimit(int leftLimit) {
		this.leftLimit = leftLimit;
	}

	public int getRightLimit() {
		return leftLimit;
	}

	public void setRightLimit(int rightLimit) {
		this.rightLimit = rightLimit;
	}

	public int getEnemyType() {
		return enemyType;
	}

	public void setEnemyType(int enemyType) {
		this.enemyType = enemyType;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public float getXshot() {
		return xshot;
	}

	public void setXshot(float xshot) {
		this.xshot = xshot;
	}

	public float getYshot() {
		return yshot;
	}

	public void setYshot(float yshot) {
		this.yshot = yshot;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}