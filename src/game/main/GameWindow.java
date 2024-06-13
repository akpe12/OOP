package game.main;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	public GameWindow(GamePanel gamePanel) {
		setContentPane(gamePanel);
		pack();

		setTitle("Dragon Flight");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setIgnoreRepaint(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

}