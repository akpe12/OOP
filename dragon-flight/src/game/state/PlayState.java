package game.state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.entity.Bullet;
import game.entity.Enemy;
import game.entity.Player;
import game.map.Background;
import game.util.KeyHandler;
import game.util.MouseHandler;


public class PlayState extends GameState {

	private Background background;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;

	// @JW : spawn related, milliseconds.
	private final int SPAWN_DELAY = 3000;
	private long lastSpawnTime;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		background = new Background();
		player = new Player(this);
		enemies = new ArrayList<Enemy>();		// @JW : Enemy 객체에 state 대입은 아래 spawn 메소드에서
		spawn();
		bullets = new ArrayList<Bullet>();
	}

	@Override
	public void update(double dt) {
		background.move(dt);

		player.move(dt);
		player.fire(dt);
		player.checkCollision(dt); // @YCW: pass dt to this for checking elapsed invincible time

		for (int i = 0; i < bullets.size();) {
			Bullet bullet = bullets.get(i);
			bullet.move(dt);

			if (bullet.isOut()) {
				bullets.remove(bullet);
				continue;
			}
			i++;
		}

		// @JW : enemies related
		if (System.currentTimeMillis() - lastSpawnTime >= SPAWN_DELAY){

			if(enemies.isEmpty())
				spawn();

			enemies.clear();
			spawn();
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).enemyHit();

			if (enemies.get(i).isAlive())
				enemies.get(i).move(dt);
			else
				enemies.remove(i);
		}
		
		isGameOver();
	}

	public void spawn() {
		lastSpawnTime = System.currentTimeMillis();

		int x = 0;
		for(int i = 0 ; i < 5; i++)
		{
			Enemy tempE = new Enemy(x, this);
			enemies.add(tempE);
			x += 78;
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

		for(Bullet b: bullets)
			b.render(g);

		for(Enemy e : enemies)
			e.render(g);
	}
	
	// @YCW: added below function for changing state to EndState when the player is dead
	public void isGameOver() {
		if (player.isDead() == true) {
			gsm.setState(new EndState(gsm));
		}
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
}

