// Created by Aryeh Bloom and Jack Seigerman
import javax.swing.*;
import java.awt.*;

// Main class for the GUI
public class Gui {
    private final ImageResizer imagePanel = new ImageResizer();
    private final JButton randomImageButton = new JButton("Generate Image");
    private final JButton galleryButton = new JButton("Gallery");
    private final JButton downloadButton = new JButton("Download");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Gui().createAndShowGUI());
    }

    // Method to create and display the GUI
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Random Image Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 400);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(galleryButton);
        buttonPanel.add(randomImageButton);
        buttonPanel.add(downloadButton);

        frame.add(imagePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        // Button listeners
        randomImageButton.addActionListener(e -> RandomImage.generateAndDisplayImage(imagePanel));
        galleryButton.addActionListener(e -> Gallery.onGalleryButtonClicked());
        downloadButton.addActionListener(e -> Download.onDownloadButtonClicked(imagePanel));
    }
}
