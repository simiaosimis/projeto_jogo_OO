package jogo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Player extends Pessoa {
	

	private Plataforma[] plat;
	private boolean plataforma=false;
	private boolean chao=false;
	private boolean saltar=false;
	private int posicao=1;
	private Game game;
	private float xtiro;
	private float ytiro;
	private boolean permiteTiro=true;
	private int i=0;
	private BufferedImage player;
	
	public Player(int x, int y, int altura, int largura, Game game){
		super(x, y, altura, largura,false,false,false);
		this.game=game;
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		player = ss.grabImage(posicao, 1, 35, 68);
	}
	
	public void tick(){
		if(posicao<3)
			posicao++;
		else 
			posicao=0;
		
		if(esquerda)
			x-=4;
		if(direita)
			x+=4;
		
		if(x>=590)x=590;
		if(x<=0)x=0;
		
		for(int i = 0; i<plat.length-1; i++)
		if((x+largura)>plat[i].getX() && x<(plat[i].getX()+plat[i].getLargura()) && (y+altura)>=plat[i].getY() && (y+altura)<=(plat[i].getY()+7)){
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
		
		if(!plataforma && !chao)
			game.aplicarGravidade(this);
		
		if(this.saltar && (plataforma || chao)){
			game.setVelocidade(-40);
			game.aplicarGravidade(this);
				/*while(i<15){
					y-=5;
					i++;
				}*/
			this.saltar=false;
			i=0;
			}
		}
	

	public void render(Graphics g){
		
		g.drawImage(player, (int)x, (int)y, null);
		if(atirar & permiteTiro){
			permiteTiro = false;
		}
		if(atirar)
			atirar(xtiro-=3,ytiro);
		if(xtiro<0)
			atirar = false;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,largura,altura);
	}
	
	public Rectangle getTiroBounds(){
		return new Rectangle((int)xtiro,(int)ytiro,30,30);
	}
	
	public void atirar(float x, float y){
		BufferStrategy bs = game.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.RED);
		g.fillRect((int)x,(int)y,30,30);
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

	public float getXtiro() {
		return xtiro;
	}

	public void setXtiro(float xtiro) {
		this.xtiro = xtiro;
	}

	public float getYtiro() {
		return ytiro;
	}

	public void setYtiro(float ytiro) {
		this.ytiro = ytiro;
	}

	public boolean isPermiteTiro() {
		return permiteTiro;
	}

	public void setPermiteTiro(boolean permiteTiro) {
		this.permiteTiro = permiteTiro;
	}

}