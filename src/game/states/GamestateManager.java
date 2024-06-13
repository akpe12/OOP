package game.states;

import java.awt.Graphics2D;

import game.main.Game;

public class GamestateManager {
	
	private Gamestate state;
	
	private Intro intro;
	private Playing playing;
	private Gameover gameover;
	
	public GamestateManager() {}
	
	public void initState(Game game) {
		intro = game.getIntro();
		playing = game.getPlaying();
		gameover = game.getGameover();
		
		setState(Gamestate.INTRO);
	}
	
	public void update(double dt) {
		state.update(dt);
	}

	public void render(Graphics2D g) {
		state.render(g);
	}
	
	public Gamestate getState() {
		return state;
	}
	
	public void setState(int stateId, boolean resetRequired) {
		switch (stateId) {
		case Gamestate.INTRO:
			state = intro;
			break;
		case Gamestate.PLAYING:
			state = playing;
			break;
		case Gamestate.GAMEOVER:
			state = gameover;
			break;
		}
		
		if (resetRequired) {
			state.reset();
		}
	}
	
	public void setState(int stateId) {
		setState(stateId, true);
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