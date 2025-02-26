package interfaces;

import controller.MainPageController;
import controller.SignUpController;
import model.SignUpModel;

import javax.swing.*;
import java.sql.SQLException;

public interface UserInterface {
    void signupButtonClicked();

    void loginButtonClicked() throws SQLException;
}
