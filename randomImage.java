// Created by Aryeh Bloom and Jack Seigerman
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

// Class to generate a random image
public class RandomImage {
    private static final String POSSIBLE_CHARACTERS = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CHARACTER_LENGTH = 5;
    private static String idText;

    public static String getIdText() {
        return idText;
    }

    public static String generateRandomString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < CHARACTER_LENGTH; i++) {
            int randNum = (int) (Math.random() * POSSIBLE_CHARACTERS.length());
            text.append(POSSIBLE_CHARACTERS.charAt(randNum));
        }
        return text.toString();
    }

    public static BufferedImage fetchRandomImage(String text) throws IOException {
        String source = "https://i.imgur.com/" + text + ".jpg";
        URL url = new URL(source);
        return ImageIO.read(url);
    }

    public static void generateAndDisplayImage(ImageResizer imagePanel) {
        String text = generateRandomString();
        BufferedImage image = null;

        // Retry fetching the image a limited number of times
        for (int attempts = 0; attempts < 5; attempts++) {
            try {
                image = fetchRandomImage(text);
                if (image != null && image.getWidth() != 161) {
                    imagePanel.setImage(image);
                    idText = text;
                    Gallery.enqueue(text);
                    break; // Exit loop on success
                }
            } catch (IOException e) {
                // Log or handle the exception as needed
            }
        }
    }

    public static String getID() {
        return idText;
    }
}
