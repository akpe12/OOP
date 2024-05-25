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
	
	public static int width;  // FIXME
	public static int height;  // FIXME

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

	@Override
	public void addNotify() {
		super.addNotify();

		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void run() {
		init();

		final double NS_TO_S = 1 / 1e9;
		final double NS_TO_MS = 1 / 1e6;

		long nsCurrent = System.nanoTime();
		long nsLast;
		double dt;
		
		int sCurrent;
		int sLast = (int) (nsCurrent * NS_TO_S);
		int sRunning = 0;
		
		int frameCount = 0;
		final double TARGET_FPS = 144.0;
		final long DRAW_INTERVAL = (long) (1e9 / TARGET_FPS);
		
		while (running) {
			//-------------------------Update time variables-------------------------//

			nsLast = nsCurrent;
			nsCurrent = System.nanoTime();
			dt = (nsCurrent - nsLast) * NS_TO_S;

			sCurrent = (int) (nsCurrent * NS_TO_S);
			if (sCurrent - sLast > 0) {
				int fps = frameCount;
				frameCount = 0;
				
				sLast = sCurrent;
				
				++sRunning;
				System.out.println(sRunning + "s " + fps + "fps");
			}
			
			++frameCount;
			
			//--------------------------Work on game screen--------------------------//
			
			input(key, mouse);
			
			update(dt);
			
			repaint();
			
			//--------------------Adjusting for the draw interval--------------------//
			
            long nsWait = DRAW_INTERVAL - (System.nanoTime() - nsCurrent);
            long msWait = (long) (nsWait * NS_TO_MS);
            
            if (msWait > 0) {
                try {
                    Thread.sleep(msWait - 1);  // -1 for more accurate fps
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
			
            while (System.nanoTime() - nsCurrent < DRAW_INTERVAL) {} // Busy-waiting
		}
	}
	
	public void init() {
		running = true;

		initGraphics();

		key = new KeyHandler(); 
		mouse = new MouseHandler(); 
		
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

	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		
		// Drawing onto an off-screen image for double buffering 
		g.setColor(new Color(200, 255, 200));
		g.fillRect(0, 0, width, height);
		gsm.render(g);

		// Displaying the off-screen image
		g2.drawImage(img, 0, 0, width, height, null);
		g2.dispose();
	}
	
}
