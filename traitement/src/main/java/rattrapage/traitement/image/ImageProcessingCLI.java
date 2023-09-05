package rattrapage.traitement.image;

import org.apache.commons.cli.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.AffineTransform;

import java.awt.image.BufferedImage; // pour après
import java.io.FileOutputStream; // pour apprès
import java.io.File; // pour après
import java.io.IOException;

public class ImageProcessingCLI {
    public static void main( String[] args ) {
        
    }

    public static BufferedImage convertToGrayScale(BufferedImage image){
        int width = image.getWidth();
        int height =  image.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x= 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int gray = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
                grayImage.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
            }
        }
        return grayImage;
    }

    public static BufferedImage convertToSepia(BufferedImage image) {
        int width = image.getWidth();
        int height =   image.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int newR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int newG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int newB = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                newR = Math.min(newR, 255);
                newG = Math.min(newG, 255);
                newB = Math.min(newB, 255);

                int newRGB = (newR << 16) | (newG << 8) | newB;
                sepiaImage.setRGB(x, y, newRGB);

            }
        }
        return sepiaImage;
    }

}