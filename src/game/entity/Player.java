package game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.main.Resource;
import game.main.WindowManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Rectangle { // FIXME: Entity 추상 클래스를 상속받도록 변경

	private boolean left;
	private boolean right;

	private int speed;

	public Player() {
		super((WindowManager.WIDTH) / 2 - 40, 450, 80, 80); // FIXME: 상수 이용(80 <- SIZE 등)
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

	public void render(Graphics graphics) {
		graphics.drawImage(Resource.player, x, y, width, height, null);
	}

}
