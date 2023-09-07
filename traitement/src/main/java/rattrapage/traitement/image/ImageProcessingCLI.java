package rattrapage.traitement.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProcessingCLI {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Syntaxe incorrecte. Utilisation :");
            System.err.println("java ImageProcessingCLI <chemin_de_l'image> <transformation> <chemin_de_sortie>");
            System.err.println("Transformation : grayscale ou sepia");
            System.exit(1);
        }

        String inputPath = args[0];
        String transformation = args[1];
        String outputPath = args[2];

        if (!transformation.equalsIgnoreCase("grayscale") && !transformation.equalsIgnoreCase("sepia")) {
            System.err.println("Erreur : La transformation doit être 'grayscale' ou 'sepia'.");
            System.exit(1);
        }

        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        if (!inputFile.exists()) {
            System.err.println("Erreur : Le fichier source n'existe pas.");
            System.exit(1);
        }

        if (outputFile.exists()) {
            System.out.print("Attention : Le fichier de destination existe déjà. Voulez-vous l'écraser ? (oui/non) : ");
            String confirmation = System.console().readLine().trim().toLowerCase();

            if (!confirmation.equals("oui")) {
                System.out.println("Opération annulée.");
                System.exit(0);
            }
        }

        try {
            BufferedImage sourceImage = ImageIO.read(inputFile);
            BufferedImage transformedImage;

            if (transformation.equalsIgnoreCase("grayscale")) {
                transformedImage = convertToGrayScale(sourceImage);
            } else {
                transformedImage = convertToSepia(sourceImage);
            }

            ImageIO.write(transformedImage, "jpeg", outputFile);
            System.out.println("Transformation terminée. Image enregistrée sous : " + outputPath);
        } catch (IOException e) {
            System.err.println("Erreur lors du traitement de l'image : " + e.getMessage());
            System.exit(1);
        }
    }

    // Conversion en niveaux de gris
    public static BufferedImage convertToGrayScale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int gray = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
                grayImage.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
            }
        }
        return grayImage;
    }

    // Conversion en sépia
    public static BufferedImage convertToSepia(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
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
