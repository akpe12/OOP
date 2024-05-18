package game.state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.entity.Bullet;
import game.entity.Enemy;
import game.entity.Player;
import game.entity.Bullet;
import game.map.Background;
import game.util.KeyHandler;
import game.util.MouseHandler;

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
		bullets = new ArrayList<Bullet>();
	}

	@Override
	public void update(double dt) {
		
		background.move(dt);
		
		player.move(dt);
		player.fire(dt);
		
		// enemies.move(dt);
		
	    for (int i = 0; i < bullets.size();) {
            Bullet bullet = bullets.get(i);
            bullet.move(dt);
            
            if (bullet.isOut()) {
				bullets.remove(bullet);
				continue;
			}
            
            ++i;
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
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
}
