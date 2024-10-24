//created by Aryeh Bloom and Jack Seigerman
import javax.swing.*;
import java.awt.*;


// Main class for the GUI
public class Gui
{
    // initialize GUI Elements
    private final ImageResizer imagePanel = new ImageResizer();
    private final JButton randomImageButton = new JButton("Generate Image");
    private final JButton galleryButton = new JButton("Gallery");
    private final JButton downloadButton = new JButton("Download");

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Gui().createAndShowGUI());
    }

    // Method to create and display the GUI
    private void createAndShowGUI()
    {
        // Create the main frame
        JFrame gui = new JFrame("Random Image Generator");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setLayout(new BorderLayout());
        gui.setSize(500, 400);

        // Create a panel for the buttons with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(galleryButton);
        buttonPanel.add(randomImageButton);
        buttonPanel.add(downloadButton);

        // Add the image panel and button panel to the frame
        gui.add(imagePanel, BorderLayout.CENTER);
        gui.add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        gui.setVisible(true);

        // button listeners
        randomImageButton.addActionListener(_ -> RandomImage.generateAndDisplayImage(imagePanel));
        galleryButton.addActionListener(_ -> Gallery.onLeftButtonClicked());
        downloadButton.addActionListener(_ -> download.onRightButtonClicked(imagePanel)); // Pass the imagePanel instance
    }
}
