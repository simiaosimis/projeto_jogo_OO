/* File: Keyboard.java
 * 
 * Package: src/jogo
 * 
 * Description: This is the Keyboard class responsible for handle the keyboard input.
 * 
 */

package jogo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {

	Game game;

	public Keyboard(Game game) {

		this.game = game;
	}

	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
}