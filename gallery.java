import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Queue;


public class gallery
{
    private static Queue<String> galleryIds = new LinkedList<String>();

    public imageResizer topLeft = new imageResizer();
    public imageResizer topCenter = new imageResizer();
    public imageResizer topRight = new imageResizer();
    public imageResizer midLeft = new imageResizer();
    public imageResizer midCenter = new imageResizer();
    public imageResizer midRight = new imageResizer();
    public imageResizer botLeft = new imageResizer();
    public imageResizer botCenter = new imageResizer();
    public imageResizer botRight = new imageResizer();
    private static boolean debounce = true;


    //saves the last 10 image ids to a queue
    public static void enqueu(String id){
        if(galleryIds.size() > 8){
            galleryIds.remove();
        }
        galleryIds.add(id);
        System.out.println("Current Queue: " +galleryIds);
    }

    public static void onLeftButtonClicked()
    {
        // Add functionality for the gallery button here
        System.out.println("Gallery Button Clicked");
        if(debounce)
        {
            new gallery().createAndShowGallery();
            debounce = false;
        }

    }

    private void createAndShowGallery()
    {
        System.out.println("Creating Gallery");
        JFrame gal = new JFrame("Gallery");
        gal.setDefaultCloseOperation(gal.DISPOSE_ON_CLOSE);
        gal.setLayout(new BorderLayout());
        gal.setSize(500, 400);
        gal.setLayout(new GridLayout(3, 3));

        gal.setVisible(true);

        gal.add(topLeft, BorderLayout.NORTH);
        gal.add(topCenter, BorderLayout.NORTH);
        gal.add(topRight, BorderLayout.NORTH);

        gal.add(midLeft, BorderLayout.CENTER);
        gal.add(midCenter, BorderLayout.CENTER);
        gal.add(midRight, BorderLayout.CENTER);

        gal.add(botLeft, BorderLayout.SOUTH);
        gal.add(botCenter, BorderLayout.SOUTH);
        gal.add(botRight, BorderLayout.SOUTH);

    //window pane closed via user (using the red x)
        gal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                debounce = true;
                System.out.println("Gallery Closed, debounce reset");
            }
        });


    }
}
