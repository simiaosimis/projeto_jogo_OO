package jogo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Player extends Pessoa {
	

	private Plataforma[] plat;
	private boolean plataforma=false;
	private boolean chao=false;
	private boolean saltar=false;
	private Game game;
	private int xtiro;
	private int ytiro;
	
	private BufferedImage player;
	
	public Player(int x, int y, int altura, int largura, Game game){
		super(x, y, altura, largura,false,false,false);
		this.game=game;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		player = ss.grabImage(2, 1, 50, 100);
	}
	
	public void tick(){
		if(!plataforma && !chao)
			game.aplicarGravidade(this, 0);
		
		if(esquerda)
			x-=7;
		if(direita)
			x+=7;
		
		if(x>=590)x=590;
		if(x<=0)x=0;
		
		for(int i = 0; i<plat.length-1; i++)
		if((x+largura)>plat[i].getX() && x<(plat[i].getX()+plat[i].getLargura()) && (y+altura)==plat[i].getY()){
			//System.out.println("Aqui tem uma plataforma!");
			plataforma=true;
			break;
		}
		else plataforma=false;
		if((x+this.largura)>plat[plat.length-1].getX() && x<(plat[plat.length-1].getX()+plat[plat.length-1].getLargura()) && (y+this.altura)>=plat[plat.length-1].getY()){
			//System.out.println("Aqui tem um chao!");
			chao=true;
		}
		else chao=false;
		
		
		if(this.saltar && (this.plataforma || this.chao)){
			game.aplicarGravidade(this,-1);
			game.setI(1);
			this.saltar=false;
			}
		}
	

	public void render(Graphics g){
		g.drawImage(player, (int)x, (int)y, null);
		if(atirar){
			atirar(xtiro-=3,ytiro);
		}
	}
	
	public void atirar(int x, int y){
		BufferStrategy bs = game.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.RED);
		g.fillRect(x,y,30,30);
	}

	public boolean getSaltar() {
		return saltar;
	}

	public void setSaltar(boolean saltar) {
		this.saltar = saltar;
	}

	public Plataforma[] getPlat() {
		return plat;
	}

	public void setPlat(Plataforma plat[]) {
		this.plat = plat;
	}
	
	
	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getXtiro() {
		return xtiro;
	}

	public void setXtiro(int xtiro) {
		this.xtiro = xtiro;
	}

	public int getYtiro() {
		return ytiro;
	}

	public void setYtiro(int ytiro) {
		this.ytiro = ytiro;
	}
	


}