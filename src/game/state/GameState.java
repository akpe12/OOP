package game.state;

import java.awt.Graphics2D;

import game.util.KeyHandler;
import game.util.MouseHandler;

public abstract class GameState {

	public abstract void update(); // FIXME dt를 인자로 넘겨주기
	public abstract void input(KeyHandler key, MouseHandler mouse);
	public abstract void render(Graphics2D g);

}
