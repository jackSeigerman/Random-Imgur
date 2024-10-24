// Created by Aryeh Bloom and Jack Seigerman
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

// Class to handle the gallery functionality
public class Gallery {
    private static final Queue<String> galleryIds = new LinkedList<>();
    private static final Gallery instance = new Gallery();

    private ImageResizer[] imagePanels = new ImageResizer[9];
    private boolean debounce = true;

    // Private constructor to prevent instantiation
    private Gallery() {
        for (int i = 0; i < 9; i++) {
            imagePanels[i] = new ImageResizer();
        }
    }

    public static Gallery getInstance() {
        return instance;
    }

    public static void enqueue(String id) {
        if (galleryIds.size() >= 9) {
            galleryIds.poll();
        }
        galleryIds.add(id);
    }

    public static void onGalleryButtonClicked() {
        if (getInstance().debounce) {
            getInstance().createAndShowGallery();
            getInstance().debounce = false;
        }
    }

    private void createAndShowGallery() {
        JFrame galleryFrame = new JFrame("Gallery");
        galleryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        galleryFrame.setSize(500, 400);
        galleryFrame.setLayout(new GridLayout(3, 3));

        int index = 0;
        for (String id : galleryIds) {
            try {
                BufferedImage image = RandomImage.fetchRandomImage(id);
                imagePanels[index].setImage(image);
                galleryFrame.add(imagePanels[index]);
                index++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        galleryFrame.setVisible(true);
        galleryFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                debounce = true;
            }
        });
    }
}
