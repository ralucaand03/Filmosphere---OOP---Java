package view;

import controller.LogInController;
import model.Colors;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LogInView extends Component {
    JFrame frame = new JFrame("User Login");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    public LogInView(LogInController controller) {
        // Accessing the colors
        Color mainColor = Colors.MAIN_COLOR.getColor();
        Color darkColor = Colors.DARK_COLOR.getColor();
        Color lightColor = Colors.LIGHT_COLOR.getColor();

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(darkColor);

        JPanel panel = new JPanel(new GridLayout(6, 1, 1, 2)); // Increased rows, increased vertical gap
        panel.setBackground(darkColor);

        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        welcomeLabel.setForeground(mainColor);

        JLabel fLabel = new JLabel("\uD835\uDD3D\uD835\uDD5A\uD835\uDD5D\uD835\uDD5E\uD835\uDD60\uD835\uDD64\uD835\uDD61\uD835\uDD59\uD835\uDD56\uD835\uDD63\uD835\uDD56"); // Modified this line
        fLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fLabel.setFont(fLabel.getFont().deriveFont(Font.PLAIN, 40)); // Changed font size
        fLabel.setForeground(lightColor); // Set label color to white


        JLabel titleLabel = new JLabel("USER LOG IN");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25)); // Changed font size
        titleLabel.setForeground(mainColor); // Set label color to white

        JPanel credentialPanel = new JPanel(new GridLayout(2, 2, 5, 15)); // Increased vertical gap
        credentialPanel.setBackground(mainColor);
        credentialPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Increased insets

        // Increased font size for labels and set label colors to white
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18)); // Increased font size
        usernameLabel.setForeground(darkColor); // Set label color to white


        usernameField.setColumns(23); // Increased width of username field

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18)); // Increased font size
        passwordLabel.setForeground(darkColor); // Set label color to white


        passwordField.setColumns(23); // Increased width of password field

        credentialPanel.add(usernameLabel);
        credentialPanel.add(usernameField);
        credentialPanel.add(passwordLabel);
        credentialPanel.add(passwordField);

        JButton loginButton = new JButton("Log In");
        loginButton.setPreferredSize(new Dimension(350, 30));
        loginButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(350, 30));
        signUpButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        // Set button background color to A1C084
        loginButton.setBackground(lightColor);
        signUpButton.setBackground(lightColor);

        //--------------------------------------
        signUpButton.addActionListener(e -> controller.signupButtonClicked());
        loginButton.addActionListener(e -> {
            try {
                controller.loginButtonClicked();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        //--------------------------------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Add space at the top

        buttonPanel.setBackground(darkColor);
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);
        //credentialPanel.add(buttonPanel);
        panel.add(welcomeLabel);
        panel.add(fLabel);
        panel.add(titleLabel);
        panel.add(credentialPanel);
        panel.add(buttonPanel);

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        frame.setContentPane(mainPanel);
        frame.setPreferredSize(new Dimension(450, 700)); // Adjusted height
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setVisible(boolean isVisible){
        frame.setVisible(isVisible);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

}
