package game.state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.util.KeyHandler;
import game.util.MouseHandler;

public class GameStateManager {

	private GameState state;

	public GameStateManager() {
//		state = new PlayState(this);
		state = new IntroState(this);
	}

	public void update(double dt) {
		state.update(dt);
	}

	public void input(KeyHandler key, MouseHandler mouse) {
		state.input(key, mouse);
	}

	public void render(Graphics2D g) {
		state.render(g);
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public GameState getState() {
		return state;
	}
}
