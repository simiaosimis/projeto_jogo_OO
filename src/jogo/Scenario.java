package jogo;

public class Scenario {

	private int height;
	private int width;

	public Scenario() {
		this.width = 0;
		this.height = 0;

	}

	public Scenario(int width, int height) {
		this.width = width;
		this.height = height;

	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
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
	 * g.drawImage(img, height, width, this);
	 * }
	 */
}