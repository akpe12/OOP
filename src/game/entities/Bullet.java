package game.entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.Game;

public class Bullet extends Entity {
    private static BufferedImage bullet;

	private Rectangle2D.Float hitbox;
    private int damage;

    static {
    	loadImg("images/entities/bullet_01.png");
    }

    private static void loadImg(String path) {
        try {
            bullet = ImageIO.read(new File(path));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Bullet(int playerCenterX) {
        super();

        width = 90.0f;
        height = 90.0f;
        x = playerCenterX - width / 2;
        y = Game.HEIGHT - 130;
        
    	centerX = x + width / 2;
    	centerY = y + height / 2;
        
        speed = 1000.0f;
        damage = 50;  // current enemy's hp is 100
        
        float hitboxWidth = 15.0f;
        float hitboxHeight = 60.0f;
        hitbox = new Rectangle2D.Float(
        		centerX - hitboxWidth / 2, centerY - hitboxHeight / 2, hitboxWidth, hitboxHeight);
        hitboxs.add(hitbox);
    };
    
	@Override
	public void update(double dt) {
		super.update(dt);
		
		hitbox.x = centerX - hitbox.width / 2;
		hitbox.y = centerY - hitbox.height / 2;
		
		move(dt);
        
        if (isOut()) {
        	gsm.getPlaying().getBullets().remove(this);
		}
	}

    private void move(double dt) {
        y -= speed * dt;
    }

    private boolean isOut() {
        return y < -height;
    }

    @Override
    public void render(Graphics2D g) {
    	super.render(g);
    	
        g.drawImage(bullet, (int)x, (int)y, (int)width, (int)height, null);
    }

    public void hit(Enemy e) {
    	e.setHp(e.getHp() - damage);
    }
    
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
}
