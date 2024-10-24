// Created by Aryeh Bloom and Jack Seigerman
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

// Class to handle the download functionality
public class Download {
    private static final String LAST_USED_FOLDER = "lastUsedFolder";

    public static void onDownloadButtonClicked(ImageResizer imagePanel) {
        System.out.println("Download Button Clicked");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Preferences prefs = Preferences.userNodeForPackage(Download.class);
        String lastUsedDir = prefs.get(LAST_USED_FOLDER, System.getProperty("user.home"));

        JFileChooser fileChooser = new JFileChooser(lastUsedDir);
        fileChooser.setDialogTitle("Save As");
        fileChooser.setSelectedFile(new File(RandomImage.getID() + ".png"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            saveImage(fileChooser.getSelectedFile(), imagePanel, prefs);
        } else {
            System.out.println("Save command cancelled by user.");
        }
    }

    private static void saveImage(File fileToSave, ImageResizer imagePanel, Preferences prefs) {
        if (!fileToSave.getAbsolutePath().endsWith(".png")) {
            fileToSave = new File(fileToSave + ".png");
        }
        prefs.put(LAST_USED_FOLDER, fileToSave.getParent());

        BufferedImage image = imagePanel.getImage();
        try {
            if (image != null) {
                ImageIO.write(image, "png", fileToSave);
                System.out.println("Image saved to: " + fileToSave.getAbsolutePath());
            } else {
                System.out.println("No image to save.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
