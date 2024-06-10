package game.ui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GameObject;
import game.states.Playing;

public class GameoverButton extends GameObject {
	
	private static BufferedImage button;
	private static BufferedImage buttonEntered;
	private static BufferedImage wings;
	private static BufferedImage replayPhrase;
	
	private static String gameoverButtonImgPath = "images/ui/gameover_button/";
	
	enum ButtonState {
		EXITED,
		ENTERED,
		CLICKED;
	}
	
	private ButtonState buttonState = ButtonState.EXITED;

	boolean buttonPressed = false;
	private Rectangle bounds;

	public GameoverButton() {

        try {
			button = ImageIO.read(new File(gameoverButtonImgPath + "button.png"));
			buttonEntered = ImageIO.read(new File(gameoverButtonImgPath + "button_entered.png"));
			wings = ImageIO.read(new File(gameoverButtonImgPath + "wings.png"));
			replayPhrase = ImageIO.read(new File(gameoverButtonImgPath + "replay_phrase.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        bounds = new Rectangle(55, 370, button.getWidth(), button.getHeight());
        
	}
	
	public void update() {
		
		if (mouse.isIn(bounds)) {
			buttonState = ButtonState.ENTERED;
			if (mouse.left.isPressed()) {
				buttonPressed = true;
			}
		} else {
			buttonState = ButtonState.EXITED;
		}
		
		if (!mouse.left.isPressed()) {
			if (buttonPressed) {
				if (mouse.isIn(bounds)) {
					Playing playing = game.getPlaying();
					
					playing.reset();
					playing.start();
					gsm.setState(playing);
					
					buttonPressed = false;
				} else {
					buttonPressed = false;
				}
			}
		}
		
	}

	public void render(Graphics2D g) {
		switch(buttonState) {
		case EXITED:
			g.drawImage(button, 55, 370, button.getWidth(), button.getHeight(), null);
			break;
		case ENTERED:
			g.drawImage(buttonEntered, 55, 370, button.getWidth(), button.getHeight(), null);
			break;
		default:
			break;
		}

		g.drawImage(wings, 70, 380, 70, 70, null);
		g.drawImage(replayPhrase, -15, 267, 500, 300, null);
	}
}