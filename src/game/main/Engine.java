package game.main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.state.GameStateManager;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class Engine {
	
	private static GameStateManager gsm;
	private static WindowManager wm;
		
	private static KeyHandler key;
	private static MouseHandler mouse;
	
	private static Timer timer; // FIXME: Timer 대신 ScheduledThreadExecutor 사용

	public static void init() {
		gsm = new GameStateManager();
		wm = new WindowManager();
		
		key = new KeyHandler();
		mouse = new MouseHandler();
		
		timer = new Timer(20, new MainGameUpdate());
	}

	public static void start() {
		// TODO: gameStateManager 시작
		wm.addPanel(new GameScreen());
		wm.addListener(key, mouse);
		wm.createWindow();
		timer.start();
	}

	private static class MainGameUpdate implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			gsm.update();
			gsm.input(key, mouse);
		}

	}

	private static class GameScreen extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			gsm.render(g);
			repaint();
		}
		
	}
}
