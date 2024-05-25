package game.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.GamePanel;

public class Background {

	private BufferedImage img;
	
	private int width = GamePanel.width;
	private int height = GamePanel.height;
	
	private int speed;

	private double y = 0;

	public Background() {
		try {
			img = ImageIO.read(new File("image/backgroundMap.png"));
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

}
