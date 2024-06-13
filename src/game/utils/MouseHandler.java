package game.utils;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
	
	private int x;
	private int y;
	
	public class Mouse {
		private boolean pressed;
		
		public boolean isPressed() {
			return pressed;
		}
		
		public void setPressed(boolean pressed) {
			this.pressed = pressed;
		}
	}
	
	public Mouse left = new Mouse();
	
    public void toggle(MouseEvent e, boolean pressed) {
        if(e.getButton() == MouseEvent.BUTTON1) {
        	left.setPressed(pressed);
        }
    }
    
    public boolean isIn(Rectangle bounds) {
    	return bounds.contains(x, y);
    }

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		toggle(e, true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		toggle(e, false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
