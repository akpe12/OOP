package game.util;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
	
	public class Mouse {
		public boolean pressed;
		
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		toggle(e, true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		toggle(e, false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
