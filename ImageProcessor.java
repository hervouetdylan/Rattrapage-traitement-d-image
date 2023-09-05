import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java ImageProcessor <inputImagePath> <outputImagePath> <transformationType>");
            System.out.println("Available transformation types: grayscale, sepia");
            return;
        }

        String inputImagePath = args[0];
        String outputImagePath = args[1];
        String transformationType = args[2];

        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
            BufferedImage outputImage = null;

            if ("grayscale".equalsIgnoreCase(transformationType)) {
                outputImage = convertToGrayscale(inputImage);
            } else if ("sepia".equalsIgnoreCase(transformationType)) {
                outputImage = applySepiaFilter(inputImage);
            } else {
                System.out.println("Invalid transformation type. Available options: grayscale, sepia");
                return;
            }

            if (outputImage != null) {
                File outputImageFile = new File(outputImagePath);
                ImageIO.write(outputImage, "jpg", outputImageFile);
                System.out.println("Transformation complete. Output image saved to: " + outputImagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage convertToGrayscale(BufferedImage inputImage) {
        BufferedImage outputImage = new BufferedImage(
                inputImage.getWidth(),
                inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int rgb = inputImage.getRGB(x, y);
                int gray = (int) (0.2989 * ((rgb >> 16) & 0xFF) + 0.5870 * ((rgb >> 8) & 0xFF) + 0.1140 * (rgb & 0xFF));
                int newRgb = (gray << 16) + (gray << 8) + gray;
                outputImage.setRGB(x, y, newRgb);
            }
        }

        return outputImage;
    }

    private static BufferedImage applySepiaFilter(BufferedImage inputImage) {
        BufferedImage outputImage = new BufferedImage(
                inputImage.getWidth(),
                inputImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int rgb = inputImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int newR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int newG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int newB = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                newR = Math.min(newR, 255);
                newG = Math.min(newG, 255);
                newB = Math.min(newB, 255);

                int newRgb = (newR << 16) + (newG << 8) + newB;
                outputImage.setRGB(x, y, newRgb);
            }
        }

        return outputImage;
    }
}
