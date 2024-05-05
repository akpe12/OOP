package game.main;

import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowManager {
	
	private JFrame frame;
	private JPanel panel;
	
	public static final int WIDTH = 384;
	public static final int HEIGHT = 552;
	
	public WindowManager() {
		this.frame = new JFrame("Dragon Flight");
		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
	}
	
	public void addPanel(JPanel panel) {
		this.panel = panel;
		this.panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.panel.setFocusable(true);
		this.panel.requestFocusInWindow();
	}
	
	public void addKeyListener(KeyListener listener) {
		try {
			this.panel.addKeyListener(listener);
		} catch(NullPointerException e) {
			System.err.println("[WindowManager]: Error! Tried to add KeyListener before JPanel");
			System.exit(-1);
		}
	}
	
	public void createWindow() {
		this.frame.setContentPane(panel);
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
}