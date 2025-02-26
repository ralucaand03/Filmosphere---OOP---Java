package view;

import controller.MainPageController;
import model.Colors;
import model.SignUpModel;

import javax.swing.*;
import java.awt.*;

public class MainPageView {
    private JFrame frame;

    public MainPageView(MainPageController controller, SignUpModel model) {
        frame = new JFrame("Main Page");

        // Accessing the colors
        Color mainColor = Colors.MAIN_COLOR.getColor();
        Color darkColor = Colors.DARK_COLOR.getColor();
        Color lightColor = Colors.LIGHT_COLOR.getColor();


        // Main panel divided into two parts
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(darkColor);

        // Left panel with the label
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(darkColor);

        JLabel fLabel = new JLabel("\uD835\uDD3D\uD835\uDD5A\uD835\uDD5D\uD835\uDD5E\uD835\uDD60\uD835\uDD64\uD835\uDD61\uD835\uDD59\uD835\uDD56\uD835\uDD63\uD835\uDD56");
        fLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fLabel.setFont(fLabel.getFont().deriveFont(Font.PLAIN, 40));
        fLabel.setForeground(lightColor);

        leftPanel.add(fLabel, BorderLayout.CENTER);
        mainPanel.add(leftPanel);

        String email = model.getEmail();

        if (email.matches("^[a-zA-Z]+(\\.[a-zA-Z]+)?@admin\\.com$")) {
            JLabel flabel2 = new JLabel("Admin");
            flabel2.setHorizontalAlignment(SwingConstants.CENTER);
            Font buttonFont1 = new Font(Font.MONOSPACED, Font.BOLD, 30);
            flabel2.setFont(buttonFont1);
            flabel2.setForeground(mainColor);

            leftPanel.add(flabel2, BorderLayout.SOUTH);
        }

        // Right panel with a 2x2 grid
        JPanel rightPanel = new JPanel(new GridLayout(2, 2, 5, 5)); // Adjusted gaps
        rightPanel.setBackground(darkColor);

        JButton accountButton = new JButton("Your Account");
        JButton favoritesButton = new JButton();  // Create a button without text

        // Load the image from the package "images"
        // Load the image from the package "images"
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/image5.jpeg"));

// Get the image from the icon
        Image image = imageIcon.getImage();

// Resize the image to 30x10 pixels (adjust the dimensions as needed)
        Image scaledImage = image.getScaledInstance(230, 230, Image.SCALE_SMOOTH);

// Create a new ImageIcon with the scaled image
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

// Set the scaled image as the icon for the button
        favoritesButton.setIcon(scaledImageIcon);


        JButton moviesButton = new JButton("Movies");
        JButton cinemasButton = new JButton("Cinemas");

        // Styling for buttons
        Font buttonFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
        accountButton.setFont(buttonFont);
        favoritesButton.setFont(buttonFont);
        moviesButton.setFont(buttonFont);
        cinemasButton.setFont(buttonFont);

        accountButton.setPreferredSize(new Dimension(150, 40));
        favoritesButton.setPreferredSize(new Dimension(150, 40));
        moviesButton.setPreferredSize(new Dimension(150, 40));
        cinemasButton.setPreferredSize(new Dimension(150, 40));

        accountButton.setBackground(mainColor);
        favoritesButton.setBackground(mainColor);
        moviesButton.setBackground(mainColor);
        cinemasButton.setBackground(mainColor);

        accountButton.setForeground(Color.WHITE);
        favoritesButton.setForeground(Color.WHITE);
        moviesButton.setForeground(Color.WHITE);
        cinemasButton.setForeground(Color.WHITE);

        rightPanel.add(accountButton);
        rightPanel.add(favoritesButton);
        rightPanel.add(moviesButton);
        rightPanel.add(cinemasButton);

        mainPanel.add(rightPanel);

        //--------------------------------------
        accountButton.addActionListener(e -> controller.accountButtonClicked());
        moviesButton.addActionListener(e -> controller.moviesButtonClicked());
        cinemasButton.addActionListener(e-> controller.cinemasButtonClicked());
        //--------------------------------------

        frame.setContentPane(mainPanel);
        frame.setPreferredSize(new Dimension(800, 500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setVisible(boolean isVisible) {
        frame.setVisible(isVisible);
    }
}
