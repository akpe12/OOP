package game.main;

import game.audio.AudioPlayer;
import game.states.Gameover;
import game.states.GamestateManager;
import game.states.Intro;
import game.states.Playing;

public class Game implements Runnable {

	public static final int WIDTH = 384;
	public static final int HEIGHT = 512;
	
	private static final int FPS_SET = 144;
	private static final int UPS_SET = 200;
	
	private GamePanel gamePanel;
	
	private GamestateManager gsm;
	private AudioPlayer audioPlayer;

	private Intro intro;
	private Playing playing;
	private Gameover gameover;
	
	public Game() {
		init();
		new Thread(this).start();
	}
	
	private void init() {
		gsm = new GamestateManager();
		audioPlayer = new AudioPlayer();
		
		gamePanel = new GamePanel(this);
		new GameWindow(gamePanel);

		GameObject.set(this);

		intro = new Intro();
		playing = new Playing();
		gameover = new Gameover();

		gsm.initState(this);
	}

	public void run() {
		
		double timePerFrame = 1e9 / FPS_SET;
		double timePerUpdate = 1e9 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheckTime = System.currentTimeMillis();

		double dt = 0;
		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				dt = deltaU / UPS_SET;
				gamePanel.update(dt);
				updates++;
				deltaU = deltaU - (int)deltaU;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF = deltaF - (int)deltaF;
			}

			if (System.currentTimeMillis() - lastCheckTime >= 1000) {
				lastCheckTime = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public GamestateManager getGamestateManager() {
		return gsm;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
	
	public Intro getIntro() {
		return intro;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Gameover getGameover() {
		return gameover;
	}
	
}
