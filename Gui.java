import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gui
{
    // initialize GUI Elements
    private final ImagePanel imagePanel = new ImagePanel();
    private final JButton randomImageButton = new JButton("Generate Image");

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Gui().createAndShowGUI());
    }

    // Method to create and display the GUI
    private void createAndShowGUI()
    {
        // gui create
        JFrame gui = new JFrame("Random Image Generator");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setLayout(new BorderLayout());
        gui.setSize(500, 400);
        gui.setVisible(true);

        // image location
        gui.add(imagePanel, BorderLayout.CENTER);

        // listens for click on button
        gui.add(randomImageButton, BorderLayout.NORTH);
        randomImageButton.addActionListener(_ -> generateAndDisplayImage());
    }

    // Method to generate a random string and display the image
    private void generateAndDisplayImage()
    {
        String text = RandomImageGenerator.generateRandomString();
        // upon success of fetching image
        try
        {
            BufferedImage image = RandomImageGenerator.fetchRandomImage(text);
            if (image != null && image.getWidth() == 161)
            {
                // If the image is invalid, try again
                generateAndDisplayImage();
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
            generateAndDisplayImage();
        }
    }
}
