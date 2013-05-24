package pessoa;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	public static final int WIDTH = 320;
	public static final int HEIGHT = (WIDTH / 12) * 9;
	public static final int SCALE =2;
	public final String TITLE = "Jogo do Morro";
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;

	private Player p;
	
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("/image/bandido.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new Keyboard(this));

		p = new Player(200,100, this);
	}
	
	private synchronized void start(){
		if(running)
			return;
		
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop(){
		if(!running)
			return;
		
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run(){
		init();
		long lastTime = System.nanoTime();
		final double amountOfTickets = 60.0;
		double ns = 1000000000/ amountOfTickets;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		
		while(running){
			//o jogo acontece aqui
			long now = System.nanoTime();
			delta+=(now - lastTime)/ns;
			lastTime = now;
			if(delta>1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis()-timer> 1000){
				timer+=1000;
				System.out.println(updates + " ticks , frames " + frames);
				updates=0;
				frames=0;
			}
		}
		stop();
		
	}
	
	public void tick(){
		
		p.tick();
		
	}
	
	public void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		p.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT){
			p.setX(p.getX()+10);
		}
		else if(key == KeyEvent.VK_LEFT){
			p.setX(p.getX()-10);
		}
		else if(key == KeyEvent.VK_DOWN){
			p.setY(p.getY()+10);
		}
		else if(key == KeyEvent.VK_UP){
			p.setY(p.getY()-10);
		}
		if(key == KeyEvent.VK_SPACE){
			System.out.println(p.getX() +" X , Y " + p.getY());
		}
	}
	
	public void keyReleased(KeyEvent e){
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();

	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}

}
