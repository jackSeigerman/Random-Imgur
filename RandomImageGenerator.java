import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class RandomImageGenerator {
    // Characters used to generate the random string
    private static final String CHARS = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghiklmnopqrstuvwxyz";
    // Length of the random string
    private static final int STRING_LENGTH = 5;

    // Method to generate a random string
    public static String generateRandomString() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < STRING_LENGTH; i++) {
            int randNum = (int) (Math.random() * CHARS.length());
            text.append(CHARS.charAt(randNum));
        }
        return text.toString();
    }

    // Method to fetch a random image from Imgur
    public static BufferedImage fetchRandomImage(String text) throws IOException {
        String source = "https://i.imgur.com/" + text + ".jpg";
        URL url = new URL(source);
        return ImageIO.read(url);
    }
}
