package game.graphics.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.graphics.particle.shape.Circle;
import game.graphics.particle.shape.Shape;
import game.math.Range;
import game.math.Vector2f;
import game.util.RandomUtil;

public class ParticleSystem {
	
	// Temp
	private BufferedImage img;  // or Sprite
	private Vector2f origin;  // center position
	
	private ArrayList<Particle> particles;

	private boolean initiated = false;
	private boolean finished = false;
	
	private double particleTimer;
	
	// Main
	private Range<Double> startLifetime;
	private Range<Float> startSpeed;
	private Range<Float> startSize;
	private Range<Float> startRotation;
	private Range<Color> startColor;
	
	// Emission
	private int burstsCount;
	
	// Shape
	private Shape shape;
	
	// Color over lifetime
	// Size over lifetime
	
	// Velocity over lifetime
	private float limitSpeed;
	private float dampen;
	
	// Rotation over lifetime
	
	public ParticleSystem(String imgPath, Vector2f origin) {
		try {
			img = ImageIO.read(new File(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.origin = origin;
		this.burstsCount = 0;
	}
	
	public void setStartLifetime(double start) {
		startLifetime = new Range<Double>(start);
	}
	
	public void setStartSpeed(float start) {
		startSpeed = new Range<Float>(start);
	}
	
	public void setStartSize(float start, float end) {
		startSize = new Range<Float>(start, end);
	}
	
	public void setStartRotation(float start, float end) {
		startRotation = new Range<Float>(start, end);
	}
	
	public void setStartColor(Color start) {
		startColor =  new Range<Color>(start);
	}
	
	public void setEmission(int burstsCount) {
		this.burstsCount = burstsCount;
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public void init() {
		particles = new ArrayList<Particle>(burstsCount);
		
		if (shape instanceof Circle) {
			initCircle();
		}
		
		particleTimer = 0;
		initiated = true;
	}
	
	private void initCircle() {
		
		float radius = ((Circle)shape).getRadius();
		float radiusThickness = ((Circle)shape).getRadiusThickness();
		
		for (int i = 0; i < burstsCount; i++) {
			//
			double lifetime = RandomUtil.nextDouble(startLifetime.start, startLifetime.end);
			float speed = RandomUtil.nextFloat(startSpeed.start, startSpeed.end);
			float size = RandomUtil.nextFloat(startSize.start, startSize.end);
			float rotation = RandomUtil.nextFloat(startRotation.start, startRotation.end);
			
			//
			float scale = RandomUtil.nextFloat(radius * (1 - radiusThickness), radius);
			
			Vector2f dPos = Vector2f.createRandom(scale);
			Vector2f startPos = this.origin.add(dPos);
			
			Vector2f velocity = dPos.scale(speed);
			
			// add particle
			Particle p = new Particle(lifetime, size, rotation, startPos, velocity);
			particles.add(p);
		}
	}
	
	public void play(double dt) {
		if (initiated) {
			particleTimer += dt;
			
			if (particleTimer > startLifetime.end) {
				finished = true;
			}
			
			for (int i = particles.size() - 1; i >= 0; i--) {
				Particle p = particles.get(i);
				
				if (p.isLifetimeTimerOver(p.getLifetime())) {
					particles.remove(i);
				}
			}
			
			for (Particle p : particles) {
				p.updateTimer(dt);
				p.setVelocity(p.getVelocity().scale(0.95f));
				p.setSize(p.getSize() * 0.98f);
				
				Vector2f position = p.getPosition().add(p.getVelocity());
				p.setPosition(position);
			}
		}
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void render(Graphics2D g) {
		if (initiated) {
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).draw(g, img);
			}
		}
	}
	
}