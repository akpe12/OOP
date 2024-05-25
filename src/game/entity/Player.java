package game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.state.GameState;
import game.state.PlayState;
import game.util.KeyHandler;
import game.util.MouseHandler;

import javax.swing.*;

import static java.lang.Math.abs;

public class Player extends Entity {

	public static ImageIcon player = new ImageIcon("image/player.png");
	public static ImageIcon player_inv = new ImageIcon("image/player_inv.gif");

	private GameState state;
	private int hp;
	private int speed;

	private double bulletPeriod = 0.08; // 80ms
	private double elapsed = 0;

	private int invincibleTime = 1; // @YCW: current invincible time is 2 seconds, enemy에게 맞으면 발동
	private double elapsedInvincibleTime = 0;
	private boolean isCollision = false;
	private boolean isInvincible = false;

	public Player(GameState state) {
		super((384 - 80) / 2, 512 - 100, 50, 50); // FIXME @YDH : 상수 선언
		this.hp = 3; // @YCW: default hp value = 3
		this.speed = 500;
		this.state = state;
	}

	public void move(double dt) {
		//	if (isInvincible == false) {
		if (left) {
			x -= this.speed * dt;
		}
		if (right) {
			x += this.speed * dt;
		}
		//	}
	}
	public void fire(double dt) {
		if (isInvincible == false) {
			elapsed += dt;
			if (elapsed > bulletPeriod) {
				Bullet bullet = new Bullet((int) x);
				((PlayState) state).getBullets().add(bullet);

				elapsed = 0;
			}
		}
	}

	public void input(KeyHandler key, MouseHandler mouse) {
		left = key.left.pressed;
		right = key.right.pressed;

		double centerX = x + 40;

		if (centerX <= 5) {
			x = 5 - 40;
			left = false;
			if (key.left.pressed && key.right.pressed) right = false;
		}
		if (centerX >= 384 - 5) { // FIXME @YDH : 상수 선언
			x = 384 - 5 - 40;
			right = false;
			if (key.left.pressed && key.right.pressed) left = false;
		}
	}

	// @YCW: add checkColision for interaction between Character and Enemy ( + Character and Obstacle )
	public void checkCollision(double dt) {
		ArrayList<Enemy> enemies = ((PlayState)state).getEnemies();
		Obstacle obstacle = ((PlayState)state).getObstacle();

		for(int i = 0; i < enemies.size(); i++)
			if((abs((this.x + this.width / 2) - (enemies.get(i).getX() + this.width / 2)) < (enemies.get(i).getWidth() / 2 + this.width / 2) &&
					abs((this.y + this.height / 2) - (enemies.get(i).getY() + enemies.get(i).getHeight() / 2)) < (enemies.get(i).getHeight() / 2 + this.height / 2)) &&
					isInvincible == false)
			{
				isCollision = true;
			}

		if(obstacle != null)
			if((abs (this.x - obstacle.getX()) <= 50) &&
					((obstacle.hitY() <= this.y) && (obstacle.hitY() + 20 >= this.y)) &&
					isInvincible == false)
			{
				isCollision = true;
			}

		if (isCollision == true) {
			hp = hp - 1;
			isCollision = false;
			isInvincible = true; // if isInvincible = true, then move, fire, render functions are stopped.
		}

		if (isInvincible == true) {
			elapsedInvincibleTime += dt;
			if (elapsedInvincibleTime > invincibleTime) {
				isInvincible = false;

				elapsedInvincibleTime = 0;
			}
		}
	}

	public void render(Graphics2D g) {
		if (!isInvincible)
			player.paintIcon(null,g,(int)x,(int)y);
		else
			player_inv.paintIcon(null,g,(int)x,(int)y);
	}

	// @YCW: add getX for x position of bullet
	public double getX() {
		return x;
	}

	public int getHp() {
		return hp;
	}
}