package jogo;

public class Cenario {

	private int altura;
	private int largura;

	public Cenario() {
		this.largura = 0;
		this.altura = 0;

	}

	public Cenario(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;

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

	/*
	 * public void paintComponenent(Graphics g){
	 * //M�todo que chama a imagem
	 * Image img = Toolkit.getDefaultToolkit().getImage("cenario.png");
	 * 
	 * MediaTracker mt = new MediaTracker(imagem);
	 * mt.addImage(img, 1);
	 * try {
	 * mt.waitForAll();
	 * } catch (InterruptedException e) {
	 * e.printStackTrace();
	 * }
	 * g.drawImage(img, altura, largura, this);
	 * }
	 */
}