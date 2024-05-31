package game.map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.GamePanel;
import game.main.Resource;

public class Background {

	private BufferedImage img;

	private int width = GamePanel.width;
	private int height = GamePanel.height;

	private int speed;

	private double y = 0;

	public Background() {
		try {
			img = ImageIO.read(new File("image/Map_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.speed = 100;
	}

	public void move(double dt) {
		y += speed * dt;
		y = y % height;
	}

	public void render(Graphics2D g) {
		g.drawImage(img, 0, (int)y, width, height, null);
		g.drawImage(img, 0, -height + (int)y, width, height, null);
	}
	// @YCW: added below function instead of inspecting current GameState
	public void renderDarker(Graphics2D g) {
		g.drawImage(img, 0, (int)y, width, height, null);
		g.drawImage(img, 0, -height + (int)y, width, height, null);

		// @YCW: added below lines for making background darker in EndState
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Adjust alpha for darkness level
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset alpha
	}

}