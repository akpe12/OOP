package game.entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import game.audio.AudioPlayer;
import game.main.Game;
import game.states.Playing;
import game.utils.Timer;

public class Obstacle extends Entity {

    public static BufferedImage warning_1;
    public static BufferedImage warning_2;
    public static ImageIcon obstacle = new ImageIcon("images/entities/obstacles/meteo.gif");
    
    static {
    	try {
			warning_1 = ImageIO.read(new File("images/entities/obstacles/warning_stamp.png"));
	    	warning_2 = ImageIO.read(new File("images/entities/obstacles/warning_line.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private float velocity;
    private float maxTrackSpeed;
	private float threshold;  
    
    private Rectangle2D.Float hitbox;

    private Timer trackTimer; 
    private boolean tracking;  // @JW : warning -> obstacle

    private Timer blinkTimer;
    private boolean sirenSounded;
    private boolean blinking;
    
    private static float startMoveSpeed = 300.0f;
    private static float startMaxTrackSpeed = 100.0f;
    private static double startBlinkTime = 0.5;

    public Obstacle(float x) {
        super(x, 0, 100, 200);
        speed = startMoveSpeed;
        maxTrackSpeed = startMaxTrackSpeed;
        threshold = 20.0f;
        
        float size = 30.0f;
		hitbox = new Rectangle2D.Float(0, -size, size, size);
		hitboxs.add(hitbox);
        
        trackTimer = new Timer(2.0);
        tracking = true;

        blinkTimer = new Timer(startBlinkTime);
        sirenSounded = false;
        blinking = false;
        
        gsm.getPlaying().getTimers().add(trackTimer);
        gsm.getPlaying().getTimers().add(blinkTimer);
        
        gsm.getPlaying().getSpawnObstacleTimer().stop();
        trackTimer.start();
    }

	@Override
	public void update(double dt) {
        if (tracking) {
        	trackPlayer(dt);
            x = centerX - warning_1.getWidth() / 2;
            
        	if (trackTimer.getCount() >= 1) {
        		trackTimer.reset();
        		tracking = false;
        		
        		blinkTimer.start();
        		blinking = true;
            }
    	} else if (blinking) {
    		if (!sirenSounded) {
        		if (blinkTimer.getCount() >= 2) {
        			ap.playEffect(AudioPlayer.SIREN);
        			sirenSounded = true;
        		}
    		}
    		if (blinkTimer.getCount() >= 4) {
        		x = centerX - width / 2;
                y = -height;
    			
    			ap.playEffect(AudioPlayer.FIREBALL);
    			
    			blinkTimer.reset();
    			blinking = false;
    		}
        } else {
    		hitbox.x = centerX - hitbox.width / 2;
    		hitbox.y = 165 + y;
    		
    		move(dt);
    	}
		
		if (isOut()) {
			gsm.getPlaying().getObstacles().remove(this);
			
			gsm.getPlaying().getTimers().remove(trackTimer);
			gsm.getPlaying().getTimers().remove(blinkTimer);
			
			gsm.getPlaying().getSpawnObstacleTimer().start();
		}
	}
	
	private void trackPlayer(double dt) {
    	Player p = gsm.getPlaying().getPlayers().get(0);
    	
    	float dx = p.getCenterX() - centerX;
        	velocity += dx * 0.06f;  // interpolation
    	
    	if (Math.abs(velocity) > maxTrackSpeed) {
            velocity = (velocity > 0) ? maxTrackSpeed : -maxTrackSpeed;
        } 

    	if (Math.abs(dx) < threshold) {
            velocity *= 0.95f;
        }
    	
    	centerX += velocity * dt;
	}

	private void move(double dt) {
    	y += speed * dt;
    }
    
    private boolean isOut() {
        return y > Game.HEIGHT;
    }
    
    @Override
    public void render(Graphics2D g) {
    	super.render(g);
    	
        if (tracking) {
            g.drawImage(warning_1, (int)x, (int)y, warning_1.getWidth(), warning_1.getHeight(), null);
            g.drawImage(warning_2, (int)x, (int)y, warning_2.getWidth(), warning_2.getHeight(), null);
        } else if (blinking) {
        	if (blinkTimer.getCount() % 2 == 0) {
        		g.drawImage(warning_1, (int)x, (int)y, warning_1.getWidth(), warning_1.getHeight(), null);
        	}
        	g.drawImage(warning_2, (int)x, (int)y, warning_2.getWidth(), warning_2.getHeight(), null);
        } else {
            obstacle.paintIcon(null, g, (int)x, (int)y);
        }
    }

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	public static float getStartMoveSpeed() {
		return startMoveSpeed;
	}

	public static void setStartMoveSpeed(float startMoveSpeed) {
		Obstacle.startMoveSpeed = startMoveSpeed;
	}

	public static float getStartMaxTrackSpeed() {
		return startMaxTrackSpeed;
	}

	public static void setStartMaxTrackSpeed(float startMaxTrackSpeed) {
		Obstacle.startMaxTrackSpeed = startMaxTrackSpeed;
	}

	public static double getStartBlinkTime() {
		return startBlinkTime;
	}

	public static void setStartBlinkTime(double startBlinkTime) {
		Obstacle.startBlinkTime = startBlinkTime;
	}
}
