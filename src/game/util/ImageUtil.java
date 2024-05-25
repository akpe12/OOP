package game.util;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageUtil {
    public static BufferedImage rotateImage(BufferedImage originalImage, double angle) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        
        BufferedImage rotatedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g = rotatedImage.createGraphics();
        
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), width / 2.0, height / 2.0);
        
        g.setTransform(transform);
        g.drawImage(originalImage, 0, 0, null);
        
        g.dispose();
        
        return rotatedImage;
    }
}
