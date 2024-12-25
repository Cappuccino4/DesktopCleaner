package austin.cappuccio;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class App 
{

    public static Window w = new Window();
    public static HashMap<String, Icon> dict = new HashMap<String, Icon>();

    public void makeWindow() {
        w.DesktopCleanerWindow(dict);
    }

    public void loopDesktop() {
        String userHome = System.getProperty("user.home");

        // Construct the path to the Desktop folder
        Path desktopPath = Paths.get(userHome, "Desktop");
        File directory = new File(desktopPath.toString());

        if (directory.exists() && directory.isDirectory()) {
            // Get the FileSystemView to retrieve file icons and names
            FileSystemView fileSystemView = FileSystemView.getFileSystemView();

            // Get the list of files in the directory
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    
                    // Get the file name
                    String fileName = fileSystemView.getSystemDisplayName(file);

                    // Get the file icon
                    Icon fileIcon = fileSystemView.getSystemIcon(file);

                    if (!fileName.equalsIgnoreCase("desktop.ini") && !fileName.equalsIgnoreCase("DesktopMaid.jar")) {
                        dict.put(fileName, fileIcon);
                    }
                }
            } else {
                System.out.println("The directory is empty or cannot be read.");
            }
        } else {
            System.out.println("Invalid directory path.");
        }
    }

    public static void main( String[] args )
    {
        App a = new App();
        System.out.println("Made app");
        a.loopDesktop();
        a.makeWindow();
    }
}
