package game.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import static java.lang.Math.abs;

import game.state.GameState;
import game.state.PlayState;

import static game.main.Window.HEIGHT;
import static game.main.Window.WIDTH;


public class Enemy extends Entity {

    // @JW : gif는 ImageIcon 사용
    public static ImageIcon enemy = new ImageIcon("image/enemy_mov.gif");

    private double speed;   // @JW : double?

    // FIXME
    private GameState state;

    public Enemy(int x, GameState state) {
        super(x, -70, 60, 60, 100);      // @JW 사이즈 조절?
        this.speed = 3;

        this.state = state;
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

    public void enemyHit(){
        ArrayList<Bullet> bullets = ((PlayState)state).getBullets();

        // @JW : 좌표 범위내에 들어오면 getHit(getDamage) 실행
        for(int i = 0; i < bullets.size(); i++)
            if((abs (x - bullets.get(i).getX()) <= 40) &&
                    (abs (y - bullets.get(i).getY()) <= 40))
                this.hp -= bullets.get(i).getDamage();
    }

    // @JW : gif는 스윙 ImageIcon의 paintIcon 사용
    public void render(Graphics g) {
        enemy.paintIcon(null, g, (int)x, (int)y);
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    // @YCW: add getWidth and getHeight for implementing the checking collision between character and enemy 
    public int getWidth() {
    	return this.width;
    }
    
    public int getHeight() {
    	return this.height;
    }
}