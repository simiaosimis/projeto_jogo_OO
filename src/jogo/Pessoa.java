package jogo;

public class Pessoa {
	
	protected int x;
	protected int y;
	protected int altura;
	protected int largura;
	protected boolean atirar;
	protected boolean esquerda;
	protected boolean direita;
	
	
	public Pessoa(){}
	
	public Pessoa(int x, int y, int altura, int largura, boolean atirar, boolean esquerda, boolean direita) {
		super();
		this.x = x;
		this.y = y;
		this.altura = altura;
		this.largura = largura;
		this.atirar=atirar;
		this.esquerda=esquerda;
		this.direita=direita;
	}
	
	public void atirar(){}
	
	

	public boolean isEsquerda() {
		return esquerda;
	}

	public void setEsquerda(boolean esquerda) {
		this.esquerda = esquerda;
	}

	public boolean isDireita() {
		return direita;
	}

	public void setDireita(boolean direita) {
		this.direita = direita;
	}

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

	public boolean isAtirar() {
		return atirar;
	}

	public void setAtirar(boolean atirar) {
		this.atirar = atirar;
	}
	
	

}
