import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class download {

    private static final String LAST_USED_FOLDER = "lastUsedFolder";

    // Placeholder methods for the Download button
    public static void onRightButtonClicked(imageResizer imagePanel) {
        // Add functionality for the download button here
        System.out.println("Download Button Clicked");

        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get last used directory from preferences
        Preferences prefs = Preferences.userNodeForPackage(download.class);
        String lastUsedDir = prefs.get(LAST_USED_FOLDER, System.getProperty("user.home"));

        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser(lastUsedDir);
        fileChooser.setDialogTitle("Save As");

        // Set default file name
        fileChooser.setSelectedFile(new File(randomImage.getID() + ".png"));

        // Set file filter for PNG files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        fileChooser.setFileFilter(filter);

        // Show save dialog
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Ensure the file has the .png extension
            if (!fileToSave.getAbsolutePath().endsWith(".png")) {
                fileToSave = new File(fileToSave + ".png");
            }

            // Save the directory of the selected file
            prefs.put(LAST_USED_FOLDER, fileToSave.getParent());

            // Get the image from the imagePanel
            BufferedImage image = imagePanel.getImage();
            try {
                if (image != null) {
                    ImageIO.write(image, "png", fileToSave);
                    System.out.println("Image saved to: " + fileToSave.getAbsolutePath());
                } else {
                    System.out.println("No image to save.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Save command cancelled by user.");
        }
    }
}
