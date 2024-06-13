package game.entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.ImageIcon;

import game.audio.AudioPlayer;
import game.entities.effect.Effect;
import game.entities.effect.EnemyDeathEffect;
import game.main.Game;
import game.math.Vector2f;

public class Enemy extends Entity {

    public static ImageIcon enemy = new ImageIcon("images/entities/enemy_mov.gif");  // FIXME
    private static float size = 70.0f;

	private int hp;
	
    private Rectangle2D.Float hitbox;

    private static float startSpeed = 200.0f;
    
    public Enemy(float x) {
        super(x, -70, (int)size, (int)size);
        
        hp = 100;
        speed = startSpeed;
        
        float hitboxWidth = 42.0f;
        float hitboxHeight = 60.0f;
        hitbox = new Rectangle2D.Float(
        		centerX - hitboxWidth, centerY - hitboxHeight, hitboxWidth, hitboxHeight);
        hitboxs.add(hitbox);
    }

    @Override
	public void update(double dt) {
    	super.update(dt);
    	
    	List<Enemy> enemies = gsm.getPlaying().getEnemies();
        List<Bullet> bullets = gsm.getPlaying().getBullets();
        
    	for (int i = bullets.size()-1; i >= 0; i--) {
    		Bullet b = bullets.get(i);
    		
            if(hitbox.intersects(b.getHitbox())) {
            	b.hit(this);
            	bullets.remove(b);
            }
    	}
    	
		move(dt);
    	
		if (isOut()) {
			enemies.remove(this);
		} else if (isDead()) {
			die();
		}
	}

    private boolean isDead() {
        return this.hp <= 0;
    }

    private boolean isOut() {
        return this.y > Game.HEIGHT;
    }
    
    private void die() {
    	gsm.getPlaying().getEnemies().remove(this);
    	
    	Effect deathEffect = new EnemyDeathEffect(new Vector2f(centerX, centerY));
    	gsm.getPlaying().getEffects().add(deathEffect);
    }
    
    private void move(double dt) {
        y += speed * dt;
    }

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
        enemy.paintIcon(null, g, (int)x, (int)y);
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp)  {
		this.hp = hp;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public static float getStartSpeed() {
		return startSpeed;
	}

	public static void setStartSpeed(float startSpeed) {
		Enemy.startSpeed = startSpeed;
	}
    
}
