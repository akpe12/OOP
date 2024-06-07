package game.entity.map;

import static game.util.Constant.PanelConstant.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.entity.Entity;
import game.main.GamePanel;
import game.state.State;
import game.util.Constant.PanelConstant;

public class Background extends Entity {

	private BufferedImage img;
	
	private int speed;

	private double y = 0;

	public Background() {
		super(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		try {
			img = ImageIO.read(new File("image/backgroundMap.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.speed = 100;
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
