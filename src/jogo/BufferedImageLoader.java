package jogo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image;
	
	public BufferedImage loadImage(String path) throws IOException{
		
		//URL url =  this.getClass().getResource(path);
		image = ImageIO.read(Game.class.getResourceAsStream(path));
		return image;
	}
}
