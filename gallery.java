import javax.swing.*;
import java.awt.*;
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
        new gallery().createAndShowGallery();

    }

    private void createAndShowGallery(){
        System.out.println("Creating Gallery");
        JFrame gal = new JFrame("Gallery");
        gal.setDefaultCloseOperation(gal.EXIT_ON_CLOSE);
        gal.setLayout(new BorderLayout());
        gal.setSize(500, 400);

        gal.setVisible(true);

        gal.add(topLeft, BorderLayout.NORTH);
        gal.add(topCenter, BorderLayout.CENTER);
        gal.add(topRight, BorderLayout.EAST);

    }
}
