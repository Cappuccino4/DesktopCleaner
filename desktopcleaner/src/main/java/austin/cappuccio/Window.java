package austin.cappuccio;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Window {
    private JFrame frame;
    private JTextPane fileDisplay;
    private HashMap<String, Icon> dict;
    public int index = 0;
    public ArrayList<String> keys;
    public String fileName;
    public Icon fileIcon;
    public int fileCount;

    public void DesktopCleanerWindow(HashMap<String, Icon> d) {
        dict = d;
        keys = new ArrayList<String>(dict.keySet());
        System.out.println(dict);
        fileCount = keys.size();
        nextFile();
        initializeWindow();
        changeFile();
    }

    private void initializeWindow() {
        // Create the main application window
        frame = new JFrame("Desktop Maid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 333);
        frame.setLayout(new BorderLayout());

        // Create a header label
        JLabel headerLabel = new JLabel("Desktop Maid", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10)); // 3 rows, 1 column, 10px spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add margins

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(255, 102, 102)); // Soft red
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setPreferredSize(new Dimension(75, 50)); // Set size
        deleteButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                deleteFile();
                            }
                
                            private void deleteFile() {
                                fileManipulation.delete(fileName);
                                nextFile();
                                changeFile();
                            } 
        });

        JButton keepButton = new JButton("Keep");
        keepButton.setBackground(new Color(102, 255, 102)); // Soft green
        keepButton.setForeground(Color.WHITE);
        keepButton.setFocusPainted(false);
        keepButton.setPreferredSize(new Dimension(75,50));
        keepButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                this.keepFile();
                            }
                
                            private void keepFile() {
                                nextFile();
                                changeFile();
                            } 
        });

        JButton moveButton = new JButton("Move");
        moveButton.setBackground(new Color(102, 178, 255)); // Soft blue
        moveButton.setForeground(Color.WHITE);
        moveButton.setFocusPainted(false);
        moveButton.setPreferredSize(new Dimension(75,50));
        moveButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                this.moveFile();
            } 

                        private void moveFile() {
                            fileManipulation.moveFile(fileName);
                            nextFile();
                            changeFile();
                        } 
        });

        // Add buttons to the panel
        buttonPanel.add(deleteButton);
        buttonPanel.add(keepButton);
        buttonPanel.add(moveButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Create a central display area (placeholder)
        fileDisplay = new JTextPane();
        fileDisplay.setFont(new Font("Times New Roman", Font.BOLD, 18));
        fileDisplay.setText("Grabbing Desktop files...");
        fileDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(fileDisplay);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make the window visible
        frame.setVisible(true);
    }

    // Public method to get the JFrame object, if needed
    public JFrame getFrame() {
        return frame;
    }

    public void changeFile() {
        fileDisplay.setText(fileName + "\n\n\n");
        // Used to change the icon for the desktop image
        ImageIcon imageIcon = (ImageIcon) fileIcon;
        ImageIcon finalIcon = new ImageIcon();
        try {
            finalIcon = resizeImage(imageIcon, 80, 80);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fileDisplay.insertIcon(finalIcon);
        StyledDocument doc = fileDisplay.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    public void nextFile() {
        if (index >= fileCount) {
            stopProgram();
        }
        fileName = keys.get(index);
        fileIcon = dict.get(fileName);
        index++;
    }

    public ImageIcon resizeImage(ImageIcon originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(originalImage.getImage(), 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return new ImageIcon(bi);
    }

    public void stopProgram() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

}
