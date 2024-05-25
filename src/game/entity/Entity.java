package game.entity;

public abstract class Entity { 
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
    protected boolean right = false;
    protected boolean left = false;

	protected int hp;

	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}

    public Entity(double x, double y, int width, int height) {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    }

	// @JW : Enemy로 받아올 때
	public Entity(double x, double y, int width, int height, int hp) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.hp = hp;
	}
}
