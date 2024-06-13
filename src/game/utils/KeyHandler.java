package game.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public class Key {
		private boolean pressed;
		
		public boolean isPressed() {
			return pressed;
		}
		
		public void setPressed(boolean pressed) {
			this.pressed = pressed;
		}
	}
	
    public Key left = new Key();
    public Key right = new Key();
    public Key f3 = new Key();
    public Key anyKey = new Key();
   
    
    public void toggle(KeyEvent e, boolean pressed) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) left.setPressed(pressed);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) right.setPressed(pressed);
        if(e.getKeyCode() == KeyEvent.VK_F3) f3.setPressed(pressed);
        anyKey.setPressed(pressed);
    }

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		toggle(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggle(e, false);
	}

}
