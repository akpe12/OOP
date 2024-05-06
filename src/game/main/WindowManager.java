package game.main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.util.KeyHandler;
import game.util.MouseHandler;

public class WindowManager {
	
	private JFrame frame;
	private JPanel panel;
	
	public static final int WIDTH = 384;
	public static final int HEIGHT = 552;
	
	public WindowManager() {
		frame = new JFrame("Dragon Flight");
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
	
	public void addPanel(JPanel panel) {
		this.panel = panel;
		this.panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.panel.setFocusable(true);
		this.panel.requestFocusInWindow();
	}
	
	public void addListener(KeyHandler key, MouseHandler mouse) {
		panel.addKeyListener(key);
		panel.addMouseListener(mouse);
	}
	
	public void createWindow() {
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
}