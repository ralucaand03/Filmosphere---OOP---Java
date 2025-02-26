package view;

import controller.YourAccountController;
import model.Colors;
import model.SignUpModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

public class YourAccountView {
    private JFrame frame;
    private YourAccountController controller;
    private SignUpModel model;
    private JLabel userNameLabel; // Declare the userNameLabel as a class variable for later updates

    public YourAccountView(YourAccountController controller, SignUpModel model) {
        frame = new JFrame("Your Account");

        this.controller = controller;
        this.model = model;

        // Accessing the colors
        Color mainColor = Colors.MAIN_COLOR.getColor();
        Color darkColor = Colors.DARK_COLOR.getColor();
        Color lightColor = Colors.LIGHT_COLOR.getColor();


        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(darkColor);
        leftPanel.setLayout(new GridLayout(4, 1));

        // "Your account" label
        JLabel yourAccountLabel = new JLabel("Your Account");
        yourAccountLabel.setForeground(mainColor);
        yourAccountLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
        yourAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(yourAccountLabel);

        // Image
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("/images/image2.png"));

            // Resize the image
            int newWidth = 100; // Set your desired width
            int newHeight = 100; // Set your desired height
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
            leftPanel.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if signUpModel is not null before accessing its properties
        if (model != null) {
            // Example: Accessing the name property
            String name = model.getName();

            // User's name label
            userNameLabel = new JLabel(name);
            userNameLabel.setForeground(mainColor);
            userNameLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
            userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            leftPanel.add(userNameLabel);

            // Label text
            JLabel fLabel = new JLabel("\uD835\uDD3D\uD835\uDD5A\uD835\uDD5D\uD835\uDD5E\uD835\uDD60\uD835\uDD64\uD835\uDD61\uD835\uDD59\uD835\uDD56\uD835\uDD63\uD835\uDD56");
            fLabel.setHorizontalAlignment(SwingConstants.CENTER);
            fLabel.setFont(fLabel.getFont().deriveFont(Font.PLAIN, 20));
            fLabel.setForeground(lightColor);
            leftPanel.add(fLabel);
        } else {
            // Handle the case where signUpModel is null
            // For example, show an error message or set default values
            JLabel errorLabel = new JLabel("Error: SignUpModel is null");
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            leftPanel.add(errorLabel);
        }

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(mainColor);

// Use BoxLayout with Y_AXIS alignment for vertical layout
        BoxLayout boxLayout = new BoxLayout(rightPanel, BoxLayout.Y_AXIS);
        rightPanel.setLayout(boxLayout);

// Labels and user information
        String[] labels = {
                "Username: " + model.getUsername(),
                "Phone Number: " + model.getPhoneNumber(),
                "Email Address: " + model.getEmail()
        };

        EmptyBorder emptyBorder = new EmptyBorder(10, 20, 10, 20);

        for (String labelText : labels) {
            // Create a panel for each label to set the alignment to the left
            JPanel labelPanel = new JPanel(new BorderLayout());
            labelPanel.setBackground(mainColor);

            JLabel label = new JLabel(labelText);
            label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setBorder(emptyBorder);
            label.setVerticalAlignment(SwingConstants.CENTER);

            labelPanel.add(label, BorderLayout.WEST);

            // Add some vertical space between labels
            rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adjust the value to control the spacing

            rightPanel.add(labelPanel);
        }


        // Create "Update Name" button with smaller height
        JButton updateNameButton = new JButton("Update Name");
        updateNameButton.setBackground(mainColor);
        updateNameButton.addActionListener(e -> {
            // Show an option panel to update the name

            String newName = JOptionPane.showInputDialog(frame, "Enter your new name:");
            if (newName != null && !newName.isEmpty()) {
                // Update the user's name label
                userNameLabel.setText(newName);
                // Update the model (if needed)
                model.setName(newName);
                controller.updateName(newName);
            }
        });
        updateNameButton.setPreferredSize(new Dimension(150, 30)); // Adjust the dimensions as needed

        // Create "Go Back" button with smaller height
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setBackground(mainColor);
        goBackButton.addActionListener(e -> {
            try {
                controller.goBackButtonClicked();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        goBackButton.setPreferredSize(new Dimension(150, 30)); // Adjust the dimensions as needed

        // Create a panel for the buttons at the bottom
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2)); // Use GridLayout for buttonPanel
        buttonPanel.setBackground(mainColor);
        buttonPanel.add(updateNameButton);
        buttonPanel.add(goBackButton);

        // Add the buttonPanel to the SOUTH position of rightPanel
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set up the frame
        frame.setLayout(new GridLayout(1, 2));
        frame.add(leftPanel);
        frame.add(rightPanel);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setVisible(boolean isVisible) {
        frame.setVisible(isVisible);
    }
    public void updateUserNameLabel(String newName) {
        userNameLabel.setText(newName);
    }
}
