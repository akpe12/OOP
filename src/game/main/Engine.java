package game.main;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.state.PlayState;

public class Engine {
	
	private static PlayState playState; // FIXME: 여러 개의 state를 관리하는 gameStateManager 선언
	private static WindowManager windowManager;
	private static Timer timer; // FIXME: Timer 대신 ScheduledThreadExecutor 사용

	public static void init() {
		// gameStateManager = new GameStateManager();
		playState = new PlayState();
		windowManager = new WindowManager();
		timer = new Timer(20, new MainGameLoop());
	}

	public static void start() {
		// gameStateManager.stackState(new MainMenu(gameStateManager));
		windowManager.addPanel(new GameScreen());
		windowManager.addKeyListener(new Keyboard());
		windowManager.createWindow();
		timer.start();
	}

	private static class MainGameLoop implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// gameStateManager.loop();
			playState.loop();
		}

	}

	private static class GameScreen extends JPanel {

		// private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// gameStateManager.render(g);
			playState.render(g);
			repaint();
		}
		
	}

	private static class Keyboard implements KeyListener {

		@Override
		public void keyPressed(KeyEvent key) {
			// gameStateManager.keyPressed(key.getKeyCode());
			playState.keyPressed(key.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent key) {
			// gameStateManager.keyReleased(key.getKeyCode());
			playState.keyReleased(key.getKeyCode());
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

	}
}
