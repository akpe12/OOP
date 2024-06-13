package game.entities.effect;

import java.awt.Graphics2D;

import game.audio.AudioPlayer;
import game.graphics.particle.ParticleSystem;
import game.graphics.particle.shape.Circle;
import game.graphics.particle.shape.Mesh;
import game.math.Vector2f;

public class EnemyDeathEffect extends Effect {
	
	private ParticleSystem enemyDeathCloud;
	private ParticleSystem enemyDeathGlow;

	public EnemyDeathEffect(Vector2f origin) {
		enemyDeathCloud = new ParticleSystem("images/entities/effects/enemyDeathCloud.png", origin);
		enemyDeathGlow = new ParticleSystem("images/entities/effects/enemyDeathGlow.png", origin);
		
		setEnemyDeathCloud();
		setEnemyDeathGlow();
		
		enemyDeathCloud.init();
		enemyDeathGlow.init();
		
    	ap.playEffect(AudioPlayer.ENEMY_DEATH);
	}
	
	private void setEnemyDeathCloud() {
		enemyDeathCloud.setStartLifetime(0.45);
		enemyDeathCloud.setStartSpeed(0.15f);
		enemyDeathCloud.setStartSize(0.5f, 1f);
		enemyDeathCloud.setStartRotation(0, 360.0f);
		
		enemyDeathCloud.setEmission(8);
		enemyDeathCloud.setShape(new Circle(8.0f, 0.8f));
	}
	
	private void setEnemyDeathGlow() {
		enemyDeathGlow.setStartLifetime(0.25);
		
		enemyDeathGlow.setShape(new Mesh());
		//enemyDeathGlow.setColorOverLifetime();
	}
	
	public boolean isFinished() {
		return enemyDeathCloud.isFinished() && enemyDeathGlow.isFinished();
	}
	
	@Override
	public void update(double dt) {
		enemyDeathCloud.play(dt);
		enemyDeathGlow.play(dt);
		
		if (isFinished()) {
			gsm.getPlaying().getEffects().remove(this);
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		super.render(g);
		
		enemyDeathCloud.render(g);
		enemyDeathGlow.render(g);
	}
	
}
