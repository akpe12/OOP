package game.main;

import game.audio.AudioPlayer;
import game.entities.Entity;
import game.states.GamestateManager;
import game.utils.KeyHandler;
import game.utils.MouseHandler;

public class GameObject {

	protected static KeyHandler key;
	protected static MouseHandler mouse;
	
	protected static GamestateManager gsm;
	
	protected static AudioPlayer ap;
	
    public static void set(Game game) {
		key = game.getGamePanel().getKey();
		mouse = game.getGamePanel().getMouse();
		
		gsm = game.getGamestateManager();
		ap = game.getAudioPlayer();
    }
    
}
