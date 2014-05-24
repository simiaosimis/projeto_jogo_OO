/* File: BufferedImageLoader.java
 * 
 * Package: src/jogo
 * 
 * Description: This is the BufferedImageLoader class responsible for manage the image load of the game.
 * 
 */

package jogo;

import java.awt.image.BufferedImage;
import java.io.IOException;


import javax.imageio.ImageIO;

// Load a image
public class BufferedImageLoader {

	private BufferedImage image;
	
	public BufferedImage loadImage(String path) throws IOException {

		image = ImageIO.read(Game.class.getResourceAsStream(path));
		return image;
	}
}
