package jogo;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	public static final int WIDTH = 320;
	public static final int HEIGHT = (WIDTH / 12) * 9;
	public static final int SCALE = 2;
	public final String TITLE = "Jogo do Morro";
	private float gravity = 9.8f;
	private float speed = 0.0f;

	private boolean running = false;
	private Thread thread;

	private BufferedImage image = null;
	private BufferedImage imageitem = null;
	private BufferedImage spriteSheet = null;
	private BufferedImage enemyImage = null;
	private BufferedImage enemyImage2 = null;
	private BufferedImage menu = null;
	private BufferedImage shot = null;
	private BufferedImage intro1;
	private BufferedImage intro2;
	private BufferedImage intro3;
	private BufferedImage intro[] = { intro1, intro2, intro3 };
	private BufferedImage leftArrow;
	private BufferedImage rigthArrow;
	private BufferedImage raquel;
	private BufferedImage end;
	private int introduction = 0;

	private Player p;
	private Plataforma floor = new Plataforma(0, 412, WIDTH * SCALE);
	private Plataforma plat = new Plataforma(300, 285, 100);
	private Plataforma plat2 = new Plataforma(250, 375, 100);
	private Plataforma plat3 = new Plataforma(0, 316, 195);
	private Plataforma plat4 = new Plataforma(282, 143, 129);
	private Plataforma plat5 = new Plataforma(265, 217, 162);
	private Plataforma plat6 = new Plataforma(491, 252, 158);
	private Plataforma plat7 = new Plataforma(0, 183, 210);
	private Plataforma plat8 = new Plataforma(0, 72, 411);
	private Plataforma plat9 = new Plataforma(457, 110, 160);
	private Plataforma plat10 = new Plataforma(0, 0, 0);
	private Plataforma[] plats = { plat, plat2, plat3, plat4, plat5, plat6, plat7, plat8, plat9, plat10, floor };
	private enemy enemy1;
	private enemy enemy2;
	private enemy enemy3;
	private enemy enemy4;
	private enemy enemy5;
	private enemy enemy6;
	private enemy enemy[] = { enemy1, enemy2, enemy3, enemy4, enemy5, enemy6 };
	private Item item1;
	private Item item2;
	private Item item3;
	private Item[] item = { item1, item2, item3 };
	private boolean rendersitem[] = { true, true, true };
	Audio audio = new Audio();
	private int currentFase = 0;
	private int lastFase = 0;
	BufferedImageLoader loader = new BufferedImageLoader();

	public void backgroundSound() {
		audio.setFileName("C:/Users/Jota/Desktop/09 Dryad Of The Woods.wav");
		audio.playSong();
	}

	public void init() {
		if (currentFase < 2) {
			try {
				enemyImage = loader.loadImage("/jogo/bandido.png");
				enemyImage2 = loader.loadImage("/jogo/Atirador1.png");
				image = loader.loadImage("/jogo/cenario.png");
				spriteSheet = loader.loadImage("/jogo/player.png");
				imageitem = loader.loadImage("/jogo/item.png");
				menu = loader.loadImage("/jogo/menu.png");
				shot = loader.loadImage("/jogo/shot.png");
				intro1 = loader.loadImage("/jogo/Intro 1.png");
				intro2 = loader.loadImage("/jogo/Intro 2.png");
				intro3 = loader.loadImage("/jogo/Intro 3.png");
				leftArrow = loader.loadImage("placaEsquerda.png");
				rigthArrow = loader.loadImage("placaDireita.png");
				raquel = loader.loadImage("/jogo/raquel.png");
				end = loader.loadImage("/jogo/end.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
			enemy[0] = new enemy(0, 256, 60, 35, this, 1);
			enemy[0].setLeftLimit(480);
			enemy[1] = new enemy(0, 116, 60, 35, this, 1);
			enemy[1].setLeftLimit(470);
			enemy[2] = new enemy(269, 150, 60, 35, this, 1);
			enemy[2].setLeftLimit(250);
			enemy[2].setRightLimit(269);
			enemy[3] = new enemy(-100, 116, 60, 35, this, 2);
			enemy[3].setLeftLimit(470);
			enemy[4] = new enemy(-100, 256, 60, 35, this, 2);
			enemy[4].setLeftLimit(480);
			enemy[5] = new enemy(-100, 116, 60, 35, this, 2);
			enemy[5].setLeftLimit(470);
			item[0] = new Item(-530, 114, 30, 30, this);
			item[1] = new Item(-530, 167, 30, 30, this);
			item[2] = new Item(-530, 500, 30, 30, this);
			for (int r = 0; r < enemy.length; r++)
				enemy[r].setDead(false);
			for (int k = 0; k < 3; k++)
				rendersitem[k] = true;
		}
		else {
			// Nothing to do
		}
		p = new Player(500, 300, 68, 35, this);
		p.setPlat(plats);
		addKeyListener(new Keyboard(this));
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	private synchronized void start() {
		if (running) {
			return;
		}
		else {
			// Nothing to do
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running) {
			return;
		}
		else {
			// Nothing to do
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTickets = 60.0;
		double ns = 1000000000 / amountOfTickets;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			// o jogo acontece aqui
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta > 1) {
				tick();
				render();
				updates++;
				delta--;
			}
			else {
				// Nothing to do
			}
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " ticks , frames " + frames);
				updates = 0;
				frames = 0;
			}
			else {
				// Nothing to do
			}
		}
		stop();
	}

	/*
	 * public boolean endDaFase(){
	 * if(currentFase==1){
	 * if(p.getX()<=0 && p.getY()<=25 && p.isPlataform())
	 * return true;
	 * }
	 * else if(currentFase==2){
	 * if(p.getX()<80 && p.getY()<100 && p.isPlataform())
	 * return true;
	 * }
	 * else if(currentFase==3){
	 * if(p.getX()<80 && p.getY()<100 && p.isPlataform())
	 * return true;
	 * }
	 * else if(currentFase==4){
	 * if(p.getX()<80 && p.getY()<100 && p.isPlataform())
	 * return true;
	 * }
	 * return false;
	 * }
	 */

	public void tick() {
		if ((p.getX() < 30 && p.getY() < 26 && p.isPlataform() && currentFase == 1) || p.isDead()) {
			if (!p.isDead()) {
				currentFase = 2;
			}
			else {
				// Nothing to do
			}
			System.out.println(currentFase);
			p.setY(300);
			p.setX(50);
			p.setDoShot(true);
			for (int r = 0; r < enemy.length; r++){
				enemy[r].setDead(false);
			}
			for (int k = 0; k < 3; k++){
				rendersitem[k] = true;
			}
			enemy[0].setX(181);
			enemy[0].setY(329);
			enemy[0].setType(2);
			enemy[0].setImage();
			enemy[1].setX(181);
			enemy[1].setY(263);
			enemy[1].setType(2);
			enemy[1].setImage();
			enemy[2].setX(181);
			enemy[2].setY(195);
			enemy[2].setType(2);
			enemy[2].setImage();
			enemy[3].setX(181);
			enemy[3].setY(128);
			enemy[3].setType(2);
			enemy[3].setImage();
			enemy[4].setX(520);
			enemy[4].setY(258);
			enemy[4].setType(1);
			enemy[4].setImage();
			enemy[4].setLeftLimit(25);
			enemy[4].setRightLimit(520);
			enemy[5].setX(-200);
			enemy[5].setType(2);
			plat.setX(487);
			plat.setY(369);
			plat.setWidth(54);
			plat2.setX(523);
			plat2.setY(323);
			plat2.setWidth(126);
			plat3.setX(456);
			plat3.setY(293);
			plat3.setWidth(38);
			plat4.setX(523);
			plat4.setY(259);
			plat4.setWidth(126);
			plat5.setX(430);
			plat5.setY(234);
			plat5.setWidth(58);
			plat6.setX(523);
			plat6.setY(201);
			plat6.setWidth(126);
			plat7.setX(523);
			plat7.setY(147);
			plat7.setWidth(126);
			plat8.setX(587);
			plat8.setY(171);
			plat8.setWidth(62);
			plat9.setX(587);
			plat9.setY(112);
			plat9.setWidth(62);
			plat10.setX(0);
			plat10.setY(91);
			plat10.setWidth(573);
			item[0] = new Item(530, 114, 30, 30, this);
			item[1] = new Item(-530, 167, 30, 30, this);
			item[2] = new Item(-530, 289, 30, 30, this);
			for (int o = 0; o < enemy.length; o++) {
				enemy[o].setXshot(enemy[o].getX());
				enemy[o].setYshot(enemy[o].getY());
				if (enemy[o].getTipo() == 2) {
					enemy[o].setShoot(true);
				}
				else {
					// Nothing to do
				}
				if (p.isDead()) {
					p.setDoShot(true);
				}
				else {
					// Nothing to do
				}
				p.setDead(false);
			}
		}
		else {
			// Nothing to do
		}
		if ((p.getX() < 50 && p.getY() < 50 && p.isPlataform() && currentFase == 2) || p.isDead()) {
			if (!p.isDead()) {
				currentFase = 3;
			}
			else {
				// Nothing to do
			}
			p.setY(300);
			p.setX(500);
			p.setDoShot(true);
			for (int r = 0; r < enemy.length; r++){
				enemy[r].setDead(false);
			}
			for (int k = 0; k < 3; k++){
				rendersitem[k] = true;
			}
			enemy[0].setX(60);
			enemy[0].setY(151);
			enemy[0].setType(2);
			enemy[0].setImage();
			enemy[1].setX(60);
			enemy[1].setY(259);
			enemy[1].setType(2);
			enemy[1].setImage();
			enemy[2].setX(174);
			enemy[2].setY(165);
			enemy[2].setType(1);
			enemy[2].setImage();
			enemy[2].setLeftLimit(320);
			enemy[2].setRightLimit(174);
			enemy[3].setX(-180);
			enemy[3].setY(128);
			enemy[3].setType(2);
			enemy[3].setImage();
			enemy[4].setX(174);
			enemy[4].setY(84);
			enemy[4].setType(1);
			enemy[4].setImage();
			enemy[4].setLeftLimit(320);
			enemy[4].setRightLimit(174);

			enemy[5].setX(-300);
			enemy[5].setType(1);
			enemy[5].setLeftLimit(1320);
			enemy[5].setRightLimit(-174);
			plat.setX(50);
			plat.setY(360);
			plat.setWidth(70);
			plat2.setX(170);
			plat2.setY(318);
			plat2.setWidth(200);
			plat3.setX(380);
			plat3.setY(285);
			plat3.setWidth(95);
			plat4.setX(175);
			plat4.setY(232);
			plat4.setWidth(160);
			plat5.setX(45);
			plat5.setY(178);
			plat5.setWidth(100);
			plat6.setX(175);
			plat6.setY(150);
			plat6.setWidth(160);
			plat7.setX(350);
			plat7.setY(110);
			plat7.setWidth(100);
			plat8.setX(470);
			plat8.setY(70);
			plat8.setWidth(180);
			plat9.setX(0);
			plat9.setY(0);
			plat9.setWidth(0);
			plat10.setX(0);
			plat10.setY(0);
			plat10.setWidth(0);
			item[0] = new Item(256, 114, 30, 30, this);
			item[1] = new Item(256, 197, 30, 30, this);
			item[2] = new Item(405, 182, 30, 30, this);
			for (int o = 0; o < enemy.length; o++) {
				enemy[o].setXshot(enemy[o].getX());
				enemy[o].setYshot(enemy[o].getY());
				if (enemy[o].getTipo() == 2) {
					enemy[o].setShoot(true);
				}
				else {
					// Nothing to do
				}
			}
			if (p.isDead()) {
				p.setDoShot(true);
			}
			else {
				// Nothing to do
			}
			p.setDead(false);
		}
		else {
			// Nothing to do
		}
		if ((p.getX() > 588 && p.getY() < 71 && p.isPlataform() && currentFase == 3) || p.isDead()) {
			if (!p.isDead()) {
				currentFase = 4;
			}
			else {
				// Nothing to do
			}
			p.setY(300);
			p.setX(500);
			p.setDoShot(true);
			for (int r = 0; r < enemy.length; r++){
				enemy[r].setDead(false);
			}
			for (int k = 0; k < 3; k++){
				rendersitem[k] = true;
			}
			enemy[0].setX(11);
			enemy[0].setY(222);
			enemy[0].setType(2);
			enemy[0].setImage();
			enemy[1].setX(11);
			enemy[1].setY(135);
			enemy[1].setType(2);
			enemy[1].setImage();
			enemy[2].setX(580);
			enemy[2].setY(100);
			enemy[2].setType(2);
			enemy[2].setImage();
			enemy[3].setX(570);
			enemy[3].setY(219);
			enemy[3].setType(2);
			enemy[3].setImage();
			enemy[4].setX(580);
			enemy[4].setY(172);
			enemy[4].setType(2);
			enemy[4].setImage();
			enemy[2].setDirecao(1);
			enemy[3].setDirecao(1);
			enemy[4].setDirecao(1);
			enemy[5].setX(-580);
			enemy[5].setType(2);
			plat.setX(210);
			plat.setY(344);
			plat.setWidth(80);
			plat2.setX(315);
			plat2.setY(295);
			plat2.setWidth(335);
			plat3.setX(0);
			plat3.setY(262);
			plat3.setWidth(260);
			plat4.setX(170);
			plat4.setY(233);
			plat4.setWidth(80);
			plat5.setX(0);
			plat5.setY(174);
			plat5.setWidth(260);
			plat6.setX(170);
			plat6.setY(145);
			plat6.setWidth(80);
			plat7.setX(0);
			plat7.setY(85);
			plat7.setWidth(260);
			plat8.setX(300);
			plat8.setY(125);
			plat8.setWidth(350);
			plat9.setX(0);
			plat9.setY(0);
			plat9.setWidth(0);
			plat10.setX(0);
			plat10.setY(0);
			plat10.setWidth(0);
			item[0] = new Item(220, 112, 30, 30, this);
			item[1] = new Item(220, 200, 30, 30, this);
			item[2] = new Item(405, 98, 30, 30, this);
			for (int o = 0; o < enemy.length; o++) {
				enemy[o].setXshot(enemy[o].getX());
				enemy[o].setYshot(enemy[o].getY());
				if (enemy[o].getTipo() == 2) {
					enemy[o].setShoot(true);
				}
				else {
					// Nothing to do
				}
			}
			if (p.isDead()) {
				p.setDoShot(true);
			}
			else {
				// Nothing to do
			}
			p.setDead(false);
		}
		else {
			// Nothing to do
		}
		if ((p.getX() < 40 && p.getY() < 94 && p.isPlataform() && currentFase == 4)) {
			introduction = 4;
		}
		else {
			// Nothing to do
		}
		if (currentFase != lastFase) {
			if (currentFase == 2) {
				lastFase = 2;
				try {
					image = loader.loadImage("/jogo/Fase 2.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				// Nothing to do
			}
			if (currentFase == 3) {
				lastFase = 3;
				try {
					image = loader.loadImage("/jogo/Fase 3.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				// Nothing to do
			}
			if (currentFase == 4) {
				lastFase = 4;
				try {
					image = loader.loadImage("/jogo/Fase 4.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				// Nothing to do
			}
		}
		else {
			// Nothing to do
		}
		if ((Mouse.getX() > 138 && Mouse.getX() < 246) && (Mouse.getY() > 308 && Mouse.getY() < 332)) {
			introduction = 1;
			Mouse.setMouseY(500);
		}
		else {
			// Nothing to do
		}
		if (introduction == 2) {
			currentFase = 1;
			introduction = 3;
		}
		else {
			// Nothing to do
		}
		p.tick();
		for (int j = 0; j < enemy.length; j++){
			if (!enemy[j].dead) {
				enemy[j].tick();
			}
			else {
				// Nothing to do
			}
		}
		if (collision(p, enemy)) {
			if (currentFase == 1) {
				init();
			}
			else {
				p.setDead(true);
			}
		}
		else {
			// Nothing to do
		}
		if (collisionShot(p, enemy) >= 0) {
			enemy[collisionShot(p, enemy)].dead = true;
			if (p.getDirecao() == 0) {
				p.setXshot(1000);
			}
			else if (p.getDirecao() == 1) {
				p.setXshot(-50);
			}
			else {
				// Nothing to do
			}
		}
		else {
			// Nothing to do
		}
		if (collisionShotPlayer(p, enemy)) {
			for (int i = 0; i < enemy.length; i++){
				if (enemy[i].getDirecao() == 0) {
					enemy[i].setXshot(1000);
				}
				else if (enemy[i].getDirecao() == 1) {
					enemy[i].setXshot(-50);
				}
				else {
					// Nothing to do
				}
			}
			p.setDead(true);
		}
		else {
			// Nothing to do
		}
		collisionItem(p, item);
		System.out.println("x:  " + Mouse.getX() + " y: " + Mouse.getY());
	}

	int o = 0;

	public void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		else {
			// Nothing to do
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		for (int j = 0; j < item.length; j++){
			if (rendersitem[j]) {
				item[j].render(g);
			}
			else {
				// Nothing to do
			}
		}
		g.fillRect(Mouse.getX() - 2, Mouse.getY() - 2, 4, 4);
		p.render(g);
		for (int j = 0; j < enemy.length; j++){
			if (!enemy[j].dead) {
				enemy[j].render(g);
			}
			else {
				// Nothing to do
			}
		}
		if (currentFase == 0) {
			g.drawImage(menu, 0, 0, getWidth(), getHeight(), this);
		}
		else {
			// Nothing to do
		}
		if (introduction == 1) {
			g.drawImage(intro1, 0, 0, getWidth(), getHeight(), this);
			o++;
			if (o >= 30) {
				g.drawImage(intro2, 0, 0, getWidth(), getHeight(), this);
			}
			else {
				// Nothing to do
			}
			if (o >= 60) {
				g.drawImage(intro3, 0, 0, getWidth(), getHeight(), this);
			}
			else {
				// Nothing to do
			}
			if (o >= 90) {
				introduction = 2;
			}
			else {
				// Nothing to do
			}
		}
		else {
			// Nothing to do
		}

		if (currentFase == 1) {
			g.drawImage(leftArrow, 0, 25, 43, 46, this);
		}
		else {
			// Nothing to do
		}
		if (currentFase == 2) {
			g.drawImage(leftArrow, 0, 47, 43, 46, this);
		}
		else {
			// Nothing to do
		}
		if (currentFase == 3) {
			g.drawImage(rigthArrow, 588, 25, 43, 46, this);
		}
		else {
			// Nothing to do
		}
		if (currentFase == 4) {
			g.drawImage(raquel, 0, 18, 38, 68, this);
		}
		else {
			// Nothing to do
		}
		if (introduction == 4) {
			g.drawImage(end, 0, 0, getWidth(), getHeight(), this);
		}
		else {
			// Nothing to do
		}
		g.dispose();
		bs.show();
	}

	public void applyGravity(Player player) {

		float time = 0.18f;
		speed += gravity * time;
		player.setY(player.getY() + (speed * time));
		// player.setY((player.getY() + (speed * time) + (gravity*(float)Math.pow(time, 2))/2.0f)/5);
		// speed=gravity*time;
		speed = (speed > 300.0f) ? 300.0f : speed;

	}

	public boolean collision(Player a, Enemy[] b) {

		for (int i = 0; i < b.length; i++){
			if (b[i].getTipo() == 1) {
				if (!b[i].dead && a.getBounds().intersects(b[i].getBounds())) {
					System.out.println("COLIDIU");
					enemy[0].deathSound();
					return true;
				}
				else {
					// Nothing to do
				}
			}
			else {
				// Nothing to do
			}
		}
		return false;
	}

	public boolean collisionItem(Player a, Item[] b) {
		for (int i = 0; i < b.length; i++){
			if (a.getBounds().intersects(b[i].getBounds())) {
				rendersitem[i] = false;
				return true;
			}
			else {
				// Nothing to do
			}
		}
		return false;
	}

	public int collisionShot(Player a, Enemy[] b) {
		for (int i = 0; i < b.length; i++){
			if (a.getshotBounds().intersects(b[i].getBounds())) {
				System.out.println("MORREU");
				return i;
			}
			else {
				// Nothing to do
			}
		}
		return -1;
	}

	public boolean collisionShotPlayer(Player a, Enemy[] b) {
		for (int i = 0; i < b.length; i++) {
			if (a.getBounds().intersects(b[i].getshotBounds())) {
				enemy[0].deathSound();
				return true;
			}
			else {
				// Nothing to do
			}
		}
		return false;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			p.setRight(true);
			if (p.isDoShot()) {
				p.setDirecao(0);
			}
			else {
				// Nothing to do
			}
		}
		else if (key == KeyEvent.VK_LEFT) {
			p.setLeft(true);
			if (p.isDoShot()) {
				p.setDirecao(1);
			}
			else {
				// Nothing to do
			}
		}
		else {
			// Nothing to do
		}
		if (key == KeyEvent.VK_SPACE) {
			p.setSaltar(true);
		}
		else {
			// Nothing to do
		}
		if (key == KeyEvent.VK_UP) {
		}
		else {
			// Nothing to do
		}
		if (key == KeyEvent.VK_DOWN) {
			if (p.isDoShot()) {
				p.setXshot(p.getX());
				p.setYshot(p.getY());
				p.setShoot(true);
				p.shotSound();
			}
			else {
				// Nothing to do
			}
		}
		else {
			// Nothing to do
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
		}
		else {
			// Nothing to do
		}
		if (key == KeyEvent.VK_RIGHT) {
			p.setRight(false);
		}
		else if (key == KeyEvent.VK_LEFT) {
			p.setLeft(false);
		}
		else {
			// Nothing to do
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
		game.backgroundSound();
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public BufferedImage getEnemyImage() {
		return enemyImage;
	}

	public BufferedImage getEnemyImage2() {
		return enemyImage2;
	}

	public void setEnemyImage(BufferedImage enemyImage) {
		this.enemyImage = enemyImage;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public BufferedImage getImageitem() {
		return imageitem;
	}

	public void setImageitem(BufferedImage imageitem) {
		this.imageitem = imageitem;
	}

	public BufferedImage getShotImage() {
		return shot;
	}

}