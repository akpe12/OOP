package game.map;

import java.awt.Graphics2D;

import game.main.Resource;
import game.main.Window;

import java.awt.Color;
import java.awt.AlphaComposite;

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

	// @YCW: added below function instead of inspecting current GameState
	public void renderDarker(Graphics2D g) {
		g.drawImage(Resource.backgroundMap, 0, (int)y, width, height, null);
		g.drawImage(Resource.backgroundMap, 0, -height + (int)y, width, height, null);

		// @YCW: added below lines for making background darker in EndState
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Adjust alpha for darkness level
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset alpha
	}
}
