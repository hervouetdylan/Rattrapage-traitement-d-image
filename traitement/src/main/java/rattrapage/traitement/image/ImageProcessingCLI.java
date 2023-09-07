package rattrapage.traitement.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProcessingCLI {
    //cette fonction va permette de transfomer une image en niveaux de gris ou en sépia
    public static void main(String[] args) {

        //cette parti va gérer les éxceptions au niveaux de l'écriture de la ligne dans le terminale
        if (args.length != 3) {
            System.err.println("Syntaxe incorrecte. Utilisation :");
            System.err.println("java ImageProcessingCLI <chemin_de_l'image> <transformation> <chemin_de_sortie>");
            System.err.println("Transformation : grayscale ou sepia");
            System.exit(1);
        }
        //C'est trois lignes vont enregistrer les 3 arguments (chemin_de_l'image transformation chemin_de_sortie) pour pouvoir les récupérer par la suite
        String inputPath = args[0];
        String transformation = args[1];
        String outputPath = args[2];

        //Cette partie la va confirmer la bonne écriture de la transfomation
        if (!transformation.equalsIgnoreCase("grayscale") && !transformation.equalsIgnoreCase("sepia")) {
            System.err.println("Erreur : La transformation doit être 'grayscale' ou 'sepia'.");
            System.exit(1);
        }

        //la ont va enregistrer les chemin 
        File inputFile = new File(inputPath); //d'entré
        File outputFile = new File(outputPath);//de sorti

        if (!inputFile.exists()) {
            System.err.println("Erreur : Le fichier source n'existe pas.");
            System.exit(1);
        }

        // la il va parmis le chemin indiqué si le nom existe déjà et si il veut l'écraser ou supprimer
        if (outputFile.exists()) {
            System.out.print("Attention : Le fichier de destination existe déjà. Voulez-vous l'écraser ? (oui/non) : ");
            String confirmation = System.console().readLine().trim().toLowerCase();

            if (!confirmation.equals("non")) {
                System.out.println("Opération annulée.");
                System.exit(0);
            }
        }

        //dans ce bloc on va prendre l'image depuis sont chemin puis la stocké dans une transfomed Image
        //ensuite on va appelé les fonctions de convertion pour les convertir l'image
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
        //assigne les dimmensions hauteur et largeur de l'image d'entrée
        int width = image.getWidth();
        int height = image.getHeight();
        //l'image est défini comme YPE_BYTE_GRAY ce qui signifie qu'elle sera grise
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        //cette boucle va calculer le pixel actuel soit horizontale ou verticale et la défini dans l'image
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
