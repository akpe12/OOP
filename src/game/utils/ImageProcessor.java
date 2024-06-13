package game.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class ImageProcessor {
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
    
    public static BufferedImage applyColorFilter(BufferedImage image, Color filterColor, float intensity, float alphaIntensity) {
        int width = image.getWidth();
        int height = image.getHeight();

        // 새로운 BufferedImage 생성
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                Color color = new Color(pixel, true);

                int red = clamp((int) (color.getRed() * (1 - intensity) + filterColor.getRed() * intensity));
                int green = clamp((int) (color.getGreen() * (1 - intensity) + filterColor.getGreen() * intensity));
                int blue = clamp((int) (color.getBlue() * (1 - intensity) + filterColor.getBlue() * intensity));
                int alpha = clamp((int) (color.getAlpha() * (1 - alphaIntensity))); // alpha 값을 조정

                int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                newImage.setRGB(x, y, newPixel);
            }
        }
        return newImage;
    }
    
    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
    
    public static BufferedImage processImage(BufferedImage image, float alpha) {
    	BufferedImage nbi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    	float[] scales = { 1f, 1f, 1f, alpha };
    	float[] offsets = new float[4];
    	RescaleOp rop = new RescaleOp(scales, offsets, null);
    	BufferedImage filtered = rop.filter(nbi, null);
    	
    	return filtered;
    }
}
