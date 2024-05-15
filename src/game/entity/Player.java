package game.entity;

import java.awt.Graphics2D;

import game.main.Resource;
import game.state.GameState;
import game.state.GameStateManager;
import game.state.PlayState;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class Player extends Entity {

	private int speed;
	private GameState state;

	private double elapsed = 0;
	private double bulletPeriod = 0.08; // 80ms

	public Player(GameState state) {
		super((384 - 80) / 2, 512 - 100, 80, 80); // FIXME @YDH : 상수 선언
		this.speed = 500;
		this.state = state;
	}

	// update
	public void move(double dt) {

		if (left) {
			x -= this.speed * dt;
		}
		if (right) {
			x += this.speed * dt;
		}

	}

	public void fire(double dt) {
		elapsed += dt;
		if (elapsed > bulletPeriod) {
			Bullet bullet = new Bullet((int) x);
			((PlayState) state).getBullets().add(bullet);

			elapsed = 0;
		}

	}

	public void input(KeyHandler key, MouseHandler mouse) {

		left = key.left.pressed;
		right = key.right.pressed;

		double centerX = x + 40;

		if (centerX <= 5) {
			x = 5 - 40;
		}
		if (centerX >= 384 - 5) { // FIXME @YDH : 상수 선언
			x = 384 - 5 - 40;
		}

	}

	public void render(Graphics2D g) {
		g.drawImage(Resource.player, (int) x, (int) y, width, height, null);
	}

}
