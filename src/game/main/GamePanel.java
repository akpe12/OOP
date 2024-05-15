package game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.state.GameStateManager;
import game.state.PlayState;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class GamePanel extends JPanel implements Runnable {

	public static int width;
	public static int height;

	private Thread thread;
	private boolean running = false;
	
	private BufferedImage img;
	private Graphics2D g;

	private MouseHandler mouse;
	private KeyHandler key;

	private GameStateManager gsm;

	public GamePanel(int width, int height) {
		GamePanel.width = width;
		GamePanel.height = height;
		
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();

		if (thread == null) {
			thread = new Thread(this, "GameThread");
			thread.start();
		}
	}

	public void run() {
		
		init();
		
		final double NS_TO_S = 1 / 1e9;
		final double NS_TO_MS = 1 / 1e6;

		double nowNanoTime = System.nanoTime();
		double lastNanoTime;
		double dt;
		
		int nowSecondTime;
		int lastSecondTime = (int) (nowNanoTime * NS_TO_S);
		int runningTime = 0;
		
		int frameCount = 0;
		final double TARGET_FPS = 64.0;
		final double TARGET_DELAY = 1e9 / TARGET_FPS;
		
		while (running) {
			
			lastNanoTime = nowNanoTime;
			nowNanoTime = System.nanoTime();
			dt = (nowNanoTime - lastNanoTime) * NS_TO_S;
			
			update(dt);
			input(key, mouse);
			render();
			draw();
			frameCount++;
			
			nowSecondTime = (int) (nowNanoTime * NS_TO_S);
			if (nowSecondTime - lastSecondTime > 0) {
				int fps = frameCount;
				frameCount = 0;
				
				lastSecondTime = nowSecondTime;
				
				runningTime++;
				System.out.println(runningTime + "s " + fps);
			}
			
			long sleepTime = (long) ((TARGET_DELAY - (System.nanoTime() - nowNanoTime)) * NS_TO_MS);
			if (sleepTime > 0) {
	            try {
	                Thread.sleep(sleepTime);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
			
		}
		
	}

	public void init() {
		running = true;

		initGraphics();

		key = new KeyHandler(); 
		mouse = new MouseHandler(); 

		addKeyListener(key);
		addMouseListener(mouse);

		gsm = new GameStateManager(); 
	}

	public void initGraphics() {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void update(double dt) {
		gsm.update(dt);
	}

	public void input(KeyHandler key, MouseHandler mouse) {
		gsm.input(key, mouse);
	}

	public void render() {
		if (g != null) {
			g.setColor(new Color(0, 200, 0));
			g.fillRect(0, 0, width, height);
			gsm.render(g);
		}
	}

	public void draw() {
		Graphics g2 = (Graphics) this.getGraphics();
		g2.drawImage(img, 0, 0, width, height, null);
		g2.dispose();
	}
}
