/* File: Item.java
 * 
 * Package: src/jogo
 * 
 * Description: This is the Item class responsible for the updates and render the items of the game.
 * 
 */

package jogo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Item extends Renderable 

{

	private Game game;
	private BufferedImage item;

	Item(int x, int y, int 

height, int width, Game game) {

		super(x, y, height, 

width, false, false, false);
		this.game = game;

		SpriteSheet ss = new 

SpriteSheet(game.getImageitem());

		item = ss.grabImage

(1, 1, 38, 42);
	}

	public Rectangle getBounds() 

{
		return new Rectangle

((int) x, (int) y, width, height);
	}

	public void render(Graphics 

g) {
		g.drawImage(item, 

(int) x, (int) y, null);
	}
}