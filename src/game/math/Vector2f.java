package game.math;

import java.util.Random;

public class Vector2f {
	public float x;
    public float y;
    
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2f() {
    	this(0, 0);
    }
    
    public static Vector2f createRandom(float scale) {
        Random random = new Random();
        
        float angle = (float) (random.nextDouble() * 2 * Math.PI);
        float x = (float) Math.cos(angle);
        float y = (float) Math.sin(angle);
        
        return new Vector2f(x * scale, y * scale);
    }

    public Vector2f add(Vector2f vec) {
    	return new Vector2f(this.x + vec.x, this.y + vec.y);
    }
    
    public Vector2f scale(float scale) {
    	return new Vector2f(this.x * scale, this.y * scale);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
