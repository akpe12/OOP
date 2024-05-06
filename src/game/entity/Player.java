package game.entity;

import java.awt.Graphics;

import game.main.Resource;
import game.main.WindowManager;
import game.util.KeyHandler;
import game.util.MouseHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {
	
	private int speed;

	public Player() {
		super((WindowManager.WIDTH - 80) / 2, WindowManager.HEIGHT - 100, 80, 80); // FIXME
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
		
		left = key.left.pressed;
		right = key.right.pressed;

	}

	public void render(Graphics g) {
		g.drawImage(Resource.player, x, y, width, height, null);
	}

}
