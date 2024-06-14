package game.states;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.audio.AudioPlayer;
import game.entities.map.Background;
import game.ui.GameoverButton;

public class Gameover extends Gamestate { 
	
	private Background background;
	private GameoverButton button;
	
	private static BufferedImage sdImg;
	private static BufferedImage gameOverPhraseImg;
	
	private static String sdImgPath = "images/ui/gameover/sd_01.png";
	private static String gameOverPhraseImgPath = "images/ui/gameover/gameover.png";
	
    static {
		loadImg();
    }
	
    public static void loadImg() {
        try {
            sdImg = ImageIO.read(new File(sdImgPath));
            gameOverPhraseImg = ImageIO.read(new File(gameOverPhraseImgPath));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public Gameover() {
	}

	@Override
	public void reset() {
		background = new Background();
		button = new GameoverButton();

		ap.playSong(AudioPlayer.MENU);
	}
	
	@Override
	public void update(double dt) {
		background.move(dt);
		button.update();
	}
	
	@Override
	public void render(Graphics2D g) {
		background.render(g);
		
		g.drawImage(sdImg, 110, 10, 180, 200, null);
		g.drawImage(gameOverPhraseImg, -160, 20, 700, 500, null);
		button.render(g);
	}
	
}
