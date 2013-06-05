package jogo;

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
	
	private int i = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;

	private Player p;
	private Plataforma chao = new Plataforma(0,HEIGHT*SCALE,WIDTH*SCALE);
	private Plataforma plat= new Plataforma(100,300,100);
	private Plataforma plat2= new Plataforma(400,300,100);
	private Plataforma plat3= new Plataforma(100,100,100);
	private Plataforma[] plats = {plat, plat2, plat3, chao};
	
	private Inimigo inimigo = new Inimigo("Zé", 0, 10, 100,50,200,200,false);
			
	
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		try{
			spriteSheet = loader.loadImage("/image/bandido.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new Keyboard(this));

		p = new Player(500,300,100,50,this);
		
		p.setPlat(plats);
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
	
	public void aplicarGravidade(Player player, int velocidade){
		
		int gravidade;
		gravidade = 1;
		
		player.setY(player.getY() + (velocidade*i) + ((gravidade * i)/10));
		i+=10;		
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT){
			p.setDireita(true);
		}
		else if(key == KeyEvent.VK_LEFT){
			p.setEsquerda(true);
		}
		if(key == KeyEvent.VK_SPACE){
			System.out.println("APERTOU a tecla espaço");
			p.setSaltar(true);
		}
		if(key == KeyEvent.VK_UP){
			inimigo.atirar();
		}
		if(key == KeyEvent.VK_DOWN){
			p.setAtirar(true);
			p.setXtiro(p.getX());
			p.setYtiro(p.getY());
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_SPACE){
			System.out.println("Soltou a tecla espaço");
		}
		if(key == KeyEvent.VK_RIGHT){
			p.setDireita(false);
		}
		else if(key == KeyEvent.VK_LEFT){
			p.setEsquerda(false);
		}
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

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

}