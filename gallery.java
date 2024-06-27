import java.util.LinkedList;
import java.util.Queue;

public class gallery
{
    private static Queue<String> galleryIds = new LinkedList<String>();

    //saves the last 10 image ids to a queue
    public static void enqueu(String id){
        if(galleryIds.size() > 9){
            galleryIds.remove();
        }
        galleryIds.add(id);
        System.out.println("Current Queue: " +galleryIds);
    }

    public static void onLeftButtonClicked()
    {
        // Add functionality for the gallery button here
        System.out.println("Gallery Button Clicked");
    }
}
