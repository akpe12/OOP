package game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends Entity {
	private static BufferedImage bulletImg;
	private String imgPath = "image/bullet_01.png";
	private int speed;
	private int damage;
	
	public Bullet(int playerX) {
		super(playerX - 10, 512 - 100, 100, 100);
		
		loadImg(imgPath);
		
		this.speed = 1000;
		this.damage = 1;
	}
	
	public void loadImg(String path) {
		try {
			this.bulletImg = ImageIO.read(new File(path));	
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void move(double dt) {
		y -= this.speed * dt;
	}
	
	public boolean isOut() {
		return y < -100;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void render(Graphics g) {
		g.drawImage(this.bulletImg, (int)x, (int)y, width, height, null);
	}
}