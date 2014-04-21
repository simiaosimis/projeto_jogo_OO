package jogo;

public class Fase {

	private int height;
	private int width;
	private boolean complete;// atributo se passou da fase ou nao
	private int qtd_platform;
	public Inimigo enemies[];
	public Player player;
	public Cenario scene;

	public Fase() {
		this.height = 0;
		this.width = 0;
		this.complete = false;
		this.qtd_platform = 0;
	}

	public Fase(int height, int width, boolean complete, int qtd_platform, Inimigo[] enemies, Player player,
			Cenario scene) {
		this.height = height;
		this.width = width;
		this.complete = complete;
		this.qtd_platform = qtd_platform;
		this.enemies = enemies;
		this.player = player;
		this.scene = scene;
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

	public boolean getComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public int getQtd_platform() {
		return qtd_platform;
	}

	public void setQtd_platform(int qtd_platform) {
		this.qtd_platform = qtd_platform;
	}

	public Inimigo[] getEnemies() {
		return enemies;
	}

	public void setEnemies(Inimigo[] enemies) {
		this.enemies = enemies;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Cenario getScene() {
		return scene;
	}

	public void setScene(Cenario scene) {
		this.scene = scene;
	}

}