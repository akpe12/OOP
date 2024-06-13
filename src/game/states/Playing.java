package game.states;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.audio.AudioPlayer;
import game.entities.Bullet;
import game.entities.Enemy;
import game.entities.Entity;
import game.entities.Obstacle;
import game.entities.Player;
import game.entities.effect.Effect;
import game.entities.map.Background;
import game.main.Game;
import game.utils.Timer;

public class Playing extends Gamestate {
	
	private List<List<? extends Entity>> entities;
	
	private List<Background> backgrounds;
	private List<Player> players;
	private List<Enemy> enemies;
	private List<Bullet> bullets;
	private List<Effect> effects;
	private List<Obstacle> obstacles;
	
	private List<Timer> timers;
	
	private Timer spawnEnemiesTimer;
	private Timer spawnObstacleTimer;
	private Timer speedUpTimer;

	public Playing() {
		timers = new ArrayList<>();
		entities = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			entities.add(null);
		}
		
		backgrounds = new ArrayList<>();
		players = new ArrayList<>();
		enemies = new ArrayList<>();
		bullets = new ArrayList<>();
		effects = new ArrayList<>();
		obstacles = new ArrayList<>();
		
		entities.set(Entity.BACKGROUND, backgrounds);
		entities.set(Entity.PLAYER, players);
		entities.set(Entity.ENEMY, enemies);
		entities.set(Entity.BULLET, bullets);
		entities.set(Entity.EFFECT, effects);
		entities.set(Entity.OBSTACLE, obstacles);
	}
	
	@Override
	public void reset() {
		timers.clear();
		
		for (List<? extends Entity> entity : entities) {
			if (entity == null) continue;
			
			entity.clear();
		}
		
		Background.setStartSpeed(100.0f);
		Enemy.setStartSpeed(200.0f);
		
		Obstacle.setStartMoveSpeed(300.0f);
		Obstacle.setStartMaxTrackSpeed(100.0f);
		Obstacle.setStartBlinkTime(0.5);

		spawnEnemiesTimer = new Timer(4.0, 3.0, e -> {spawnEnemies();});
		spawnObstacleTimer = new Timer(5.0, 0, e -> {spawnObstacle();});
		speedUpTimer = new Timer(1.0, e -> {speedUp();});
		
		timers.add(spawnEnemiesTimer);
		timers.add(spawnObstacleTimer);
		timers.add(speedUpTimer);
		
		spawnEnemiesTimer.start();
		spawnObstacleTimer.start();
		speedUpTimer.start();
		
		backgrounds.add(new Background());
		players.add(new Player());

		ap.playSong(AudioPlayer.PLAYING);
	}

	@Override
	public void update(double dt) {
		
		Entity.update();
		
		for (int i = timers.size()-1; i >= 0; i--) {
			timers.get(i).update(dt);
		}
		
		for (List<? extends Entity> entity : entities) {
			if (entity == null) continue;
			
			for (int i = entity.size()-1; i >= 0; i--) {
				entity.get(i).update(dt);
			}
		}
		
		if (isGameover()) {
			gsm.setState(Gamestate.GAMEOVER);
		}
		
	}

	public void spawnEnemies() {
		float enemySize = 70.0f;
		float interval = (Game.WIDTH - enemySize) / 4;
		for(int i = 0 ; i < 5; i++) {
			enemies.add(new Enemy(interval * i));
		}
	}

	public void spawnObstacle() {
		obstacles.add(new Obstacle(players.get(0).getX()));
	}
	
	public void speedUp() {
		Background.setStartSpeed(Background.getStartSpeed() * 1.0046f);
		Enemy.setStartSpeed(Enemy.getStartSpeed() * 1.0046f);
		
		Obstacle.setStartMoveSpeed(Obstacle.getStartMoveSpeed() * 1.0046f);
		Obstacle.setStartMaxTrackSpeed(Obstacle.getStartMaxTrackSpeed() * 1.0046f);
		Obstacle.setStartBlinkTime(Obstacle.getStartBlinkTime() * 0.9954);
		
		spawnEnemiesTimer.setPeriod(spawnEnemiesTimer.getPeriod() * 0.9954);
		spawnObstacleTimer.setPeriod(spawnObstacleTimer.getPeriod() * 0.9954);
	}
	
	public boolean isGameover() {
		boolean isAllDead = true;
		
		for (Player p : players) {
			if (!p.isDead()) {
				isAllDead = false;
			}
		}
		
		return isAllDead;
	}

	@Override
	public void render(Graphics2D g) {
		for (List<? extends Entity> entity : entities) {
			if (entity == null) continue;
			
			for (int i = entity.size()-1; i >= 0; i--) {
				entity.get(i).render(g);
			}
		}
	}
	
	public List<List<? extends Entity>> getEntities() {
		return entities;
	}
	
	public List<Background> getBackgrounds() {
		return backgrounds;
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public List<Bullet> getBullets() {
		return bullets;
	}

	public List<Effect> getEffects() {
		return effects;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}
	
	public List<Timer> getTimers() {
		return timers;
	}

	public Timer getSpawnEnemiesTimer() {
		return spawnEnemiesTimer;
	}

	public Timer getSpawnObstacleTimer() {
		return spawnObstacleTimer;
	}
	
}

