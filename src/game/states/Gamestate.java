package game.states;

import java.awt.Graphics2D;

import game.main.GameObject;

public abstract class Gamestate extends GameObject {
	
	public static final int INTRO = 0;
	public static final int PLAYING = 1;
	public static final int GAMEOVER = 2;
	
	protected abstract void reset();
	
	protected abstract void update(double dt);
	protected abstract void render(Graphics2D g);
	
}
