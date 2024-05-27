package game.state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.effect.Effect;
import game.entity.Bullet;
import game.entity.Enemy;
import game.entity.Obstacle;
import game.entity.Player;
import game.map.Background;
import game.util.KeyHandler;
import game.util.MouseHandler;


public class PlayState extends GameState {

	private Background background;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	private Obstacle obstacle;
	private ArrayList<Effect> effects;

	// @JW : enemies spawning, milliseconds.
	private final int SPAWN_DELAY_E = 3000;
	private long lastSpawnTime_E;

	// @JW : enemies scailing, milliseconds.
	private final int SCALE_DELAY_E_2 = 10000;
	private final int SCALE_DELAY_E_3 = 10000;
	private long lastScaleTime_E;

	// @JW : obstacle trigger, milliseconds.
	private final int SPAWN_DELAY_O = 7000;
	private long lastSpawnTime_O;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		background = new Background();
		player = new Player(this);
		enemies = new ArrayList<Enemy>();		// @JW : Enemy 객체에 state 대입은 아래 spawn 메소드에서
		bullets = new ArrayList<Bullet>();
		effects = new ArrayList<Effect>();
		spawnE();
		spawnO();
	}

	@Override
	public void update(double dt) {
		background.move(dt);

		player.move(dt);
		player.fire(dt);
		player.checkCollision(dt); // @YCW: pass dt to this for checking elapsed invincible time

		updateE(dt);	// Enemy
		updateB(dt);	// Bullet
		updateO(dt);	// Obstacle
		
		isGameOver(); // @YCW: check isGameOver for changing states
	}

	public void spawnE() {
		lastSpawnTime_E = System.currentTimeMillis();
		lastScaleTime_E = System.currentTimeMillis();

		int x = 0;

		if(System.currentTimeMillis() - lastScaleTime_E >= SCALE_DELAY_E_2){


			lastScaleTime_E = System.currentTimeMillis();
		}
		else if(System.currentTimeMillis() - lastScaleTime_E >= SCALE_DELAY_E_3)
		{


		}

		for(int i = 0 ; i < 5; i++)
		{
			Enemy tempE = new Enemy(x, this);
			enemies.add(tempE);
			x += 78;
		}


	}
	public void updateE(double dt) {
		if (System.currentTimeMillis() - lastSpawnTime_E >= SPAWN_DELAY_E){
			if(enemies.isEmpty())
				spawnE();
			else
			{
				enemies.clear();
				spawnE();
			}

//			if(enemies.isEmpty())
//				spawnE();
//
//			enemies.clear();
//			spawnE();
//
		}
		for (int i = enemies.size() - 1; i >= 0; i--) {
			enemies.get(i).enemyHit();

			if (enemies.get(i).isAlive()) {
				enemies.get(i).move(dt);
			} else {
				enemies.get(i).dead();
				enemies.remove(i);
			}
		}
		
		// effect
		for (Effect e : effects)
			e.play(dt);
		for (int i = effects.size() - 1; i >= 0; i--) {
			Effect e = effects.get(i);
			
			if (e.isFinished()) {
				effects.remove(i);
			}
		}
	}

	public void updateB(double dt) {
		for (int i = 0; i < bullets.size();) {
			Bullet bullet = bullets.get(i);
			bullet.move(dt);

			if (bullet.isOut()) {
				bullets.remove(bullet);
				continue;
			}
			i++;
		}
	}

	public void spawnO() {
		lastSpawnTime_O = System.currentTimeMillis();

		obstacle = new Obstacle(this, player.getX());
	}
	public void updateO(double dt) {
		if (System.currentTimeMillis() - lastSpawnTime_O >= SPAWN_DELAY_O) {

			spawnO();
		}

		obstacle.move(dt);
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
		{
			if (e != null)
				e.render(g);
		}

		obstacle.render(g);
		
		for (Effect e : effects) 
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

	public Obstacle getObstacle(){
		return obstacle;
	}

	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<Effect> getEffects(){
		return effects;
	}
}

