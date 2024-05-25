package game.entity;

import game.state.GameState;
import game.state.PlayState;

import java.awt.Graphics;
import javax.swing.*;

public class Obstacle extends Entity {
    private GameState state;

    public static ImageIcon warning_1 = new ImageIcon("image/warning_stamp.gif");
    public static ImageIcon warning_2 = new ImageIcon("image/warning_sign_35.png");
    public static ImageIcon obstacle = new ImageIcon("image/meteo_4.gif");

    private double speed;

    private final int METEO_DELAY = 3000;
    private long lastTargetTime;

    private boolean trackEnd = false;       // @JW : warning -> obstacle

    public Obstacle(PlayState state, double playerX) {
        super(playerX , 0);
        this.speed = 500;
        this.state = state;

        lastTargetTime = System.currentTimeMillis();
    }

    public void move(double dt) {
        Player player = ((PlayState)state).getPlayer();

        if (System.currentTimeMillis() - lastTargetTime < METEO_DELAY)
            this.x += ((player.getX() - this.x) + 11) * 0.03; // interpolation
        else
            if (!trackEnd) {        // @JW : warning 딜레이가 끝나고, trackEnd가 바뀌기 직전;
                this.x -= 23;
                this.y = -300;
                trackEnd = true;
            }
            else
                this.y += this.speed * dt;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double hitY() { // 메테오 히트박스는 gif 상 하단 끝부분
        return this.y + 120;
    }

    public void render(Graphics g) {
        if (!trackEnd)
        {
            warning_1.paintIcon(null, g, (int) x, (int) y);
            warning_2.paintIcon(null, g, (int) x, (int) y);
        }
        else
            obstacle.paintIcon(null, g, (int) x, (int) y);
    }
}
