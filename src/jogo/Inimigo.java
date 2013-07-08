package jogo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Inimigo extends Renderizavel{

	private Game game;
	private BufferedImage inimigo;
	private int limite;
	private int limite2=10;
	public boolean morto;
	public int tipo;
	public int direcao=0;
	private float xtiro;
	private float ytiro;

	Audio audio = new Audio();
	public Inimigo( int x, int y, int altura, int largura, Game game, int tipo) {
		super(x,y,altura,largura,false,false,false);
		
		this.game=game;
		this.tipo=tipo;
		morto=false;
		if(tipo==1){
			SpriteSheet ss = new SpriteSheet(game.getInimigoImage());
			inimigo = ss.grabImage(1, 1, 38, 68);
		}
		else if(tipo==2){
			SpriteSheet ss = new SpriteSheet(game.getInimigoImage2());
			inimigo = ss.grabImage(1, 1, 82, 26);
		}
	}
	public void somDeMorte(){
		audio.setFileName("C:/Users/Jota/Desktop/som de morte.wav");
		audio.tocarMusica();
	}
	public void render(Graphics g){
		g.drawImage(inimigo, (int)x, (int)y, null);
		if(atirar)
			if(direcao==0 && x>0 && x<Game.WIDTH*Game.SCALE)
				atirar(xtiro+=3,ytiro);
			else if(direcao==1 && x>0 && x<Game.WIDTH*Game.SCALE)
				atirar(xtiro-=3,ytiro);
		if(xtiro<-2000)
			xtiro = x;
		if(xtiro>Game.WIDTH*Game.SCALE+2000)
			xtiro = x;
	}
	
	public void atirar(float x, float y){
		BufferStrategy bs = game.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(game.getTiroImage(), (int)x, (int)y+20, 20, 20, game);
	}
	
	public Rectangle getTiroBounds(){
		return new Rectangle((int)xtiro,(int)ytiro,20,20);
	}
	
	int cont3=0;
	
	public void tick(){
		if(tipo==1){
		if(x < limite2)
		{
			cont3 = 0;
			x+=1;
		}
		else if(x >= Game.WIDTH*Game.SCALE-limite||cont3!=0)
		{
			cont3++;
			x-=1;
		}
		else if(cont3==0)
		{
			x+=1;
		}
	}
}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x+5,(int)y+5,largura-10,altura-10);
	}
	
	public void setImage(){
		if(tipo==1){
			SpriteSheet ss = new SpriteSheet(game.getInimigoImage());
			inimigo = ss.grabImage(1, 1, 38, 68);
		}
		else if(tipo==2){
			SpriteSheet ss = new SpriteSheet(game.getInimigoImage2());
			inimigo = ss.grabImage(1, 1, 82, 26);
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}
	public int getLimite2() {
		return limite;
	}

	public void setLimite2(int limite2) {
		this.limite2 = limite2;
	}
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public boolean isMorto() {
		return morto;
	}

	public void setMorto(boolean morto) {
		this.morto = morto;
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
	public int getDirecao() {
		return direcao;
	}

	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}
}