package jogo;

public class Pessoa {
	
	protected int x;
	protected int y;
	protected int altura;
	protected int largura;
	
	public Pessoa(){}
	
	public Pessoa(int x, int y, int altura, int largura) {
		super();
		this.x = x;
		this.y = y;
		this.altura = altura;
		this.largura = largura;
	}
	
	public void atirar(){}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

}
