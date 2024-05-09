package game.state;

import java.awt.Graphics2D;

import game.entity.Player;
import game.map.Background;
import game.util.KeyHandler;
import game.util.MouseHandler;

public class PlayState extends GameState {

	private Player player;
	private Background background;
	// TODO enemy 멤버변수 만들기
	// TODO bullet 멤버변수 만들기

	public PlayState() {
		super();
		player = new Player();
		background = new Background();
	}

	@Override
	public void update() {
		background.move();
		player.move();
		// TODO enemy.move();
		// TODO bullet.move();
	}

	@Override
	public void input(KeyHandler key, MouseHandler mouse) {
		player.input(key, mouse);
	}
	
	@Override
	public void render(Graphics2D g) {
		background.render(g);
		player.render(g);
		// TODO enemy.render(g);
		// TODO projectile.render(g);
	}

}

