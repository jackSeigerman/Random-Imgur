//created by Aryeh Bloom and Jack Seigerman
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.image.BufferedImage;

// Class to handle the gallery functionality
public class gallery {
    private static Queue<String> galleryIds = new LinkedList<>();
    private static final gallery instance = new gallery(); // Singleton instance

    public imageResizer topLeft = new imageResizer();
    public imageResizer topCenter = new imageResizer();
    public imageResizer topRight = new imageResizer();
    public imageResizer midLeft = new imageResizer();
    public imageResizer midCenter = new imageResizer();
    public imageResizer midRight = new imageResizer();
    public imageResizer botLeft = new imageResizer();
    public imageResizer botCenter = new imageResizer();
    public imageResizer botRight = new imageResizer();
    private boolean debounce = true;

    // Private constructor to prevent instantiation
    private gallery() {}

    // Method to get the singleton instance
    public static gallery getInstance() {
        return instance;
    }

    // Saves the last 10 image IDs to a queue
    public static void enqueu(String id) {
        if (galleryIds.size() > 8) {
            galleryIds.remove();
        }
        galleryIds.add(id);
        System.out.println("Current Queue: " + galleryIds);
    }

    public static void onLeftButtonClicked() {
        System.out.println("Gallery Button Clicked");
        gallery g = getInstance(); // Use the existing instance
        if (g.debounce) {
            g.createAndShowGallery();
            g.debounce = false;
        }
    }

    private void createAndShowGallery() {
        System.out.println("Creating Gallery");
        JFrame gal = new JFrame("Gallery");
        gal.setDefaultCloseOperation(gal.DISPOSE_ON_CLOSE);
        gal.setSize(500, 400);
        gal.setLayout(new GridLayout(3, 3));

        // Get the last nine image IDs and set them to the image panels
        int index = 0;
        for (String id : galleryIds) {
            BufferedImage image = null; // Fetch the image using the ID
            try {
                image = randomImage.fetchRandomImage(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (index) {
                case 0: topLeft.setImage(image); break;
                case 1: topCenter.setImage(image); break;
                case 2: topRight.setImage(image); break;
                case 3: midLeft.setImage(image); break;
                case 4: midCenter.setImage(image); break;
                case 5: midRight.setImage(image); break;
                case 6: botLeft.setImage(image); break;
                case 7: botCenter.setImage(image); break;
                case 8: botRight.setImage(image); break;
            }
            index++;
        }

        gal.add(topLeft);
        gal.add(topCenter);
        gal.add(topRight);
        gal.add(midLeft);
        gal.add(midCenter);
        gal.add(midRight);
        gal.add(botLeft);
        gal.add(botCenter);
        gal.add(botRight);

        gal.setVisible(true);

        // Window pane closed via user (using the red x)
        gal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                debounce = true;
                System.out.println("Gallery Closed, debounce reset");
            }
        });
    }
}
