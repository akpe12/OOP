package game.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import game.main.Resource;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class Player extends Entity {
	
	private int speed;

	public Player() {
		super((384 - 80) / 2, 512 - 100, 80, 80); // FIXME 상수 선언
		this.speed = 10;
	}

	public void move() {
		
		if (left) {
			x -= this.speed;
		}
		if (right) {
			x += this.speed;
		}
		
	}

	public void input(KeyHandler key, MouseHandler mouse) {
		
		int centerX = x + 40;
		if(key.left.pressed && centerX > 0) {
			left = true;
		} else {
			left = false;
		}
		if(key.right.pressed && centerX < 384) { // FIXME 상수 선언
			right = true;
		} else {
			right = false;
		}

	}

	public void render(Graphics2D g) {
		g.drawImage(Resource.player, x, y, width, height, null);
	}

}
