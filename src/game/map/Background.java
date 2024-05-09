package game.map;

import java.awt.Graphics2D;

import game.main.Resource;

public class Background {

	private int width = 384;
	private int height = 512;

	private int y = 0;

	public Background() {
		// TODO Auto-generated constructor stub
	}

	public void move() {
		y++;
		y = y % height;
	}

	public void render(Graphics2D g) {
		g.drawImage(Resource.backgroundMap, 0, y, width, height, null);
		g.drawImage(Resource.backgroundMap, 0, -height + y, width, height, null);
	}

}
