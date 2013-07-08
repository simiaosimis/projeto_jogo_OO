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
	private float gravidade=9.8f;
	private float velocidade=0.0f;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = null;
	private BufferedImage imageitem = null;
	private BufferedImage spriteSheet = null;
	private BufferedImage inimigoImage = null;
	private BufferedImage inimigoImage2 = null;
	private BufferedImage menu = null;
	private BufferedImage tiro = null;
	private BufferedImage intro1;
	private BufferedImage intro2;
	private BufferedImage intro3;
	private BufferedImage intro[] = {intro1, intro2, intro3};
	private BufferedImage setaEsquerda;
	private BufferedImage setaDireita;
	private BufferedImage raquel;
	private BufferedImage fim;
	private int introducao = 0;

	private Player p;
	private Plataforma chao = new Plataforma(0,412,WIDTH*SCALE);
	private Plataforma plat = new Plataforma(300,285,100);
	private Plataforma plat2= new Plataforma(250,375,100);
	private Plataforma plat3= new Plataforma(0,316,195);
	private Plataforma plat4= new Plataforma(282,143,129);
	private Plataforma plat5= new Plataforma(265,217,162);
	private Plataforma plat6= new Plataforma(491,252,158);
	private Plataforma plat7= new Plataforma(0,183,210);
	private Plataforma plat8= new Plataforma(0,72,411);
	private Plataforma plat9= new Plataforma(457,110,160);
	private Plataforma plat10= new Plataforma(0,0,0);
	private Plataforma[] plats = {plat, plat2, plat3, plat4,plat5,plat6,plat7,plat8,plat9,plat10,chao};
	private Inimigo inimigo1;
	private Inimigo inimigo2;
	private Inimigo inimigo3;
	private Inimigo inimigo4;
	private Inimigo inimigo5;
	private Inimigo inimigo6;
	private Inimigo inimigo[] = {inimigo1,inimigo2,inimigo3,inimigo4,inimigo5,inimigo6}; 
	private Item item1;
	private Item item2;
	private Item item3;
	private Item[] item={item1,item2,item3};
	private boolean renderizaitem[]={true,true,true};
	Audio audio= new Audio();
	private int faseAtual=0;
	private int faseAntiga=0;
	BufferedImageLoader loader = new BufferedImageLoader();



	public void somDeFundo(){
		audio.setFileName("C:/Users/Jota/Desktop/09 Dryad Of The Woods.wav");
		audio.tocarMusica();
	}
	
	public void init(){
		if(faseAtual<2){
			try{
				inimigoImage = loader.loadImage("/jogo/bandido.png");
				inimigoImage2 = loader.loadImage("/jogo/Atirador1.png");
				image = loader.loadImage("/jogo/cenario.png");
				spriteSheet = loader.loadImage("/jogo/player.png");
				imageitem = loader.loadImage("/jogo/item.png");
				menu = loader.loadImage("/jogo/menu.png");
				tiro = loader.loadImage("/jogo/tiro.png");
				intro1 = loader.loadImage("/jogo/Intro 1.png");
				intro2 = loader.loadImage("/jogo/Intro 2.png");
				intro3 = loader.loadImage("/jogo/Intro 3.png");
				setaEsquerda = loader.loadImage("placaEsquerda.png");
				setaDireita = loader.loadImage("placaDireita.png");
				raquel = loader.loadImage("/jogo/raquel.png");
				fim = loader.loadImage("/jogo/fim.png");
			}catch(IOException e){
				e.printStackTrace();
			}
			inimigo[0]= new Inimigo(0,256,60,35,this,1);
			inimigo[0].setLimite(480);
			inimigo[1]= new Inimigo(0,116,60,35,this,1);
			inimigo[1].setLimite(470); 				
			inimigo[2]= new Inimigo(269,150,60,35,this,1);
			inimigo[2].setLimite(250);
			inimigo[2].setLimite2(269);
			inimigo[3]= new Inimigo(-100,116,60,35,this,2);
			inimigo[3].setLimite(470); 	
			inimigo[4]= new Inimigo(-100,256,60,35,this,2);
			inimigo[4].setLimite(480);
			inimigo[5]= new Inimigo(-100,116,60,35,this,2);
			inimigo[5].setLimite(470); 	
			item[0]= new Item(-530,114,30,30,this);
			item[1]= new Item(-530,167,30,30,this);
			item[2]= new Item(-530,500,30,30,this);
			for(int r=0;r<inimigo.length;r++)
				inimigo[r].setMorto(false);
				for(int k=0;k<3;k++)
				renderizaitem[k]=true;
		}
		p = new Player(500,300,68,35,this);
		p.setPlat(plats);
		addKeyListener(new Keyboard(this));
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
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
				render();
				updates++;
				delta--;
			}
			frames++;		
			if(System.currentTimeMillis()-timer> 1000){
				timer+=1000;
				//System.out.println(updates + " ticks , frames " + frames);
				updates=0;
				frames=0;
			}
		}
		stop();
	}
	
	/*public boolean fimDaFase(){
		if(faseAtual==1){
			if(p.getX()<=0 && p.getY()<=25 && p.isPlataforma())
				return true;
		}
		else if(faseAtual==2){
			if(p.getX()<80 && p.getY()<100 && p.isPlataforma())
				return true;
		}
		else if(faseAtual==3){
			if(p.getX()<80 && p.getY()<100 && p.isPlataforma())
				return true;
		}
		else if(faseAtual==4){
			if(p.getX()<80 && p.getY()<100 && p.isPlataforma())
				return true;
		}
		return false;
	}*/
	
	public void tick(){		
		if((p.getX()<30 && p.getY()<26 && p.isPlataforma() && faseAtual == 1) || p.isMorreu()){
			if(!p.isMorreu())
			faseAtual=2;
			System.out.println(faseAtual);
			p.setY(300);
			p.setX(50);
			p.setPermiteTiro(true);
			for(int r=0;r<inimigo.length;r++)
			inimigo[r].setMorto(false);
			for(int k=0;k<3;k++)
			renderizaitem[k]=true;
			inimigo[0].setX(181);inimigo[0].setY(329);inimigo[0].setTipo(2);inimigo[0].setImage();
			inimigo[1].setX(181);inimigo[1].setY(263);inimigo[1].setTipo(2);inimigo[1].setImage();
			inimigo[2].setX(181);inimigo[2].setY(195);inimigo[2].setTipo(2);inimigo[2].setImage();
			inimigo[3].setX(181);inimigo[3].setY(128);inimigo[3].setTipo(2);inimigo[3].setImage();
			inimigo[4].setX(520);inimigo[4].setY(258);inimigo[4].setTipo(1);inimigo[4].setImage();			
			inimigo[4].setLimite(25);
			inimigo[4].setLimite2(520);
			inimigo[5].setX(-200);inimigo[5].setTipo(2);
			plat.setX(487); plat.setY(369); plat.setLargura(54);
			plat2.setX(523); plat2.setY(323); plat2.setLargura(126);
			plat3.setX(456); plat3.setY(293); plat3.setLargura(38);
			plat4.setX(523); plat4.setY(259); plat4.setLargura(126);
			plat5.setX(430); plat5.setY(234); plat5.setLargura(58);
			plat6.setX(523); plat6.setY(201); plat6.setLargura(126);
			plat7.setX(523); plat7.setY(147); plat7.setLargura(126);
			plat8.setX(587); plat8.setY(171); plat8.setLargura(62);
			plat9.setX(587); plat9.setY(112); plat9.setLargura(62);
			plat10.setX(0); plat10.setY(91); plat10.setLargura(573);
			item[0]= new Item(530,114,30,30,this);
			item[1]= new Item(-530,167,30,30,this);
			item[2]= new Item(-530,289,30,30,this);
			for(int o=0;o<inimigo.length;o++){
				inimigo[o].setXtiro(inimigo[o].getX());
				inimigo[o].setYtiro(inimigo[o].getY());
				if(inimigo[o].getTipo()==2)inimigo[o].setAtirar(true);
			if(p.isMorreu())p.setPermiteTiro(true);
			p.setMorreu(false);
			}
		}
		if((p.getX()<50 && p.getY()<50 && p.isPlataforma() &&  faseAtual == 2) || p.isMorreu()){
			if(!p.isMorreu())
			faseAtual=3;
			p.setY(300);
			p.setX(500);
			p.setPermiteTiro(true);
			for(int r=0;r<inimigo.length;r++)
				inimigo[r].setMorto(false);
			for(int k=0;k<3;k++)
			renderizaitem[k]=true;
			inimigo[0].setX(60);inimigo[0].setY(151);inimigo[0].setTipo(2);inimigo[0].setImage();
			inimigo[1].setX(60);inimigo[1].setY(259);inimigo[1].setTipo(2);inimigo[1].setImage();
			inimigo[2].setX(174);inimigo[2].setY(165);inimigo[2].setTipo(1);inimigo[2].setImage();
			inimigo[2].setLimite(320);
			inimigo[2].setLimite2(174);
			inimigo[3].setX(-180);inimigo[3].setY(128);inimigo[3].setTipo(2);inimigo[3].setImage();
			inimigo[4].setX(174);inimigo[4].setY(84);inimigo[4].setTipo(1);inimigo[4].setImage();		
			inimigo[4].setLimite(320);
			inimigo[4].setLimite2(174);

			inimigo[5].setX(-300);inimigo[5].setTipo(1);
			inimigo[5].setLimite(1320);
			inimigo[5].setLimite2(-174);
			plat.setX(50); plat.setY(360); plat.setLargura(70);
			plat2.setX(170); plat2.setY(318); plat2.setLargura(200);
			plat3.setX(380); plat3.setY(285); plat3.setLargura(95);
			plat4.setX(175); plat4.setY(232); plat4.setLargura(160);
			plat5.setX(45); plat5.setY(178); plat5.setLargura(100);
			plat6.setX(175); plat6.setY(150); plat6.setLargura(160);
			plat7.setX(350); plat7.setY(110); plat7.setLargura(100);
			plat8.setX(470); plat8.setY(70); plat8.setLargura(180);
			plat9.setX(0); plat9.setY(0); plat9.setLargura(0);
			plat10.setX(0); plat10.setY(0); plat10.setLargura(0);
			item[0]= new Item(256,114,30,30,this);
			item[1]= new Item(256,197,30,30,this);
			item[2]= new Item(405,182,30,30,this);
			for(int o=0;o<inimigo.length;o++){
				inimigo[o].setXtiro(inimigo[o].getX());
				inimigo[o].setYtiro(inimigo[o].getY());
				if(inimigo[o].getTipo()==2)inimigo[o].setAtirar(true);
			}
			if(p.isMorreu())p.setPermiteTiro(true);
			p.setMorreu(false);
		}
		if((p.getX()>588 && p.getY()<71 && p.isPlataforma() && faseAtual == 3 ) || p.isMorreu()){
			if(!p.isMorreu())
			faseAtual=4;
			p.setY(300);
			p.setX(500);
			p.setPermiteTiro(true);
			for(int r=0;r<inimigo.length;r++)
				inimigo[r].setMorto(false);
			for(int k=0;k<3;k++)
			renderizaitem[k]=true;
			inimigo[0].setX(11);inimigo[0].setY(222);inimigo[0].setTipo(2);inimigo[0].setImage();
			inimigo[1].setX(11);inimigo[1].setY(135);inimigo[1].setTipo(2);inimigo[1].setImage();
			inimigo[2].setX(580);inimigo[2].setY(100);inimigo[2].setTipo(2);inimigo[2].setImage();
			inimigo[3].setX(570);inimigo[3].setY(219);inimigo[3].setTipo(2);inimigo[3].setImage();
			inimigo[4].setX(580);inimigo[4].setY(172);inimigo[4].setTipo(2);inimigo[4].setImage();
			inimigo[2].setDirecao(1);
			inimigo[3].setDirecao(1);
			inimigo[4].setDirecao(1);
			inimigo[5].setX(-580);inimigo[5].setTipo(2);
			plat.setX(210); plat.setY(344); plat.setLargura(80);
			plat2.setX(315); plat2.setY(295); plat2.setLargura(335);
			plat3.setX(0); plat3.setY(262); plat3.setLargura(260);
			plat4.setX(170); plat4.setY(233); plat4.setLargura(80);
			plat5.setX(0); plat5.setY(174); plat5.setLargura(260);
			plat6.setX(170); plat6.setY(145); plat6.setLargura(80);
			plat7.setX(0); plat7.setY(85); plat7.setLargura(260);
			plat8.setX(300); plat8.setY(125); plat8.setLargura(350);
			plat9.setX(0); plat9.setY(0); plat9.setLargura(0);
			plat10.setX(0); plat10.setY(0); plat10.setLargura(0);
			item[0]= new Item(220,112,30,30,this);
			item[1]= new Item(220,200,30,30,this);
			item[2]= new Item(405,98,30,30,this);
			for(int o=0;o<inimigo.length;o++){
				inimigo[o].setXtiro(inimigo[o].getX());
				inimigo[o].setYtiro(inimigo[o].getY());
				if(inimigo[o].getTipo()==2)inimigo[o].setAtirar(true);
			}
			if(p.isMorreu())p.setPermiteTiro(true);
			p.setMorreu(false);
		}
		if((p.getX()<40 && p.getY()<94 && p.isPlataforma() && faseAtual == 4 ))
			introducao = 4;
		if(faseAtual!=faseAntiga){
			if(faseAtual==2){
				faseAntiga=2;
				try {
					image = loader.loadImage("/jogo/Fase 2.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(faseAtual==3){
				faseAntiga=3;
				try {
					image = loader.loadImage("/jogo/Fase 3.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(faseAtual==4){
				faseAntiga=4;
				try {
					image = loader.loadImage("/jogo/Fase 4.png");
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		}
			if((Mouse.getX()>138 && Mouse.getX()<246) && (Mouse.getY()>308 && Mouse.getY()<332)){
				introducao=1;
				Mouse.setMouseY(500);
			}
			if(introducao==2){
			faseAtual=1;
			introducao=3;
			}
			p.tick();
			for(int j=0;j<inimigo.length;j++)
			if(!inimigo[j].morto)inimigo[j].tick();
			if(colisao(p,inimigo)){
				if(faseAtual==1)
					init();
				else
				p.setMorreu(true);
			}
			if(colisaoTiro(p,inimigo)>=0){
				inimigo[colisaoTiro(p,inimigo)].morto=true;
				if(p.getDirecao()==0)p.setXtiro(1000);
				else if(p.getDirecao()==1)p.setXtiro(-50);
			}
			if(colisaoTiroPlayer(p,inimigo)){
				for(int i=0;i<inimigo.length;i++)
				if(inimigo[i].getDirecao()==0)inimigo[i].setXtiro(1000);
				else if(inimigo[i].getDirecao()==1)inimigo[i].setXtiro(-50);
				p.setMorreu(true);
			}
			colisaoItem(p,item);
			System.out.println("x:  " + Mouse.getX() + " y: " + Mouse.getY() );
	}

	int o = 0;
	public void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			for(int j=0;j<item.length;j++)
				if(renderizaitem[j])
					item[j].render(g);
		g.fillRect(Mouse.getX()-2,Mouse.getY()-2,4,4);
		p.render(g);
			for(int j=0;j<inimigo.length;j++)
				if(!inimigo[j].morto)
						inimigo[j].render(g);
			if(faseAtual==0)
				g.drawImage(menu, 0, 0, getWidth(), getHeight(), this);	
			if(introducao==1){
				g.drawImage(intro1, 0, 0, getWidth(), getHeight(), this);
				o++;
				if(o>=30){
					g.drawImage(intro2, 0, 0, getWidth(), getHeight(), this);
				}
				if(o>=60){
					g.drawImage(intro3, 0, 0, getWidth(), getHeight(), this);
				}
				if(o>=90)
				introducao=2;
			}	
			
				if(faseAtual==1)
					g.drawImage(setaEsquerda,0,25,43, 46, this);
				if(faseAtual==2){
					g.drawImage(setaEsquerda,0,47,43, 46, this);
				}
				if(faseAtual==3){
					g.drawImage(setaDireita,588,25,43, 46, this);
				}
				if(faseAtual==4){
					g.drawImage(raquel,0,18,38, 68, this);

			}
				if(introducao==4)
					g.drawImage(fim,0,0, getWidth(), getHeight(), this);
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
			if(b[i].getTipo()==1){
				if(!b[i].morto && a.getBounds().intersects(b[i].getBounds())){
					System.out.println("COLIDIU");
					inimigo[0].somDeMorte();
					return true;
				}
			}
		
		return false;
	}
	
	public boolean colisaoItem(Player a, Item[] b){
		for(int i=0;i<b.length;i++)
		if(a.getBounds().intersects(b[i].getBounds())){
			renderizaitem[i]=false;
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
	
	public boolean colisaoTiroPlayer(Player a, Inimigo[] b){
		for(int i=0;i<b.length;i++)
		if(a.getBounds().intersects(b[i].getTiroBounds())){
			inimigo[0].somDeMorte();
			return true;
			}
		return false;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT){
			p.setDireita(true);
			if(p.isPermiteTiro())
			p.setDirecao(0);
		}
		else if(key == KeyEvent.VK_LEFT){
			p.setEsquerda(true);
			if(p.isPermiteTiro())
			p.setDirecao(1);
		}
		if(key == KeyEvent.VK_SPACE){
			p.setSaltar(true);
		}
		if(key == KeyEvent.VK_UP){

		}
		if(key == KeyEvent.VK_DOWN){
			if(p.isPermiteTiro()){
				p.setXtiro(p.getX());
				p.setYtiro(p.getY());
				p.setAtirar(true);
				p.somDeTiro();
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
		game.somDeFundo();
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	public BufferedImage getInimigoImage(){
		return inimigoImage;
	}
	
	public BufferedImage getInimigoImage2(){
		return inimigoImage2;
	}
	
	public void setInimigoImage(BufferedImage inimigoImage){
		 this.inimigoImage=inimigoImage;
	}

	public float getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(float velocidade) {
		this.velocidade = velocidade;
	}

	public BufferedImage getImageitem() {
		return imageitem;
	}

	public void setImageitem(BufferedImage imageitem) {
		this.imageitem = imageitem;
	}
	
	public BufferedImage getTiroImage() {
		return tiro;
	}

}