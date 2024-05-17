package game.state;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import game.entity.Bullet;
import game.entity.Enemy;
import game.entity.Player;
import game.map.Background;
import game.util.KeyHandler;
import game.util.MouseHandler;

import static java.lang.Math.abs;

public class PlayState extends GameState {

	private Background background;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		background = new Background();
		player = new Player(this);
		enemies = new ArrayList<Enemy>();
		spawn();
		bullets = new ArrayList<Bullet>();

	}

	@Override
	public void update(double dt) {
		background.move(dt);
		player.move(dt);
		player.fire(dt);

		// @JW : enemies 업데이트 함수
		enemyHit();
		for(int i = 0; i < enemies.size(); i++)
		{
			if((enemies.get(i).isOut()))
			{
				enemies.clear();
				spawn();
			}

			if (enemies.get(i).isAlive())
				enemies.get(i).move(dt);
			else
				enemies.remove(i);
		}

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

	public void spawn() {
		int x = 0;

		for(int i = 0 ; i < 5; i++)
		{
			Enemy tempE = new Enemy(x);
			enemies.add(tempE);
			x += 78;
		}
	}

	// @JW : FIXME 총알에 맞은 몬스터는 y좌표 딜레이있음
	public void enemyHit(){
		for(int i = 0; i < enemies.size(); i++)
		{
			// @JW : 좌표 범위내에 들어오면 getHit(getDamage) 실행
			for(int j = 0; j < bullets.size(); j++)
				if((abs (enemies.get(i).getX() - bullets.get(j).getX()) <= 40) &&
						(abs (enemies.get(i).getY() - bullets.get(j).getY()) <= 40))
					enemies.get(i).getHit(bullets.get(j).getDam());
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

		for(Bullet bullet: bullets) {
			bullet.render(g);
		}

		for(Enemy i : enemies)
			i.render(g);
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
}

