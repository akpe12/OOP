package game.entity;

import java.awt.Rectangle;

public abstract class Entity extends Rectangle { // FIXME 
	
    protected boolean right = false;
    protected boolean left = false;
    
    public Entity(int x, int y, int width, int height) {
    	super(x, y, width, height);
    }
    
}
