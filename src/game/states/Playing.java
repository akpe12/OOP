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
import game.ui.PlayerHp;
import game.utils.RandomMethod;
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
	private Timer levelUpTimer;
	
	private PlayerHp playerHpUi;
	
	private int level;

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
		levelUpTimer = new Timer(15.0, e -> {levelUp();});
		
		timers.add(spawnEnemiesTimer);
		timers.add(spawnObstacleTimer);
		timers.add(speedUpTimer);
		timers.add(levelUpTimer);
		
		spawnEnemiesTimer.start();
		spawnObstacleTimer.start();
		speedUpTimer.start();
		levelUpTimer.start();
		
		backgrounds.add(new Background());
		players.add(new Player());
		
		playerHpUi = new PlayerHp();
		
		level = 0;

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

	float enemyWidth = 90.0f;  // FIXME
	float enemyOffset = 4.0f;
	float interval = (Game.WIDTH + enemyOffset * 4 - enemyWidth) / 4;
	
	float[][] enemyTypeProbs = {
		{1.0f, 0, 0},
		{3/4.0f, 1/4.0f, 0},
		{2/4.0f, 2/4.0f, 0},
		{1/4.0f, 3/4.0f, 0},
		{2/8.0f, 5/8.0f, 1/8.0f},
		{1/8.0f, 5/8.0f, 2/8.0f},
		{1/8.0f, 4/8.0f, 3/8.0f},
		{1/16.0f, 7/16.0f, 8/16.0f}
	};
	
	private void spawnEnemies() {
		for(int i = 0 ; i < 5; i++) {
			float rand = RandomMethod.nextFloat(0, 1);
			int enemyType;
			
			if (rand < enemyTypeProbs[level][Enemy.WHITE]) {
	            enemyType = Enemy.WHITE;
	        } else if (rand < enemyTypeProbs[level][Enemy.WHITE] + enemyTypeProbs[level][Enemy.YELLOW]) {
	            enemyType = Enemy.YELLOW;
	        } else {
	            enemyType = Enemy.RED;
	        }
			
			float x = -enemyOffset * 2 + interval * i;
			enemies.add(new Enemy(x, enemyType));
		}
	}

	private void spawnObstacle() {
		obstacles.add(new Obstacle(players.get(0).getX()));
	}
	
	private void speedUp() {
		Background.setStartSpeed(Background.getStartSpeed() * 1.0046f);
		Enemy.setStartSpeed(Enemy.getStartSpeed() * 1.0046f);
		
		Obstacle.setStartMoveSpeed(Obstacle.getStartMoveSpeed() * 1.0046f);
		Obstacle.setStartMaxTrackSpeed(Obstacle.getStartMaxTrackSpeed() * 1.0046f);
		Obstacle.setStartBlinkTime(Obstacle.getStartBlinkTime() * 0.9954);
		
		spawnEnemiesTimer.setPeriod(spawnEnemiesTimer.getPeriod() * 0.9954);
		spawnObstacleTimer.setPeriod(spawnObstacleTimer.getPeriod() * 0.9954);
	}
	
	private void levelUp() {
		if (level < 7) {
			level++;
		}
	}
	
	private boolean isGameover() {
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
		
		playerHpUi.render(g);
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

