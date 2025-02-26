package view;

import controller.SignUpController;
import model.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SignUpView extends Component {
        JFrame frame = new JFrame("Create Account");
        JTextField nameField = new JTextField(20);
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        public SignUpView(SignUpController controller) {

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Accessing the colors
                Color mainColor = Colors.MAIN_COLOR.getColor();
                Color darkColor = Colors.DARK_COLOR.getColor();
                Color lightColor = Colors.LIGHT_COLOR.getColor();

                JPanel mainPanel = new JPanel(new BorderLayout(5, 2)); // Reduced space between components
                mainPanel.setBackground(mainColor);

                // Panel for the "Create Account" label

                JPanel upPanel= new JPanel(new GridLayout(2, 1, 1, 2));
                upPanel.setBackground(mainColor);
              //  upPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel createAccountLabel = new JLabel("Create Account");
                createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
                createAccountLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 35));
                createAccountLabel.setForeground(Color.WHITE);
                upPanel.add(createAccountLabel);
                //upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));
                //


                JLabel fLabel = new JLabel("\uD835\uDD3D\uD835\uDD5A\uD835\uDD5D\uD835\uDD5E\uD835\uDD60\uD835\uDD64\uD835\uDD61\uD835\uDD59\uD835\uDD56\uD835\uDD63\uD835\uDD56");
                fLabel.setHorizontalAlignment(SwingConstants.CENTER);
                fLabel.setFont(fLabel.getFont().deriveFont(Font.PLAIN, 30));
                fLabel.setForeground(darkColor);
                upPanel.add(fLabel);
                mainPanel.add(upPanel, BorderLayout.PAGE_START);

                // Panel for the form fields
                JPanel formPanel = new JPanel(new GridBagLayout());
                formPanel.setBackground(mainColor);
                formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50)); // Adjusted top and bottom insets

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(10, 0, 10, 15); // Increased vertical insets

                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
                nameLabel.setForeground(Color.WHITE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                formPanel.add(nameLabel, gbc);


                gbc.gridx = 1;
                formPanel.add(nameField, gbc);

                JLabel usernameLabel = new JLabel("Username:");
                usernameLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
                usernameLabel.setForeground(Color.WHITE);
                gbc.gridx = 0;
                gbc.gridy = 1;
                formPanel.add(usernameLabel, gbc);


                gbc.gridx = 1;
                formPanel.add(usernameField, gbc);

                JLabel passwordLabel = new JLabel("Password:");
                passwordLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
                passwordLabel.setForeground(Color.WHITE);
                gbc.gridx = 0;
                gbc.gridy = 2;
                formPanel.add(passwordLabel, gbc);

                gbc.gridx = 1;
                formPanel.add(passwordField, gbc);

                JLabel emailLabel = new JLabel("Email:");
                emailLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
                emailLabel.setForeground(Color.WHITE);
                gbc.gridx = 0;
                gbc.gridy = 3;
                formPanel.add(emailLabel, gbc);

                gbc.gridx = 1;
                formPanel.add(emailField, gbc);

                JLabel phoneLabel = new JLabel("Phone Number:");
                phoneLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
                phoneLabel.setForeground(Color.WHITE);
                gbc.gridx = 0;
                gbc.gridy = 4;
                formPanel.add(phoneLabel, gbc);

                gbc.gridx = 1;
                formPanel.add(phoneField, gbc);

                JButton signUpButton = new JButton("Sign Up");

                signUpButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
                signUpButton.setPreferredSize(new Dimension(180, 30));
                signUpButton.setBackground(lightColor);
                signUpButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // Handle sign-up button click event
                        }
                });
                //--------------------------------------
                signUpButton.addActionListener(e -> {
                    try {
                        controller.signupButtonClicked();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                //--------------------------------------
                gbc.gridx = 1;
                gbc.gridy = 5; // Incremented from 4 to 5
                gbc.insets = new Insets(15, 0, 0, 0);
                formPanel.add(signUpButton, gbc);

                mainPanel.add(formPanel, BorderLayout.CENTER);

                frame.setContentPane(mainPanel);
                frame.setPreferredSize(new Dimension(450, 550)); // Adjusted height
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public JTextField getNameField() {
                return nameField;
        }

        public void setNameField(JTextField nameField) {
                this.nameField = nameField;
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

        public JTextField getEmailField() {
                return emailField;
        }

        public void setEmailField(JTextField emailField) {
                this.emailField = emailField;
        }

        public JTextField getPhoneField() {
                return phoneField;
        }

        public void setPhoneField(JTextField phoneField) {
                this.phoneField = phoneField;
        }

        public void setVisible(boolean isVisible){
                frame.setVisible(isVisible);
        }
}
