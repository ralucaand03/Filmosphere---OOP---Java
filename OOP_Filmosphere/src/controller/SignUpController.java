package controller;

import model.SignUpModel;
import repository.UsersRepository;
import view.LogInView;
import view.SignUpView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {
    public SignUpView view;
    public SignUpModel model;

    public SignUpController(){};

    UsersRepository repository= new UsersRepository();
    // Method to handle sign-up button click
    public void signupButtonClicked() throws SQLException{
        //loginModel= new LogInM
        model = new SignUpModel();

        String name = view.getNameField().getText();
        String phone = view.getPhoneField().getText();
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        String email = view.getEmailField().getText();
        //not null
        while (name.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(
                    view,
                    "All fields must be filled.",
                    "Incomplete Information",
                    JOptionPane.ERROR_MESSAGE
            );
            return; // Stop further processing
        }

        // Validate email format
        while (!emailValid(email)) {
            int option = JOptionPane.showOptionDialog(
                    view,
                    "Invalid email address. Please try again.",
                    "Retry",
                    JOptionPane.DEFAULT_OPTION, // Use DEFAULT_OPTION for OK button only
                    JOptionPane.ERROR_MESSAGE,  // Specify the message type
                    null,
                    new Object[]{"OK"}, // Use an array with a single "OK" button
                    "OK"  // Specify the default option
            );


            email = JOptionPane.showInputDialog(this.getView(), "Enter valid email:","Retry", JOptionPane.PLAIN_MESSAGE);
            if (email== null){
                return;
            }
        }
        model.setEmail(email);

        //check for unique username
        while (repository.isUsernameExists(username)) {
            JOptionPane.showMessageDialog(
                    this.getView(),
                    "Username already exists. Please choose a different username.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            username = JOptionPane.showInputDialog(this.getView(), "Enter valid username:","Retry", JOptionPane.PLAIN_MESSAGE);
            if (username== null){
                return;
            }

        }
        // Check for unique phone
        while (repository.isPhoneExists(phone) || !phoneValid(phone)) {
            JOptionPane.showMessageDialog(
                    view,
                    "Phone number not valid. Please choose a different phone number.",
                    "Phone Number Error",
                    JOptionPane.ERROR_MESSAGE
            );
            phone = JOptionPane.showInputDialog(this.getView(), "Enter valid phone number:","Retry", JOptionPane.PLAIN_MESSAGE);
            if (phone== null){
                return;
            }
        }

        model.setName(name);
        model.setPassword(password);
        model.setUsername(username);
        model.setPhoneNumber(phone);
        repository.insertUser(username,password,email,phone,name);
        LogInController controller = new LogInController();
        LogInView logInView = new LogInView(controller);
        controller.setView(logInView);
        view.setVisible(false);
    }

    public boolean emailValid(String email) {
        String ex = "^[a-zA-Z]+(\\. [a-zA-Z]+)?@(gmail|yahoo|admin)\\.com$";
        Pattern pattern = Pattern.compile(ex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean phoneValid(String phone) {
        String ex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(ex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public void setView(SignUpView view) {
        this.view = view;
    }
    public SignUpView getView() {
        return view;
    }

    public SignUpModel getModel() {
        return model;
    }

    public void setModel(SignUpModel model) {
        this.model = model;
    }
}
