package game.entity;

public abstract class Entity { 
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
    protected boolean right = false;
    protected boolean left = false;
    
    public Entity(double x, double y, int width, int height) {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    }
    
}
