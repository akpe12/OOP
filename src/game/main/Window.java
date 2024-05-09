package game.main;

import javax.swing.JFrame;

public class Window extends JFrame {

	private GamePanel gp;

	private static final int WIDTH = 384;
	private static final int HEIGHT = 512;

	public Window() {
		setTitle("Dragon Flight");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIgnoreRepaint(true);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void addNotify() {
		super.addNotify();

		gp = new GamePanel(WIDTH, HEIGHT);
		setContentPane(gp);
	}

}