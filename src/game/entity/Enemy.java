package game.entity;

import game.state.GameState;

import java.awt.Graphics;
import javax.swing.*;

import static game.main.Window.HEIGHT;
import static game.main.Window.WIDTH;



public class Enemy extends Entity {

    // @JW : gif는 ImageIcon 사용
    public static ImageIcon enemy = new ImageIcon("image/enemy_mov.gif");

    private double speed;   // @JW : double?

    // FIXME
    private GameState state;

    public Enemy(int x) {

        super(x, -70, 60, 60, 100);      // @JW 사이즈 조절?
        this.speed = 3;
    }

    // FIXME
    public Enemy(GameState state){
        this.state =state;
    }

    public void move(double dt) {
        y += this.speed * (dt * 100);   // @JW : dt에 100 곱해야 1.6, 1.5 이렇게 배수 곱해짐

    }

    public boolean isAlive(){
        return this.hp > 0;
    }

    public boolean isOut(){     // FIXME
        return this.y > HEIGHT;
    }


    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public void getHit(int damage) {
        this.hp -= damage;
    }


    // @JW : gif는 스윙 ImageIcon의 paintIcon 사용
    public void render(Graphics g) {
        enemy.paintIcon(null, g, (int)x, (int)y);
    }

}
