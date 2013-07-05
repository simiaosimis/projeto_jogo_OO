package jogo;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
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
	private float gravidade=9.8f;
	private float velocidade=0.0f;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = null;
	private BufferedImage imageMaconha = null;
	private BufferedImage spriteSheet = null;
	private BufferedImage inimigoImage = null;

	private Player p;
	private Plataforma chao = new Plataforma(0,HEIGHT*SCALE-68,WIDTH*SCALE);
	private Plataforma plat= new Plataforma(300,274,100);
	private Plataforma plat2= new Plataforma(250,375,100);
	private Plataforma plat3= new Plataforma(0,316,195);
	private Plataforma[] plats = {plat, plat2, plat3, chao};
	private Inimigo inimigo1;
	private Inimigo inimigo2;
	private Inimigo inimigo[] = {inimigo1,inimigo2}; 
	private Maconha maconha;
	Audio audio= new Audio();
			
	
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImageLoader cenario = new BufferedImageLoader();
		BufferedImageLoader inimigoLoader = new BufferedImageLoader();
		BufferedImageLoader maconhaLoader = new BufferedImageLoader();
		try{
			String local = System.getProperty("user.dir"); 
			System.out.println(local);
			inimigoImage = inimigoLoader.loadImage("/jogo/bandido.png");
			image = cenario.loadImage("/jogo/cenario.png");
			spriteSheet = loader.loadImage("/jogo/player.png");
			imageMaconha = maconhaLoader.loadImage("/jogo/maconha.png");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		addKeyListener(new Keyboard(this));

		p = new Player(500,300,68,35,this);
		inimigo[0]= new Inimigo(0,241,60,35,this);
		inimigo[0].setLimite(480);
		inimigo[1]= new Inimigo(0,100,60,35,this);
		inimigo[1].setLimite(470);
		maconha= new Maconha(300,250,30,30,this);
		p.setPlat(plats);
		
		audio.tocarMorreu();
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
		inimigo[0].tick();
		inimigo[1].tick();
		if(colisao(p,inimigo))init();
		if(colisaoTiro(p,inimigo)>=0)inimigo[colisaoTiro(p,inimigo)].morto=true;
		colisaoMaconha(p,maconha);
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
		maconha.render(g);
		if(!inimigo[0].morto)
		inimigo[0].render(g);
		if(!inimigo[1].morto)
		inimigo[1].render(g);
		
		
		
		g.dispose();
		bs.show();
	}
	
	public void aplicarGravidade(Player player){
		
			float tempo = 0.18f;
			velocidade+=gravidade*tempo;
			player.setY(player.getY() + (velocidade * tempo));
			//player.setY((player.getY() + (velocidade * tempo) + (gravidade*(float)Math.pow(tempo, 2))/2.0f)/5);
			//velocidade=gravidade*tempo;
			velocidade= (velocidade>300.0f) ? 300.0f : velocidade;
		
	}
	
	public boolean colisao(Player a, Inimigo[] b){
		
		for(int i=0;i<b.length;i++)
		if(!b[i].morto && a.getBounds().intersects(b[i].getBounds())){
			System.out.println("COLIDIU");
			return true;
		}
		
		return false;
	}
	
	public boolean colisaoMaconha(Player a, Maconha b){
		if(a.getBounds().intersects(b.getBounds())){
			System.out.println("PEGOU MACONHA");
			return true;
		}
		return false;
	}
	
	public int colisaoTiro(Player a, Inimigo[] b){
		
		for(int i=0;i<b.length;i++)
		if(a.getTiroBounds().intersects(b[i].getBounds())){
			System.out.println("MORREU");
			return i;
		}
		
		return -1;
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
			p.setSaltar(true);
		}
		if(key == KeyEvent.VK_UP){
		
		}
		if(key == KeyEvent.VK_DOWN){
			if(p.isPermiteTiro()){
			p.setAtirar(true);
			p.setXtiro(p.getX());
			p.setYtiro(p.getY());
			}
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_SPACE){
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
	
	public BufferedImage getInimigoImage(){
		return inimigoImage;
	}

	public float getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(float velocidade) {
		this.velocidade = velocidade;
	}

	public BufferedImage getImageMaconha() {
		return imageMaconha;
	}

	public void setImageMaconha(BufferedImage imageMaconha) {
		this.imageMaconha = imageMaconha;
	}

}