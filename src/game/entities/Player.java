package game.entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import game.main.Game;
import game.utils.Timer;

public class Player extends Entity {

	private static ImageIcon player = new ImageIcon("images/entities/player.gif");
	
	private Rectangle2D.Float bodyHitbox;
	private Rectangle2D.Float wingHitbox;
	
    private boolean right = false;
    private boolean left = false;

	private int hp;

	private Timer fireTimer;
	private Timer invisibleTimer;
	private Timer invincibleTimer;
	private Timer blinkTimer;

	private boolean visible = true;
	private boolean invincible = false;
	private boolean colliding = false;

	public Player() {
		super();
		
		width = 80.0f;
		height = 80.0f;
		x = (Game.WIDTH - width) / 2;
		y = Game.HEIGHT - 100.0f;
		
		hp = 3;
		speed = 400.0f;
		
		float bodyWidth = 17.0f;
		float bodyHeight = 50.0f;
		bodyHitbox = new Rectangle2D.Float(centerX - bodyWidth / 2, centerY - bodyHeight / 2, bodyWidth, bodyHeight);
		hitboxs.add(bodyHitbox);
		
		float wingWidth = 60.0f;
		float wingHeight = 25.0f;
		wingHitbox = new Rectangle2D.Float(
				centerX - wingWidth / 2, centerY - wingHeight / 2, wingWidth, wingHeight);
		hitboxs.add(wingHitbox);
		
		fireTimer = new Timer(0.08, e -> {fire();});
		invisibleTimer = new Timer(2.0);
		invincibleTimer = new Timer(2.0);
		blinkTimer = new Timer(0.2);
		
		List<Timer> t = gsm.getPlaying().getTimers();
		
		t.add(fireTimer);
		t.add(invisibleTimer);
		t.add(invincibleTimer);
		t.add(blinkTimer);
		
		fireTimer.start();
	}
	
	@Override
	public void update(double dt) {
		super.update(dt);
		
		left = key.left.isPressed();
		right = key.right.isPressed();

		if (centerX <= 0) { 
			x = -(width / 2);
			if (left && right) {}
			else left = false;
		}
		
		if (centerX >= Game.WIDTH) { 
			x = Game.WIDTH - width / 2;
			if (left && right) {}
			else right = false;
		}
		
		if (visible) {
			move(dt);
		} else {
			if (invisibleTimer.getCount() >= 1) {
				visible = true;
				
				invisibleTimer.reset();
				fireTimer.start();
				invincibleTimer.start();
				blinkTimer.start();
			}
		}

		if (invincible) {
			if (invincibleTimer.getCount() >= 1) {
				invincible = false;
				
				invincibleTimer.reset();
				blinkTimer.reset();
			}
		}
		
		checkCollision();
	}

	public void move(double dt) {
		if (left) {
			x -= this.speed * dt;
		}
		if (right) {
			x += this.speed * dt;
		}
	}
	
	public void fire() {
		Bullet b = new Bullet((int)(x + width / 2));
		gsm.getPlaying().getBullets().add(b);
	}

	// @YCW: add checkColision for interaction between Character and Enemy ( + Character and Obstacle )
	public void checkCollision() {
		List<Enemy> enemies = gsm.getPlaying().getEnemies();
		List<Obstacle> obstacles = gsm.getPlaying().getObstacles();

		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			
			if (bodyHitbox.intersects(e.getHitbox()) || wingHitbox.intersects(e.getHitbox())) {
				if (invincible == false) {
					colliding = true;
				}
			}
		}

		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle o = obstacles.get(i);
			
			if (bodyHitbox.intersects(o.getHitbox()) || wingHitbox.intersects(o.getHitbox())) {
				if (invincible == false) {
					colliding = true;
				}
			}
		}

		if (colliding == true) {
			hp--;
			
			colliding = false;
			visible = false;
			invincible = true;
			
			invisibleTimer.start();
			fireTimer.reset();
		}
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
		if (visible) {
			if (blinkTimer.getCount() % 2 == 0) {
				player.paintIcon(null, g, (int)x, (int)y);
			}
		}
	}
	
	public boolean isDead() {
		return hp <= 0;
	}

	public float getX() {
		return x;
	}

	public int getHp() {
		return hp;
	}
	
}