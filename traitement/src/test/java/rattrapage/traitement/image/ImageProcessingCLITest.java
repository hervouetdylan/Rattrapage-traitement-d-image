package rattrapage.traitement.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;

public class ImageProcessingCLITest {

    @Test
    public void testConvertToGrayScale() {
        BufferedImage inputImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        // Configurer une image d'entrée avec des pixels de différentes couleurs
        inputImage.setRGB(0, 0, 0xFF0000); // Rouge
        inputImage.setRGB(1, 0, 0x00FF00); // Vert
        inputImage.setRGB(0, 1, 0x0000FF); // Bleu
        inputImage.setRGB(1, 1, 0xFFFFFF); // Blanc

        BufferedImage grayImage = ImageProcessingCLI.convertToGrayScale(inputImage);

        // Vérifier que l'image résultante n'est pas nulle
        assertNotNull(grayImage);

        // Vérifier que les pixels ont été correctement convertis en niveaux de gris
        assertEquals(0xFF808080, grayImage.getRGB(0, 0)); // Gris clair
        assertEquals(0xFF808080, grayImage.getRGB(1, 0)); // Gris clair
        assertEquals(0xFF808080, grayImage.getRGB(0, 1)); // Gris clair
        assertEquals(0xFFFFFFFF, grayImage.getRGB(1, 1)); // Blanc
    }

    @Test
    public void testConvertToSepia() {
        BufferedImage inputImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        // Configurer une image d'entrée avec des pixels de différentes couleurs
        inputImage.setRGB(0, 0, 0xFF0000); // Rouge
        inputImage.setRGB(1, 0, 0x00FF00); // Vert
        inputImage.setRGB(0, 1, 0x0000FF); // Bleu
        inputImage.setRGB(1, 1, 0xFFFFFF); // Blanc

        BufferedImage sepiaImage = ImageProcessingCLI.convertToSepia(inputImage);

        // Vérifier que l'image résultante n'est pas nulle
        assertNotNull(sepiaImage);

        // Vérifier que les pixels ont été correctement convertis en sépia
        // Vous devrez adapter ces assertions en fonction de votre implémentation de la conversion sépia
        assertEquals(0xFF814800, sepiaImage.getRGB(0, 0)); // Teinte sépia
        assertEquals(0xFF498B22, sepiaImage.getRGB(1, 0)); // Teinte sépia
        assertEquals(0xFF2C2C2C, sepiaImage.getRGB(0, 1)); // Teinte sépia
        assertEquals(0xFFFFFFFF, sepiaImage.getRGB(1, 1)); // Blanc
    }
}
