package game.state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.util.KeyHandler;
import game.util.MouseHandler;

public class GameStateManager {
	
	private ArrayList<GameState> states;
	
	public GameStateManager() {
		states = new ArrayList<GameState>();
		
		states.add(new PlayState());
	}
	
	public void start() {
		states.add(new PlayState());
	}
	
	public void update() {
		for (int i = 0; i < states.size(); i++) {
            if (states.get(i) != null) {
                states.get(i).update();
            }
        }
	}
	
	public void input(KeyHandler key, MouseHandler mouse) {
		for (int i = 0; i < states.size(); i++) {
            if (states.get(i) != null) {
                states.get(i).input(key, mouse);
            }
        }     
	}
	
	public void render(Graphics2D g) {
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i) != null) {
                states.get(i).render(g);
            }
        }
    }
	
}
