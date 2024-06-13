package game.entities.map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.entities.Entity;
import game.main.Game;
import game.states.Gameover;

public class Background extends Entity {

	private BufferedImage img;
	
    private static float startSpeed = 100.0f;

	public Background() {
		super(0, 0, Game.WIDTH, Game.HEIGHT);
		
		try {
			img = ImageIO.read(new File("images/entities/background_map.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.speed = startSpeed;
	}

	public void move(double dt) {
		y += speed * dt;
		if (y >= height) {
			y = 0;
		}
	}

	@Override
	public void update(double dt) {
		move(dt);
	}
	
	@Override
	public void render(Graphics2D g) {
		g.drawImage(img, 0, (int)y, (int)width, (int)height, null);
		g.drawImage(img, 0, -(int)height + (int)y, (int)width, (int)height, null);
		
		if (gsm.getState() instanceof Gameover) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Adjust alpha for darkness level
	        g.setColor(Color.BLACK);
	        g.fillRect(0, 0, (int)width, (int)height);
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset alpha
		}
	}

	public static float getStartSpeed() {
		return startSpeed;
	}

	public static void setStartSpeed(float startSpeed) {
		Background.startSpeed = startSpeed;
	}

}
