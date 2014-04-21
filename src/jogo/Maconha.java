package jogo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Maconha extends Person {

	private Game game;
	private BufferedImage maconha;

	Maconha(int x, int y, int height, int width, Game game) {

		super(x, y, height, width, false, false, false);
		this.game = game;

		SpriteSheet ss = new SpriteSheet(game.getImageMaconha());

		maconha = ss.grabImage(1, 1, 28, 28);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void render(Graphics g) {
		g.drawImage(maconha, (int) x, (int) y, null);
	}
}
