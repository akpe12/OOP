package game.entity;

import java.awt.Graphics2D;

import game.main.Resource;
import game.state.GameState;
import game.state.PlayState;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class Player extends Entity {

	private GameState state;
	private int speed;

	private double bulletPeriod = 0.08; // 80ms
	private double elapsed = 0;

	public Player(GameState state) {
		super((384 - 80) / 2, 512 - 100, 80, 80); // @YDH FIXME 상수 선언
		this.speed = 500;
		this.state = state;
	}

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

		double centerX = x + 40;
		if(key.left.pressed && centerX > 0) {
			left = true;
		} else {
			left = false;
		}
		if(key.right.pressed && centerX < 384) { // @YDH FIXME 상수 선언
			right = true;
		} else {
			right = false;
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(Resource.player, (int) x, (int) y, width, height, null);
	}

	public double getX() {
		return x;
	}

}
