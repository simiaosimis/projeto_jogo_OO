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

	public Player(int x, int y, int height, int width, Game game) {
		super(x, y, height, width, false, false, false);
		this.game = game;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		player = ss.grabImage(position, 1, 35, 68);
	}

	public void tick() {
		if (position < 3) position++;
		else position = 0;

		if (left) x -= 4;
		if (right) x += 4;

		if (x >= 590) x = 590;
		if (x <= 0) x = 0;

		for (int i = 0; i < plat.length - 1; i++)
			if ((x + width) > plat[i].getX() && x < (plat[i].getX() + plat[i].getwidth())
					&& (y + height) >= plat[i].getY() && (y + height) <= (plat[i].getY() + 7)
					&& game.getSpeed() > 0) {
				// System.out.println("Aqui tem uma plataform!");
				plataform = true;
				break;
			}
			else plataform = false;
		if ((x + this.width) > plat[plat.length - 1].getX()
				&& x < (plat[plat.length - 1].getX() + plat[plat.length - 1].getwidth())
				&& (y + this.height) >= plat[plat.length - 1].getY()) {
			// System.out.println("Aqui tem um floor!");
			floor = true;
		}
		else floor = false;

		if (!plataform && !floor) game.aplicarGravidade(this);

		if (this.jump && (plataform || floor)) {
			game.setSpeed(-40);
			game.aplicarGravidade(this);
			/*
			 * while(i<15){
			 * y-=5;
			 * i++;
			 * }
			 */
			this.jump = false;
			i = 0;
		}
	}

	Audio audio = new Audio();

	public void render(Graphics g) {

		g.drawImage(player, (int) x, (int) y, null);
		if (shoot & doShot) {
			doShot = false;
		}
		if (shoot) if (direction == 0) shoot(xshot += 3, yshot);
		else if (direction == 1) shoot(xshot -= 3, yshot);
		if (xshot < 0) shoot = false;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getshotBounds() {
		return new Rectangle((int) xshot, (int) yshot, 30, 30);
	}

	public void shotSound() {
		audio.setFileName("C:/Users/Jota/Desktop/som de shot.wav");
		audio.playMusic();
	}

	public void shoot(float x, float y) {
		BufferStrategy bs = game.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(game.getshotImage(), (int) x, (int) y + 20, 20, 20, game);
	}

	public boolean getJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public plataform[] getPlat() {
		return plat;
	}

	public boolean isPlataform() {
		return plataform;
	}

	public void setPlataform(boolean plataform) {
		this.plataform = plataform;
	}

	public void setPlat(plataform plat[]) {
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