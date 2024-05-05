package game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import game.entity.Player;

public class PlayState {

	private Player player;

	public PlayState() {

		this.player = new Player();
		
	}

	public void loop() {

		this.player.move();

	}

	public void render(Graphics graphics) {

		this.player.render(graphics);

	}

	public void keyPressed(int keyCode) {

		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			this.player.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			this.player.setRight(true);
			break;
		}

	}

	public void keyReleased(int keyCode) {

		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			this.player.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
			this.player.setRight(false);
			break;
		}

	}

}

