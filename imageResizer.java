import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// Custom panel for displaying an image
public class imageResizer extends JPanel
{
    // The image to be displayed
    private static BufferedImage image;

    // Override the paintComponent method to draw the image
    @Override
    protected void paintComponent(Graphics g)
    {
        // Call the superclasses paintComponent method
        super.paintComponent(g);
        if (image != null)
        {
            // Use Graphics2D for better rendering control
            Graphics2D g2d = (Graphics2D) g;

            // Set rendering hints for better quality
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Get the dimensions of the panel and the image
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            // Calculate the aspect ratios
            double panelAspect = (double) panelWidth / panelHeight;
            double imageAspect = (double) imageWidth / imageHeight;

            // Calculate the dimensions for the scaled image
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

            // Calculate the position to center the image
            int x = (panelWidth - drawWidth) / 2;
            int y = (panelHeight - drawHeight) / 2;

            // Draw the image
            g2d.drawImage(image, x, y, drawWidth, drawHeight, this);
        }
    }

    // Method to set the image and repaint the panel
    public void setImage(BufferedImage image)
    {
        this.image = image;
        // Trigger a repaint to display the new image
        repaint();
    }

    //getter method
    public static BufferedImage getImage(){
        return image;
    }
}
