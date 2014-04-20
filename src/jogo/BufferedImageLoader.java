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
