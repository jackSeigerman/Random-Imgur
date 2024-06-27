import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class gallery
{
    //checks if folder exists in dir if not makes it
    public static void onStart()
    {

    }

    //deletes pictures upon closing the window (make sure full delete not just place in recycle can)
    public static void onClose()
    {

    }

    //opens file explorer
    public static void openFolder()
    {
        try
        {
            // Get the current working directory
            String currentDir = Paths.get("").toAbsolutePath().toString();

            // Create the path to the folder
            File folder = new File(currentDir, "gallery");

            // Check if the folder exists
            if (!folder.exists() || !folder.isDirectory())
            {
                JOptionPane.showMessageDialog(null, "Folder does not exist: " + folder.getAbsolutePath());
                return;
            }

            // Open the folder in File Explorer
            if (Desktop.isDesktopSupported())
            {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(folder);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Desktop is not supported");
            }
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    //saves every picture from session generated into gallery folder
    public static void gallerySave()
    {
        File outfile = new File("gallery/"+randomImage.getIdText()+".png");
        BufferedImage image = imageResizer.getImage();
        try
        {
            ImageIO.write(image, "png", outfile);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}



