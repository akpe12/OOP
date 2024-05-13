package game.state;

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

import game.entity.Player;
import game.entity.Bullet;
import game.map.Background;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class PlayState extends GameState {

	private Background background;
	private Player player;
	// @YDH TODO private Enemy enemy;
	
	private double elapsed = 0;
	private double bulletPeriod = 0.08; // 80ms
	private Bullet bullet;
	private CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();

	public PlayState() {
		super();
		background = new Background();
		player = new Player();
		// @YDH TODO enemy = new Enemy();
//		bullet = new Bullet((int)player.getX());
	}

	@Override
	public void update(double dt) {
		background.move(dt);
		player.move(dt);
		// @YDH TODO enemy.move();
		
		fireBullet(dt);
		
		for (Bullet bullet: bullets) {
			bullet.move();
			
			if (bullet.isOut()) {
				bullets.remove(bullet);
			}
		}
	}

	@Override
	public void input(KeyHandler key, MouseHandler mouse) {
		player.input(key, mouse);
	}
	
	@Override
	public void render(Graphics2D g) {
		background.render(g);
		player.render(g);
		// @YDH TODO enemy.render(g);
		
		for(Bullet bullet: bullets) {
			bullet.render(g);
		}
	}
	
	public void fireBullet(double dt) {
		elapsed = elapsed + dt;
		if (elapsed > bulletPeriod) {
			Bullet bullet = new Bullet((int)player.getX());
			bullets.add(bullet);
			
			elapsed = 0;
		}
	}

}

