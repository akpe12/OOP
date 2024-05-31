package game.state;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.map.Background;
import game.util.KeyHandler;
import game.util.MouseHandler;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IntroState extends GameState{ // TODO extends GameState
	// TODO 대기실 구현
	private Background background;

	private static BufferedImage logoImg;
	private static BufferedImage illustImg;
	private static BufferedImage introPhraseImg;

	private static String logoImgPath = "image/logo.png";
	private static String illustImgPath = "image/illust_01.png";
	private static String introPhraseImgPath = "image/intro_phrase.png";

	private static int illustImgTopY = -65;
	private static int illustImgBottomY = -55;
	private static int illustImgMoveSpeed = 1;
	private int illustImgY = -65;

	private static double illustImgMovePeriod = 0.1; // 100ms
	private double elapsed = 0;

	private boolean TOP = true;
	private boolean BOTTOM = false;
	private boolean STARTPLAY = false;

	public IntroState(GameStateManager gsm) {
		super(gsm);

		background = new Background();
		loadImg();
	}

	public void loadImg() {
		try {
			logoImg = ImageIO.read(new File(logoImgPath));
			illustImg = ImageIO.read(new File(illustImgPath));
			introPhraseImg = ImageIO.read(new File(introPhraseImgPath));
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(double dt) {
		background.move(dt);

		elapsed += dt;
		if (elapsed >= illustImgMovePeriod) {
			illustImgMove();

			elapsed = 0;
		}

		if (STARTPLAY == true) {
			gsm.setState(new PlayState(gsm));
		}
	}

	public void input(KeyHandler key, MouseHandler mouse) {
		if (key.anyKey.pressed == true) {
			STARTPLAY = true;
		}
	}

	public void render(Graphics2D g) {
		background.render(g);

		g.drawImage(illustImg, -20, illustImgY, 400, 500, null);
		g.drawImage(logoImg, 40, -5, 300, 200, null);
		g.drawImage(introPhraseImg, 50, 350, 300, 220, null);
	}

	public void illustImgMove() {
		if (TOP == true && BOTTOM == false) {
			illustImgY += illustImgMoveSpeed;

			if (illustImgY == illustImgBottomY) {
				TOP = false;
				BOTTOM = true;
			}
		}
		else if (TOP == false && BOTTOM == true) {
			illustImgY -= illustImgMoveSpeed;

			if (illustImgY == illustImgTopY) {
				TOP = true;
				BOTTOM = false;
			}
		}
	}
}