package game.graphics.particle.shape;

public class Circle extends Shape {
	private float radius;
	private float radiusThickness;
	
	public Circle(float radius, float radiusThickness) {
		this.radius = radius;
		this.radiusThickness = radiusThickness;
	}
	
	public Circle(float radius) {
		this(radius, 1);
	}
	
	public float getRadius() {
		return radius;
	}
	
	public float getRadiusThickness() {
		return radiusThickness;
	}
}
