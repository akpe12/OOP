package game.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import game.main.GameObject;

public class PlayerHp extends GameObject {
	
	private BufferedImage[] hp;
	
	public PlayerHp() {
		hp = new BufferedImage[4];
		
		for (int i = 0; i < 4; i++) {
			hp[i] = loadImg("hp" + i);
		}
		
	}
	
	private BufferedImage loadImg(String path) {
		BufferedImage bi = null;
		
		try {
			bi = ImageIO.read(new File("images/ui/playing/hp/" + path + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bi;
	}
	
	public void render(Graphics2D g) {
		int playerHp = gsm.getPlaying().getPlayers().get(0).getHp();
		
		g.drawImage(hp[playerHp], 10, 10, hp[playerHp].getWidth(), hp[playerHp].getHeight(), null);
	}
}
