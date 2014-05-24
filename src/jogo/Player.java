/* File: Player.java
 * 
 * Package: src/jogo
 * 
 * Description: This is the Player class responsible for update and render the player of the 
 * game
 */

package jogo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Player extends Renderable {

	private Plataform[] plat;
	private boolean plataform = false;
	private boolean floor = false;
	private boolean jump = false;
	private int position = 1;
	private Game game;
	private float xshot;
	private float yshot;
	private boolean doShot = true;
	private int i = 0;
	private BufferedImage player;
	private int direction = 1;
	private boolean dead = false;

	// Constructor of Player class.
	public Player(int x, int y, int height, int width, Game game) {
		super(x, y, height, width, false, false, false);
		this.game = game;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		player = ss.grabImage(position, 1, 35, 68);
	}

	// Updates Player position.
	public void tick() {
		if (position < 3) {
			position++;
		}
		else {
			position = 0;
		}

		if (left) {
			x -= 4;
		}
		else {
			// Nothing to do.
		}
		if (right) {
			x += 4;
		}
		else {
			// Nothing to do.
		}

		if (x >= 590) {
			x = 590;
		}
		else {
			// Nothing to do.
		}
		if (x <= 0) {
			x = 0;
		}
		else {
			// Nothing to do.
		}

		// Here we have a platform check collision.
		for (int i = 0; i < plat.length - 1; i++){
			if ((x + width) > plat[i].getX() && x < (plat[i].getX() + plat[i].getWidth())
					&& (y + height) >= plat[i].getY() && (y + height) <= (plat[i].getY() + 7)
					&& game.getSpeed() > 0) {
				plataform = true;
				break;
			}
			else {
				plataform = false;
			}
		}
		
		// Here we have a floor.
		if ((x + this.width) > plat[plat.length - 1].getX()
				&& x < (plat[plat.length - 1].getX() + plat[plat.length - 1].getWidth())
				&& (y + this.height) >= plat[plat.length - 1].getY()) {
			floor = true;
		}
		else {
			floor = false;
		}

		if (!plataform && !floor) {
			game.applyGravity(this);
		}
		else {
			// Nothing to do.
		}

		if (this.jump && (plataform || floor)) {
			game.setSpeed(-40);
			game.applyGravity(this);
			this.jump = false;
			i = 0;
		}
		else {
			// Nothing to do.
		}
	}

	Audio audio = new Audio();

	// Render the player.
	public void render(Graphics g) {

		g.drawImage(player, (int) x, (int) y, null);
		if (shoot & doShot) {
			doShot = false;
		}
		else {
			// Nothing to do
		}
		
		// Update the shoot position if it is shooted by the player.
		if (shoot) {
			if (direction == 0) {
				shoot(xshot += 3, yshot);
			}
			else if (direction == 1) {
				shoot(xshot -= 3, yshot);
			}
			else {
				// Nothing to do.
			}
		}
		else {
			// Nothing to do.
		}
		if (xshot < 0) {
			shoot = false;
		}
		else {
			// Nothing to do.
		}
	}

	// Get the bounds of the player.
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	// Get the bounds of the player's shoot.
	public Rectangle getshotBounds() {
		return new Rectangle((int) xshot, (int) yshot, 30, 30);
	}

	// Plays the shoot sound.
	public void shotSound() {
		audio.setFileName("C:/Users/Jota/Desktop/som de shot.wav");
		audio.playSong();
	}

	// Render the player's shoot.
	public void shoot(float x, float y) {
		BufferStrategy bs = game.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(game.getShotImage(), (int) x, (int) y + 20, 20, 20, game);
	}

	public boolean getJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public Plataform[] getPlat() {
		return plat;
	}

	public boolean isPlataform() {
		return plataform;
	}

	public void setPlataform(boolean plataform) {
		this.plataform = plataform;
	}

	public void setPlat(Plataform plat[]) {
		this.plat = plat;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
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

	public boolean isDoShot() {
		return doShot;
	}

	public void setDoShot(boolean doShot) {
		this.doShot = doShot;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}