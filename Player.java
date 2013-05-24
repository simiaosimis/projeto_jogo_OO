package pessoa;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
	
	private double x;
	private double y;
	
	private BufferedImage player;
	
	public Player(double x, double y, Game game){
		this.x=x;
		this.y=y;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		player = ss.grabImage(2, 1, 50, 100);
	}
	
	public void tick(){
		x++;
		if(x>=590)x=0;
	}
	
	public void render(Graphics g){
		g.drawImage(player, (int)x, (int)y, null);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
