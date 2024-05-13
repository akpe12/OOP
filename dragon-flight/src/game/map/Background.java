package game.map;

import java.awt.Graphics2D;

import game.main.Resource;
import game.main.Window;

public class Background {

	private int width = Window.WIDTH;
	private int height = Window.HEIGHT;
	
	private int speed;

	private double y = 0;

	public Background() {
		this.speed = 100;
	}

	public void move(double dt) {
		y += speed * dt;
		y = y % height;
	}

	public void render(Graphics2D g) {
		g.drawImage(Resource.backgroundMap, 0, (int)y, width, height, null);
		g.drawImage(Resource.backgroundMap, 0, -height + (int)y, width, height, null);
	}

}
