package game.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Timer {

	private double elapsed;
	private double period;
	private double offset;
	
	private int count;
	private boolean isRunning;
	
	private ActionListener listener;
	
	public Timer(double period, double offset, ActionListener listener) {
		this.period = period;
		this.offset = offset;
		this.elapsed = offset;
		this.listener = listener;
		
		this.count = 0;
		this.isRunning = false;
	}
	
	public Timer(double period, double offset) {
		this(period, offset, null);
	}
	
	public Timer(double period, ActionListener listener) {
		this(period, 0, listener);
	}
	
	public Timer(double period) {
		this(period, 0, null);
	}

	public void update(double dt) {
		if (isRunning) {
			elapsed += dt;
			
			if (elapsed >= period) {
				count += elapsed / period;
				elapsed = elapsed % period;
				
				if (listener != null) {
					notifyListener();
				}
			}
		}
	}
	
	private void notifyListener() {
		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null);
		listener.actionPerformed(event);
	}
	
	public void start() {
		isRunning = true;
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public void reset() {
		elapsed = offset;
		
		count = 0;
		isRunning = false;
	}
	
	public double getPeriod() {
		return period;
	}
	
	public void setPeriod(double period) {
		if (period >= 0) {
			this.period = period;
		}
	}
	
	public void setOffset(double offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}
	
}
