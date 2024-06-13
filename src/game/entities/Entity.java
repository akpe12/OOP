package game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import game.main.GameObject;
import game.states.Playing;

public abstract class Entity extends GameObject { 

	public static final int BACKGROUND = 0;
	public static final int PLAYER = 1;
	public static final int ENEMY = 2;
	public static final int BULLET = 3;
	public static final int EFFECT = 4;
	public static final int OBSTACLE = 5;
	
	protected float x;
	protected float y;
	protected float width;
	protected float height;
	
	protected float centerX;
	protected float centerY;

    protected float speed;
	
	protected List<Rectangle2D.Float> hitboxs;
	
	private static boolean hitboxEnabled = false;
	private static boolean f3Pressed = false;
	
    public Entity() {
    	hitboxs = new ArrayList<Rectangle2D.Float>();
    }

	public Entity(float x, float y) {
		this();
		
		this.x = x;
		this.y = y;
	}

    public Entity(float x, float y, float width, float height) {
    	this(x, y);
    	
    	this.width = width;
    	this.height = height;
    	
    	this.centerX = x + width / 2;
    	this.centerY = y + height / 2;
    }
	
	public static void update() {
		if (key.f3.isPressed()) {
			f3Pressed = true;
		} else {
			if (f3Pressed) {
				hitboxEnabled = !hitboxEnabled;
				f3Pressed = false;
			}
		}
	}

	public void update(double dt) {
		centerX = x + width / 2;
		centerY = y + height / 2;
		
		for (Rectangle2D.Float h : hitboxs) {
			h.x = centerX - h.width / 2;
			h.y = centerY - h.height / 2;
		}
	}
	
	public void render(Graphics2D g) {
		if (hitboxEnabled) {
			if (hitboxs != null) {
				drawHitboxs(g);
			}
		}
	}
    
    protected void drawHitboxs(Graphics2D g) {
    	for (Rectangle2D.Float h : hitboxs) {
    		g.setColor(Color.PINK);
    		g.drawRect((int)h.x, (int)h.y, (int)h.width, (int)h.height);
    	}
	}
	
	public float getCenterX() {
		return centerX;
	}

	public float getCenterY() {
		return centerY;
	}
	
}
