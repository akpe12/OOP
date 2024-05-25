package game.effect;

import java.awt.Graphics2D;

public abstract class Effect {
	public abstract void play(double dt);
	public abstract void render(Graphics2D g);
	
	public abstract boolean isFinished();
}
