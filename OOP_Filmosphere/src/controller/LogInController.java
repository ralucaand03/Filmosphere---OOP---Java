package controller;

import interfaces.UserInterface;
import model.SignUpModel;
import repository.UsersRepository;
import view.LogInView;
import view.MainPageView;
import view.SignUpView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LogInController implements UserInterface {
    private LogInView view;
    private UsersRepository repository = new UsersRepository();

    public LogInController() {

    }

    @Override
    public void signupButtonClicked() {
        SignUpController controller = new SignUpController();
        SignUpView signUpView = new SignUpView(controller);
        controller.setView(signUpView);
        view.setVisible(false);
    }

    @Override
    public void loginButtonClicked() throws SQLException {
        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();

        String result = repository.verifyUser(username, password);
        SignUpModel model = repository.getPerson(username);
        if (result.equals("User verified successfully.")) {
            MainPageController controller = new MainPageController(model);
            MainPageView mainPageView = new MainPageView(controller, model);
            controller.setView(mainPageView);
            view.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(view, "User not found", "Login error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public LogInView getView() {
        return view;
    }

    public void setView(LogInView view) {
        this.view = view;
    }
}
