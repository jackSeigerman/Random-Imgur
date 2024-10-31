//created by Aryeh Bloom and Jack Seigerman
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

// Custom panel for displaying an image
public class ImageResizer extends JPanel
{
    // The image to be displayed (instance-specific)
    private BufferedImage image;

    // Override the paintComponent method to draw the image
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

    // Method to set the image and repaint the panel
    public void setImage(BufferedImage image)
    {
        this.image = image;
        repaint();
    }

    // Getter method for the image (non-static now)
    public BufferedImage getImage(){
        return image;
    }
}
