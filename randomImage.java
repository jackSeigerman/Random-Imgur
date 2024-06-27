import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class randomImage
{
    // Characters used to generate the random string
    private static final String possibleCharacters = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghiklmnopqrstuvwxyz";
    // Length of the random string
    private static final int characterLength = 5;

    // Method to generate a random string
    public static String generateRandomString()
    {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < characterLength; i++)
        {
            int randNum = (int) (Math.random() * possibleCharacters.length());
            text.append(possibleCharacters.charAt(randNum));
        }
        return text.toString();
    }

    // Method to fetch a random image from Imgur
    public static BufferedImage fetchRandomImage(String text) throws IOException
    {
        String source = "https://i.imgur.com/" + text + ".jpg";
        URL url = new URL(source);
        return ImageIO.read(url);
    }

    // Method to generate a random string and display the image
    public static void generateAndDisplayImage(imageResizer imagePanel)
    {
        String text = generateRandomString();
        // upon success of fetching image
        try
        {
            BufferedImage image = fetchRandomImage(text);
            if (image != null && image.getWidth() == 161)
            {
                // If the image is invalid, try again
                generateAndDisplayImage(imagePanel);
            }
            else
            {
                // If the image is valid, display it
                if (image != null)
                {
                    imagePanel.setImage(image);
                }
            }
        }
        // ERROR (tries again)
        catch (IOException e)
        {
            generateAndDisplayImage(imagePanel);
        }
    }
}
