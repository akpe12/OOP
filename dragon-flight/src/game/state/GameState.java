package game.state;

import java.awt.Graphics2D;

import game.util.KeyHandler;
import game.util.MouseHandler;

public abstract class GameState {

	public abstract void update(double dt);
	public abstract void input(KeyHandler key, MouseHandler mouse);
	public abstract void render(Graphics2D g);

}
