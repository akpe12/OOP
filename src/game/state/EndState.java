package game.state;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.map.Background;

import game.util.KeyHandler;
import game.util.MouseHandler;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EndState extends GameState{ // TODO extends GameState
	// TODO: 게임 종료 화면 구현
	// @YCW: background should be little bit darker
	private Background background;

	private static BufferedImage sdImg;
	private static BufferedImage gameOverPhraseImg;
	private static BufferedImage btnImg;
	private static BufferedImage wingsImg;
	private static BufferedImage replayPhraseImg;

	private static String sdImgPath = "image/sd_01.png";
	private static String gameOverPhraseImgPath = "image/game_over.png";
	private static String btnImgPath = "image/ui_button.png";
	private static String wingsImgPath = "image/ui_wings.png";
	private static String replayPhraseImgPath = "image/replay_phrase.png";

	private boolean REPLAY = false;

	public EndState(GameStateManager gsm) {
		super(gsm);

		background = new Background();
		loadImg();
	}

	public void loadImg() {
		try {
			sdImg = ImageIO.read(new File(sdImgPath));
			gameOverPhraseImg = ImageIO.read(new File(gameOverPhraseImgPath));
			btnImg = ImageIO.read(new File(btnImgPath));
			wingsImg = ImageIO.read(new File(wingsImgPath));
			replayPhraseImg = ImageIO.read(new File(replayPhraseImgPath));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(double dt) {
		background.move(dt);

		if (REPLAY == true) {
			gsm.setState(new PlayState(gsm));
		}
	}

	public void input(KeyHandler key, MouseHandler mouse) {
		if (gsm.getState() instanceof EndState && mouse.left.pressed == true) {
			REPLAY = true;
		}
	}

	public void render(Graphics2D g) {
		background.renderDarker(g);

		g.drawImage(sdImg, 110, 10, 180, 200, null);
		g.drawImage(gameOverPhraseImg, -160, 20, 700, 500, null);
		g.drawImage(btnImg, 55, 370, 280, 100, null);
		g.drawImage(wingsImg, 70, 380, 70, 70, null);
		g.drawImage(replayPhraseImg, -15, 267, 500, 300, null);
	}
}