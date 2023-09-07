package rattrapage.traitement.image;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

public class ImageProcessingCLITest {

    @Test
    public void testConvertToGrayScale() {
        // Créez une BufferedImage de test par exemple en blanc 
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        // La je vais appeler la méthode de conversion
        BufferedImage grayImage = ImageProcessingCLI.convertToGrayScale(testImage);

        // Ca va assurer que grayImage est une image en niveaux de gris en vérifiant ses composantes de couleur
        for (int y = 0; y < grayImage.getHeight(); y++) {
            for (int x = 0; x < grayImage.getWidth(); x++) {
                int rgb = grayImage.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                // Vérifiez que les composantes de couleur rouge, vert et bleu sont egaux
                assertEquals(red, green);
                assertEquals(green, blue);
            }
        }
    }

    @Test
    public void testConvertToSepia() {
        // Créez une BufferedImage de test par exemple en blanc 
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        // La je vais appeler la méthode de conversion
        BufferedImage sepiaImage = ImageProcessingCLI.convertToSepia(testImage);

        // Vérifier que sepiaImage est une image sépia en vérifiant ses composantes de couleur
        for (int y = 0; y < sepiaImage.getHeight(); y++) {
            for (int x = 0; x < sepiaImage.getWidth(); x++) {
                int rgb = sepiaImage.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
            }
        }
    }
}
