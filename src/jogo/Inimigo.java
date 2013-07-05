package jogo;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Inimigo extends Pessoa{

	private String nome;
	private int movimento;
	public boolean tiro;
	public Fase fase;
	private Game game;
	private BufferedImage inimigo;
	private int limite;
	public boolean morto;
	
	public Inimigo( int x, int y, int altura, int largura, Game game) {
		super(x,y,altura,largura,false,false,false);
		
		this.game=game;
		morto=false;
		SpriteSheet ss = new SpriteSheet(game.getInimigoImage());
		
		inimigo = ss.grabImage(1, 1, 50, 75);
		}
	
	public void render(Graphics g){
		g.drawImage(inimigo, (int)x, (int)y, null);
	}
	
	int cont3=0;
	
	public void tick(){
		if(x < 10)
		{
			cont3 = 0;
			x+=1;
		}
		else if(x >= game.WIDTH*game.SCALE-limite||cont3!=0)
		{
			cont3++;
			x-=1;
		}
		else if(cont3==0)
		{
			x+=1;
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,largura,altura);
	}
	
	public void atirar(){
		System.out.println("O inimigo atirou!");
	}
		
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getMovimento() {
		return movimento;
	}

	public void setMovimento(int movimento) {
		this.movimento = movimento;
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
	public boolean getTiro() {
		return tiro;
	}

	public void setTiro(boolean tiro) {
		this.tiro = tiro;
	}
	
	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}
	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}
}