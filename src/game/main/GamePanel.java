package game.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.audio.AudioPlayer;
import game.states.GamestateManager;
import game.utils.KeyHandler;
import game.utils.MouseHandler;

public class GamePanel extends JPanel {
	
	private BufferedImage img;
	private Graphics2D g;

	private KeyHandler key;
	private MouseHandler mouse;
	
	private GamestateManager gsm;

	public GamePanel(Game game) {
		img = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
		
		key = new KeyHandler(); 
		mouse = new MouseHandler(); 
		
		gsm = game.getGamestateManager();

		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void update(double dt) {
		gsm.update(dt);
	}
	
	@Override
	public void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		
		// Drawing onto an off-screen image (for double buffering)
		g.setColor(new Color(200, 255, 200));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		gsm.render(g);

		// Displaying the off-screen image
		g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
		g2.dispose();
	}

	public MouseHandler getMouse() {
		return mouse;
	}

	public KeyHandler getKey() {
		return key;
	}
	
}
