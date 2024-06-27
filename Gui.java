import javax.swing.*;
import java.awt.*;

public class Gui
{
    // initialize GUI Elements
    private final ImageResizer imagePanel = new ImageResizer();
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
        randomImageButton.addActionListener(_ -> RandomImageGenerator.generateAndDisplayImage(imagePanel));
    }
}
