package game.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private BufferedImage SPRITESHEET = null;
	private BufferedImage[][] spriteArray;
	
	public int w;
	public int h;
	private int wSprite;
	private int hSprite;
	
	public SpriteSheet(String file, int w, int h) {
		this.w = w;
		this.h = h;
		
		System.out.println("Loading: " + file + "...");
		SPRITESHEET = loadSprite(file);
		
		wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h;
        loadSpriteArray();
	}

	private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }
        return sprite;
    }
	
	public void loadSpriteArray() {
        spriteArray = new BufferedImage[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }
	
	public BufferedImage getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x * w, y * h, w, h);
    }
}
