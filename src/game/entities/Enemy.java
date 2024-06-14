package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.ImageIcon;

import game.entities.effect.Effect;
import game.entities.effect.EnemyDeathEffect;
import game.main.Game;
import game.math.Vector2f;

public class Enemy extends Entity {
	
	public static final int WHITE = 0;
	public static final int YELLOW = 1;
	public static final int RED = 2;

    public static ImageIcon[] dragons;
    
    static {
    	dragons = new ImageIcon[3];
    	for (int i = 0; i < 3; i++) {
    		dragons[i] = loadImg(i);
    	}
    }
    
    private static ImageIcon loadImg(int enemyType) {
    	return new ImageIcon("images/entities/enemies/dragon" + enemyType + ".gif");
    }
    
    private int enemyType;

	private int maxHp;
	private int currentHp;
	
	private Rectangle2D.Float healthBar;
	private boolean drawingHealthBar = false;
	
    private Rectangle2D.Float hitbox;

    private static float startSpeed = 200.0f;
    
    public Enemy(float x, int enemyType) {
    	width = 90.0f;
    	height = 74.0f;
    	this.x = x;
    	this.y = -height;
    	
    	this.enemyType = enemyType;
        
    	switch(enemyType) {
    	case WHITE:
    		maxHp = 50;
    		break;
    	case YELLOW:
    		maxHp = 150;
    		break;
    	case RED:
    		maxHp = 250;
    		break;
    	}
    	currentHp = maxHp;
        speed = startSpeed;
        
        float healthBarWidth = 60.0f;
        float healthBarHeight = 7.0f;
        healthBar = new Rectangle2D.Float(
        		centerX - healthBarWidth / 2, y + height, healthBarWidth, healthBarHeight);
        
        float hitboxWidth = 42.0f;
        float hitboxHeight = 60.0f;
        hitbox = new Rectangle2D.Float(
        		centerX - hitboxWidth / 2, centerY - hitboxHeight / 2, hitboxWidth, hitboxHeight);
        hitboxs.add(hitbox);
    }

    @Override
	public void update(double dt) {
    	super.update(dt);
    	
    	healthBar.x = centerX - healthBar.width / 2;
    	healthBar.y = y + height;
    	
    	List<Enemy> enemies = gsm.getPlaying().getEnemies();
        List<Bullet> bullets = gsm.getPlaying().getBullets();
        
    	for (int i = bullets.size()-1; i >= 0; i--) {
    		Bullet b = bullets.get(i);
    		
            if(hitbox.intersects(b.getHitbox())) {
            	b.hit(this);
            	bullets.remove(b);
            	
            	drawingHealthBar = true;
            }
    	}
    	
		move(dt);
    	
		if (isOut()) {
			enemies.remove(this);
		} 
		if (isDead()) {
			die();
		}
	}
    
    private void move(double dt) {
        y += speed * dt;
    }

    private boolean isOut() {
        return y > Game.HEIGHT;
    }

    private boolean isDead() {
        return currentHp <= 0;
    }
    
    private void die() {
    	gsm.getPlaying().getEnemies().remove(this);
    	
    	Effect deathEffect = new EnemyDeathEffect(new Vector2f(centerX, centerY));
    	gsm.getPlaying().getEffects().add(deathEffect);
    }

	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
        dragons[enemyType].paintIcon(null, g, (int)x, (int)y);
        
        if (drawingHealthBar) {
        	g.setColor(new Color(150, 0, 0));
        	g.fillRect((int)healthBar.x, (int)healthBar.y, 
        			(int)healthBar.width, (int)healthBar.height);
        	g.setColor(Color.YELLOW);
        	g.fillRect((int)healthBar.x, (int)healthBar.y, 
        			(int)(healthBar.width * (currentHp / (float)maxHp)), (int)healthBar.height);
        	g.setColor(new Color(50, 0, 0));
        	g.drawRect((int)healthBar.x , (int)healthBar.y, (int)healthBar.width, (int)healthBar.height);
        }
	}
	
	public int getCurrentHp() {
		return currentHp;
	}
	
	public void setCurrentHp(int hp)  {
		currentHp = hp;
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
