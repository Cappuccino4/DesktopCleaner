package austin.cappuccio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class fileManipulation {
    // class for the OS functions of the program


    // Deleting a file
    public static boolean delete(String fileName) {
        String userHome = System.getProperty("user.home");

        // Construct the path to the Desktop folder
        Path desktopPath = Paths.get(userHome, "Desktop");
        Path path = desktopPath.resolve(fileName);

        System.out.println(path);

        try {
            Files.delete(path);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to delete file: " + e.getMessage());
            return false;
        }
        
    }

    // Moving a file
    public static boolean moveFile(String fileToMove) {
        String userHome = System.getProperty("user.home");

        // Construct the path to the Desktop folder
        Path desktopPath = Paths.get(userHome, "Desktop");
        JFrame frame = new JFrame("Select Directory");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(400, 300);
         frame.setLocationRelativeTo(null); // Center the frame

         // Create a JFileChooser object
         JFileChooser fileChooser = new JFileChooser(desktopPath.toString());

         // Set the file selection mode to directories only
         fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

   
         // Disable the "All files" option
         fileChooser.setAcceptAllFileFilterUsed(false);

         // Show the dialog and get the result
         int result = fileChooser.showOpenDialog(frame);

         // Check if the user clicked "OK"
         if (result == JFileChooser.APPROVE_OPTION) {
             // Get the selected file (which is a directory in this case)
             File selectedDirectory = fileChooser.getSelectedFile();

             // Get the path of the selected directory
             String directoryPath = selectedDirectory.getAbsolutePath();

             Path des = Paths.get(directoryPath);
             Path destination = des.resolve(fileToMove);

             // Move our file
             Path path = desktopPath.resolve(fileToMove);
             try {
                Files.move(path, destination);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
         } else {
             System.out.println("No directory selected.");
             return false;
         }

         frame.dispose(); // Close the frame after use

        return true;
    }

}