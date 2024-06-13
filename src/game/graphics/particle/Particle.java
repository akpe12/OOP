package game.graphics.particle;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.math.Vector2f;

public class Particle {
	private double lifetime;
	private float speed;
	private float size = 1;
	private float rotation;
	private Color color;
	private float alphaIntensity;
	
	private Vector2f position;  // center position
	private Vector2f velocity;
	
	private double lifetimeTimer = 0;
	private double sizeTimer = 0;
	
	public Particle(double lifetime, float size, float rotation, Vector2f position, Vector2f velocity) {
		this.lifetime = lifetime;
		this.size = size;
		this.rotation = rotation;
		
		this.position = position;
		this.velocity = velocity;
	}
	
	public Particle(double lifetime, float size, Vector2f position, Color color, float alphaIntensity) {
		this.lifetime = lifetime;
		this.size = size;
		this.position = position;
		this.color = color;
		this.alphaIntensity = alphaIntensity;
	}
	
	public void updateTimer(double dt) {
		lifetimeTimer += dt;
		sizeTimer += dt;
	}
	
	public boolean isLifetimeTimerOver(double interval) {
		if (lifetimeTimer > interval) {
			lifetimeTimer = 0;
			return true;
		} else {
			return false;	
		}
	}
	
	public boolean isSizeTimerOver(double interval) {
		if (sizeTimer > interval) {
			sizeTimer = 0;
			return true;
		} else {
			return false;	
		}
	}

	public void draw(Graphics2D g, BufferedImage img) {
		int x = (int)(position.x - img.getWidth() * size / 2);
		int y = (int)(position.y - img.getHeight() * size / 2);
		int w = (int)(img.getWidth() * size);
		int h = (int)(img.getHeight() * size);
		g.drawImage(img, x, y, w, h, null);
	}
	
	// Getters and Setters
	public double getLifetime() {
		return lifetime;
	}

	public void setLifetime(double lifetime) {
		this.lifetime = lifetime;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public float getAlphaIntensity() {
		return alphaIntensity;
	}

	public void setAlphaIntensity(float alphaIntensity) {
		this.alphaIntensity = alphaIntensity;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

}
	
