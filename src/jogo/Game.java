/* File: Game.java
 * 
 * Package: src/jogo
 * 
 * Description: This is the Game class responsible for update and render the entities of the 
 * game
 */

package jogo;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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

	private int introduction = 0;

	private Player player;
	private Plataform floor;
	private Plataform platform;
	private Plataform platform2; 
	private Plataform platform3;
	private Plataform platform4;
	private Plataform platform5;
	private Plataform platform6;
	private Plataform platform7;
	private Plataform platform8;
	private Plataform platform9;
	private Plataform platform10;
	
	private Enemy enemy1;
	private Enemy enemy2;
	private Enemy enemy3;
	private Enemy enemy4;
	private Enemy enemy5;
	private Enemy enemy6;
	private Enemy enemy[] = { enemy1, enemy2, enemy3, enemy4, enemy5, enemy6 };
	private Item item1;
	private Item item2;
	private Item item3;
	private Item[] item = { item1, item2, item3 };
	private boolean rendersitem[] = { true, true, true };
	Audio audio = new Audio();
	private int currentFase = 0;
	private int lastFase = 0;
	BufferedImageLoader loader = new BufferedImageLoader();
	ArrayList<BufferedImage> gameImages;

	// Loads the images from the game and Initializes the first level.
	public void init() {
		if (currentFase < 2) {
			gameImages = new ArrayList<BufferedImage>();
			
			try {
				String[] paths = {"bandido", "Atirador1", "cenario", "player", "item", "menu", "tiro"
						, "Intro 1", "Intro 2", "Intro 3", "placaEsquerda", "placaDireita", "raquel", "fim"};
				gameImages = loadImages(paths);
			} catch (IOException e) {
				e.printStackTrace();
			}
						
			setEnemiesLevel1();
			setItemsLevel1();
			resetLevel();
			
		} else {
			// Nothing to do.
		}
		
		Plataform[] platforms = initializePlatforms();
		// Initialize the player and sets the platforms.
		player = new Player(500, 300, 68, 35, this);
		player.setPlat(platforms);
		InitializeEvents();
	}
	
	// Update all levels and entities of the game.
	public void tick() {
		if ((player.getX() < 30 && player.getY() < 26 && player.isPlataform() && currentFase == 1)
				|| player.isDead()) {
			if (!player.isDead()) {
				currentFase = 2;
			} else {
				// Nothing to do.
			}
			setPlayerLevel2();
			resetLevel();
			setEnemiesLevel2();
			setPlatformsLevel2();
			setItemsLevel2();
			updateLevelShot();	
		} else {
			// Nothing to do.
		}
		
		if ((player.getX() < 50 && player.getY() < 50 && player.isPlataform() && currentFase == 2)
				|| player.isDead()) {
			if (!player.isDead()) {
				currentFase = 3;
			} else {
				// Nothing to do.
			}
			resetLevel();
			setPlayerLevel3();
			setEnemiesLevel3();
			setPlatformsLevel3();
			setItemsLevel3();
			updateLevelShot();
		} else {
			// Nothing to do.
		}
		
		if ((player.getX() > 588 && player.getY() < 71 && player.isPlataform() && currentFase == 3)
				|| player.isDead()) {
			if (!player.isDead()) {
				currentFase = 4;
			} else {
				// Nothing to do.
			}
			setPlayerLevel4();
			resetLevel();
			setEnemiesLevel4();
			setPlatformsLevel4();
			setItemsLevel4();
			updateLevelShot();
		} else {
			// Nothing to do.
		}
		
		if ((player.getX() < 40 && player.getY() < 94 && player.isPlataform() && currentFase == 4)) {
			introduction = 4;
		} else {
			// Nothing to do.
		}
		if (currentFase != lastFase) {
			if (currentFase == 2) {
				lastFase = 2;
				try {
					BufferedImage image = loader.loadImage("/jogo/Fase 2.png");
					gameImages.set(2, image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// Nothing to do.
			}
			if (currentFase == 3) {
				lastFase = 3;
				try {
					BufferedImage image = loader.loadImage("/jogo/Fase 3.png");
					gameImages.set(2, image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// Nothing to do.
			}
			if (currentFase == 4) {
				lastFase = 4;
				try {
					BufferedImage image = loader.loadImage("/jogo/Fase 4.png");
					gameImages.set(2, image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// Nothing to do.
			}
		} else {
			// Nothing to do.
		}
		if ((Mouse.getX() > 138 && Mouse.getX() < 246)
				&& (Mouse.getY() > 308 && Mouse.getY() < 332)) {
			introduction = 1;
			Mouse.setMouseY(500);
		} else {
			// Nothing to do.
		}
		if (introduction == 2) {
			currentFase = 1;
			introduction = 3;
		} else {
			// Nothing to do.
		}
		player.tick();
		for (int j = 0; j < enemy.length; j++) {
			if (!enemy[j].dead) {
				enemy[j].tick();
			} else {
				// Nothing to do.
			}
		}
		if (collision(player, enemy)) {
			if (currentFase == 1) {
				init();
			} else {
				player.setDead(true);
			}
		} else {
			// Nothing to do.
		}
		if (collisionShot(player, enemy) >= 0) {
			enemy[collisionShot(player, enemy)].dead = true;
			if (player.getDirection() == 0) {
				player.setXshot(1000);
			} else if (player.getDirection() == 1) {
				player.setXshot(-50);
			} else {
				// Nothing to do.
			}
		} else {
			// Nothing to do.
		}
		if (collisionShotPlayer(player, enemy)) {
			for (int i = 0; i < enemy.length; i++) {
				if (enemy[i].getDirection() == 0) {
					enemy[i].setXshot(1000);
				} else if (enemy[i].getDirection() == 1) {
					enemy[i].setXshot(-50);
				} else {
					// Nothing to do.
				}
			}
			player.setDead(true);
		} else {
			// Nothing to do.
		}
		collisionItem(player, item);
		System.out.println("x:  " + Mouse.getX() + " y: " + Mouse.getY());
	}

	int o = 0;

	// Draw all the entities in the screen.
	public void render() {

		// Starts a triple buffer.
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		} else {
			// Nothing to do.
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(gameImages.get(2), 0, 0, getWidth(), getHeight(), this);

		// Render the items.
		for (int j = 0; j < item.length; j++) {
			if (rendersitem[j]) {
				item[j].render(g);
			} else {
				// Nothing to do.
			}
		}
		g.fillRect(Mouse.getX() - 2, Mouse.getY() - 2, 4, 4);

		// Render the player.
		player.render(g);

		// Render the enemies.
		for (int j = 0; j < enemy.length; j++) {
			if (!enemy[j].dead) {
				enemy[j].render(g);
			} else {
				// Nothing to do.
			}
		}
		if (currentFase == 0) {
			g.drawImage(gameImages.get(5), 0, 0, getWidth(), getHeight(), this);
		} else {
			// Nothing to do.
		}

		// PLays the introduction in the beginning of the game.
		if (introduction == 1) {
			g.drawImage(gameImages.get(7), 0, 0, getWidth(), getHeight(), this);
			o++;
			if (o >= 30) {
				g.drawImage(gameImages.get(8), 0, 0, getWidth(), getHeight(), this);
			} else {
				// Nothing to do.
			}
			if (o >= 60) {
				g.drawImage(gameImages.get(9), 0, 0, getWidth(), getHeight(), this);
			} else {
				// Nothing to do
			}
			if (o >= 90) {
				introduction = 2;
			} else {
				// Nothing to do
			}
		} else {
			// Nothing to do
		}

		// Draw the arrow, that symbolizes the end of a level.
		if (currentFase == 1) {
			g.drawImage(gameImages.get(10), 0, 25, 43, 46, this);
		} else {
			// Nothing to do.
		}
		if (currentFase == 2) {
			g.drawImage(gameImages.get(10), 0, 47, 43, 46, this);
		} else {
			// Nothing to do.
		}
		if (currentFase == 3) {
			g.drawImage(gameImages.get(11), 588, 25, 43, 46, this);
		} else {
			// Nothing to do.
		}
		if (currentFase == 4) {
			g.drawImage(gameImages.get(12), 0, 18, 38, 68, this);
		} else {
			// Nothing to do.
		}
		if (introduction == 4) {
			g.drawImage(gameImages.get(13), 0, 0, getWidth(), getHeight(), this);
		} else {
			// Nothing to do.
		}
		g.dispose();
		bs.show();
	}

	// Applies the gravity in the player.
	public void applyGravity(Player player) {
		float time = 0.18f;
		speed += gravity * time;
		player.setY(player.getY() + (speed * time));
		speed = (speed > 300.0f) ? 300.0f : speed;
	}
	
	// Plays the sound of the game.
	public void backgroundSound() {
		audio.setFileName("C:/Users/Simiao/Desktop/09 Dryad Of The Woods.wav");
		audio.playSong();
	}

	// Loads all the images of the game.
	public ArrayList<BufferedImage> loadImages(String[] paths) throws IOException{
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		
		for(int i=0; i<paths.length; i++){
			images.add(loader.loadImage("/jogo/" + paths[i] + ".png"));
		}
		
		return images;
	}


	// Check if the player is colliding with a enemy.
	public boolean collision(Player a, Enemy[] b) {

		for (int i = 0; i < b.length; i++) {
			if (b[i].getEnemyType() == 1) {
				if (!b[i].dead && a.getBounds().intersects(b[i].getBounds())) {
					System.out.println("COLIDIU");
					enemy[0].deathSound();
					return true;
				} else {
					// Nothing to do
				}
			} else {
				// Nothing to do
			}
		}
		return false;
	}

	// Check if the player is colliding with a item.
	public boolean collisionItem(Player a, Item[] b) {
		for (int i = 0; i < b.length; i++) {
			if (a.getBounds().intersects(b[i].getBounds())) {
				rendersitem[i] = false;
				return true;
			} else {
				// Nothing to do
			}
		}
		return false;
	}

	// Check if the player's shoot is colliding with a enemy.
	public int collisionShot(Player a, Enemy[] b) {
		for (int i = 0; i < b.length; i++) {
			if (a.getshotBounds().intersects(b[i].getBounds())) {
				System.out.println("MORREU");
				return i;
			} else {
				// Nothing to do
			}
		}
		return -1;
	}

	// Check if the enemy's shoot is colliding with the player.
	public boolean collisionShotPlayer(Player a, Enemy[] b) {
		for (int i = 0; i < b.length; i++) {
			if (a.getBounds().intersects(b[i].getshotBounds())) {
				enemy[0].deathSound();
				return true;
			} else {
				// Nothing to do
			}
		}
		return false;
	}

	// Handle the keyboard events.
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			player.setRight(true);
			if (player.isDoShot()) {
				player.setDirection(0);
			} else {
				// Nothing to do
			}
		} else if (key == KeyEvent.VK_LEFT) {
			player.setLeft(true);
			if (player.isDoShot()) {
				player.setDirection(1);
			} else {
				// Nothing to do
			}
		} else {
			// Nothing to do
		}
		if (key == KeyEvent.VK_SPACE) {
			player.setJump(true);
		} else {
			// Nothing to do
		}
		if (key == KeyEvent.VK_UP) {
		} else {
			// Nothing to do
		}
		if (key == KeyEvent.VK_DOWN) {
			if (player.isDoShot()) {
				player.setXshot(player.getX());
				player.setYshot(player.getY());
				player.setShoot(true);
				player.shotSound();
			} else {
				// Nothing to do
			}
		} else {
			// Nothing to do
		}
	}

	// Handle the keyboard events.
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
		} else {
			// Nothing to do
		}
		if (key == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		} else if (key == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		} else {
			// Nothing to do
		}
	}
	
	// Start the thread that runs the game.
		private synchronized void start() {
			if (running) {
				return;
			} else {
				// Nothing to do.
			}
			running = true;
			thread = new Thread(this);
			thread.start();
		}

		// Stop the thread that runs the game.
		private synchronized void stop() {
			if (!running) {
				return;
			} else {
				// Nothing to do.
			}
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.exit(1);
		}

	public static void main(String[] args) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		// Initializes the window of the game
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Starts the game and plays it sound.
		game.start();
		game.backgroundSound();
	}
	
	// Main Game loop
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
			// The game is updated here.
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta > 1) {
				tick();
				render();
				updates++;
				delta--;
			} else {
				// Nothing to do.
			}
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				updates = 0;
				frames = 0;
			} else {
				// Nothing to do.
			}
		}
		stop();
	}

	
	public void resetLevel(){
		for (int r = 0; r < enemy.length; r++)
			enemy[r].setDead(false);
		for (int k = 0; k < 3; k++)
			rendersitem[k] = true;
	}

	public void updateLevelShot(){
		for (int o = 0; o < enemy.length; o++) {
			enemy[o].setXshot(enemy[o].getX());
			enemy[o].setYshot(enemy[o].getY());
			if (enemy[o].getEnemyType() == 2) {
				enemy[o].setShoot(true);
			} else {
				// Nothing to do.
			}
			if (player.isDead()) {
				player.setDoShot(true);
			} else {
				// Nothing to do.
			}
			player.setDead(false);
		}
	}
	
	public void setPlayerLevel2(){
		player.setY(300);
		player.setX(50);
		player.setDoShot(true);
	} 
	
	public void setPlayerLevel3(){
		player.setY(300);
		player.setX(500);
		player.setDoShot(true);
	} 
	
	public void setPlayerLevel4(){
		player.setY(300);
		player.setX(500);
		player.setDoShot(true);
	}
	
	public void setEnemiesLevel1(){
		enemy[0] = new Enemy(0, 256, 60, 35, this, 1);
		enemy[0].setLeftLimit(480);
		enemy[1] = new Enemy(0, 116, 60, 35, this, 1);
		enemy[1].setLeftLimit(470);
		enemy[2] = new Enemy(269, 150, 60, 35, this, 1);
		enemy[2].setLeftLimit(250);
		enemy[2].setRightLimit(269);
		enemy[3] = new Enemy(-100, 116, 60, 35, this, 2);
		enemy[3].setLeftLimit(470);
		enemy[4] = new Enemy(-100, 256, 60, 35, this, 2);
		enemy[4].setLeftLimit(480);
		enemy[5] = new Enemy(-100, 116, 60, 35, this, 2);
		enemy[5].setLeftLimit(470);
	}
	
	public void setEnemiesLevel2(){
		enemy[0].setX(181);
		enemy[0].setY(329);
		enemy[0].setEnemyType(2);
		enemy[0].setImage();
		enemy[1].setX(181);
		enemy[1].setY(263);
		enemy[1].setEnemyType(2);
		enemy[1].setImage();
		enemy[2].setX(181);
		enemy[2].setY(195);
		enemy[2].setEnemyType(2);
		enemy[2].setImage();
		enemy[3].setX(181);
		enemy[3].setY(128);
		enemy[3].setEnemyType(2);
		enemy[3].setImage();
		enemy[4].setX(520);
		enemy[4].setY(258);
		enemy[4].setEnemyType(1);
		enemy[4].setImage();
		enemy[4].setLeftLimit(25);
		enemy[4].setRightLimit(520);
		enemy[5].setX(-200);
		enemy[5].setEnemyType(2);
	}
	
	public void setEnemiesLevel3(){
		enemy[0].setX(60);
		enemy[0].setY(151);
		enemy[0].setEnemyType(2);
		enemy[0].setImage();
		enemy[1].setX(60);
		enemy[1].setY(259);
		enemy[1].setEnemyType(2);
		enemy[1].setImage();
		enemy[2].setX(174);
		enemy[2].setY(165);
		enemy[2].setEnemyType(1);
		enemy[2].setImage();
		enemy[2].setLeftLimit(320);
		enemy[2].setRightLimit(174);
		enemy[3].setX(-180);
		enemy[3].setY(128);
		enemy[3].setEnemyType(2);
		enemy[3].setImage();
		enemy[4].setX(174);
		enemy[4].setY(84);
		enemy[4].setEnemyType(1);
		enemy[4].setImage();
		enemy[4].setLeftLimit(320);
		enemy[4].setRightLimit(174);
		enemy[5].setX(-300);
		enemy[5].setEnemyType(1);
		enemy[5].setLeftLimit(1320);
		enemy[5].setRightLimit(-174);
	}
	
	public void setEnemiesLevel4(){
		enemy[0].setX(11);
		enemy[0].setY(222);
		enemy[0].setEnemyType(2);
		enemy[0].setImage();
		enemy[1].setX(11);
		enemy[1].setY(135);
		enemy[1].setEnemyType(2);
		enemy[1].setImage();
		enemy[2].setX(580);
		enemy[2].setY(100);
		enemy[2].setEnemyType(2);
		enemy[2].setImage();
		enemy[3].setX(570);
		enemy[3].setY(219);
		enemy[3].setEnemyType(2);
		enemy[3].setImage();
		enemy[4].setX(580);
		enemy[4].setY(172);
		enemy[4].setEnemyType(2);
		enemy[4].setImage();
		enemy[2].setDirection(1);
		enemy[3].setDirection(1);
		enemy[4].setDirection(1);
		enemy[5].setX(-580);
		enemy[5].setEnemyType(2);
	}
	
	public void setItemsLevel1(){
		item[0] = new Item(-530, 114, 30, 30, this);
		item[1] = new Item(-530, 167, 30, 30, this);
		item[2] = new Item(-530, 500, 30, 30, this);
	}
	
	public void setItemsLevel2(){
		item[0] = new Item(530, 114, 30, 30, this);
		item[1] = new Item(-530, 167, 30, 30, this);
		item[2] = new Item(-530, 289, 30, 30, this);
	}
	
	public void setItemsLevel3(){
		item[0] = new Item(256, 114, 30, 30, this);
		item[1] = new Item(256, 197, 30, 30, this);
		item[2] = new Item(405, 182, 30, 30, this);
	}
	
	public void setItemsLevel4(){
		item[0] = new Item(220, 112, 30, 30, this);
		item[1] = new Item(220, 200, 30, 30, this);
		item[2] = new Item(405, 98, 30, 30, this);
	}
	
	public Plataform[] initializePlatforms(){
		floor = new Plataform(0, 412, WIDTH * SCALE);
		platform = new Plataform(300, 285, 100);
		platform2 = new Plataform(250, 375, 100);
		platform3 = new Plataform(0, 316, 195);
		platform4 = new Plataform(282, 143, 129);
		platform5 = new Plataform(265, 217, 162);
		platform6 = new Plataform(491, 252, 158);
		platform7 = new Plataform(0, 183, 210);
		platform8 = new Plataform(0, 72, 411);
		platform9 = new Plataform(457, 110, 160);
		platform10 = new Plataform(0, 0, 0);
		Plataform[] plats = { platform, platform2, platform3, platform4,
				platform5, platform6, platform7, platform8, platform9, platform10,
				floor };
		return plats;
	}
	
	public void setPlatformsLevel2(){
		platform.setX(487);
		platform.setY(369);
		platform.setWidth(54);
		platform2.setX(523);
		platform2.setY(323);
		platform2.setWidth(126);
		platform3.setX(456);
		platform3.setY(293);
		platform3.setWidth(38);
		platform4.setX(523);
		platform4.setY(259);
		platform4.setWidth(126);
		platform5.setX(430);
		platform5.setY(234);
		platform5.setWidth(58);
		platform6.setX(523);
		platform6.setY(201);
		platform6.setWidth(126);
		platform7.setX(523);
		platform7.setY(147);
		platform7.setWidth(126);
		platform8.setX(587);
		platform8.setY(171);
		platform8.setWidth(62);
		platform9.setX(587);
		platform9.setY(112);
		platform9.setWidth(62);
		platform10.setX(0);
		platform10.setY(91);
		platform10.setWidth(573);
	}
	
	public void setPlatformsLevel3(){
		platform.setX(50);
		platform.setY(360);
		platform.setWidth(70);
		platform2.setX(170);
		platform2.setY(318);
		platform2.setWidth(200);
		platform3.setX(380);
		platform3.setY(285);
		platform3.setWidth(95);
		platform4.setX(175);
		platform4.setY(232);
		platform4.setWidth(160);
		platform5.setX(45);
		platform5.setY(178);
		platform5.setWidth(100);
		platform6.setX(175);
		platform6.setY(150);
		platform6.setWidth(160);
		platform7.setX(350);
		platform7.setY(110);
		platform7.setWidth(100);
		platform8.setX(470);
		platform8.setY(70);
		platform8.setWidth(180);
		platform9.setX(0);
		platform9.setY(0);
		platform9.setWidth(0);
		platform10.setX(0);
		platform10.setY(0);
		platform10.setWidth(0);
	}
	
	public void setPlatformsLevel4(){
		platform.setX(210);
		platform.setY(344);
		platform.setWidth(80);
		platform2.setX(315);
		platform2.setY(295);
		platform2.setWidth(335);
		platform3.setX(0);
		platform3.setY(262);
		platform3.setWidth(260);
		platform4.setX(170);
		platform4.setY(233);
		platform4.setWidth(80);
		platform5.setX(0);
		platform5.setY(174);
		platform5.setWidth(260);
		platform6.setX(170);
		platform6.setY(145);
		platform6.setWidth(80);
		platform7.setX(0);
		platform7.setY(85);
		platform7.setWidth(260);
		platform8.setX(300);
		platform8.setY(125);
		platform8.setWidth(350);
		platform9.setX(0);
		platform9.setY(0);
		platform9.setWidth(0);
		platform10.setX(0);
		platform10.setY(0);
		platform10.setWidth(0);
	}
	
	// Start to listen input from user.
	public void InitializeEvents(){
		addKeyListener(new Keyboard(this));
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public BufferedImage getSpriteSheet() {
		return gameImages.get(3);
	}

	public BufferedImage getEnemyImage() {
		return gameImages.get(0);
	}

	public BufferedImage getEnemyImage2() {
		return gameImages.get(1);
	}

	public void setEnemyImage(BufferedImage enemyImage) {
		gameImages.set(1, enemyImage);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public BufferedImage getImageitem() {
		return gameImages.get(4);
	}

	public void setImageitem(BufferedImage imageitem) {
		gameImages.set(4, imageitem);
	}

	public BufferedImage getShotImage() {
		return gameImages.get(6);
	}

}