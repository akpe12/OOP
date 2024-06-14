package game.states;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.audio.AudioPlayer;
import game.entities.map.Background;

public class Intro extends Gamestate {
	
	private Background background;
	
	private static BufferedImage logoImg;
	private static BufferedImage illustImg;
	private static BufferedImage introPhraseImg;

	private static String logoImgPath = "images/ui/intro/logo.png";
	private static String illustImgPath = "images/ui/intro/illust_01.png";
	private static String introPhraseImgPath = "images/ui/intro/phrase.png";
	
	static {
		loadImg();
	}
	
    private static void loadImg() {
        try {
            logoImg = ImageIO.read(new File(logoImgPath));
            illustImg = ImageIO.read(new File(illustImgPath));
            introPhraseImg = ImageIO.read(new File(introPhraseImgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private float illustImgTopY = -65;
	private float illustImgBottomY = -55;
	private float illustImgMoveSpeed = 15;
	private float illustImgY = -65;
	
	private boolean TOP = true;
	private boolean BOTTOM = false;
	private boolean STARTPLAY = false;
	
	public Intro() {}

	@Override
	public void reset() {
		background = new Background();
		
		ap.playSong(AudioPlayer.MENU);
	}
	
    @Override
	public void update(double dt) {
		if (key.anyKey.isPressed()) {
			STARTPLAY = true;
		}
		
		background.move(dt);
		
		illustImgMove(dt);
			
		if (STARTPLAY == true) {
			Playing playing = gsm.getPlaying();
			
			playing.reset();
			gsm.setState(Gamestate.PLAYING);
		}
	}
	
    @Override
	public void render(Graphics2D g) {
		background.render(g);
		
		g.drawImage(illustImg, 12, (int)illustImgY, illustImg.getWidth(), illustImg.getHeight(), null);
		g.drawImage(logoImg, 40, -5, 300, 200, null);
		g.drawImage(introPhraseImg, 50, 350, 300, 220, null);
	}
	
	public void illustImgMove(double dt) {
		if (TOP == true && BOTTOM == false) {
			illustImgY += illustImgMoveSpeed * dt;
			
			if (illustImgY >= illustImgBottomY) {
				TOP = false;
				BOTTOM = true;
			}
		}
		else if (TOP == false && BOTTOM == true) {
			illustImgY -= illustImgMoveSpeed * dt;
			
			if (illustImgY <= illustImgTopY) {
				TOP = true;
				BOTTOM = false;
			}	
		}
	}
}
