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
        gui.add(randomImageButton, BorderLayout.SOUTH);
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

    private static class ImagePanel extends JPanel
    {
        private BufferedImage image;

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if (image != null)
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int panelWidth = getWidth();
                int panelHeight = getHeight();
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();

                double panelAspect = (double) panelWidth / panelHeight;
                double imageAspect = (double) imageWidth / imageHeight;

                int drawWidth, drawHeight;
                if (panelAspect > imageAspect)
                {
                    drawHeight = panelHeight;
                    drawWidth = (int) (panelHeight * imageAspect);
                }
                else
                {
                    drawWidth = panelWidth;
                    drawHeight = (int) (panelWidth / imageAspect);
                }

                int x = (panelWidth - drawWidth) / 2;
                int y = (panelHeight - drawHeight) / 2;

                g2d.drawImage(image, x, y, drawWidth, drawHeight, this);
            }
        }

        public void setImage(BufferedImage image)
        {
            this.image = image;
            repaint();
        }
    }
}
