import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class download
{
    // Placeholder methods for the Download button
    public static void onRightButtonClicked()
    {
        // Add functionality for the download button here
        System.out.println("Download Button Clicked");
        File outfile = new File("jack/"+randomImage.getIdText()+".png");
        BufferedImage image = imageResizer.getImage();
        try {
            ImageIO.write(image, "png", outfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
