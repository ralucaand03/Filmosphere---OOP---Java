package controller;

import model.SignUpModel;
import repository.UsersRepository;
import view.MainPageView;
import view.YourAccountView;

import java.sql.SQLException;

public class YourAccountController {
    YourAccountView view;
    SignUpModel model;
    public YourAccountController(SignUpModel model){
        this.model =model;

    }
    public YourAccountView getView() {
        return view;
    }

    public void setView(YourAccountView view) {
        this.view = view;
    }

    public SignUpModel getModel() {
        return model;
    }

    public void setModel(SignUpModel model) {
        this.model = model;
    }
    public void goBackButtonClicked() throws SQLException {


        MainPageController controller = new MainPageController(model);
        MainPageView mainPageView = new MainPageView(controller, model);
        controller.setView(mainPageView);
        view.setVisible(false);  // Hide the LogInView
        //mainPageView.setVisible(true);  // Show the MainPageView
    }

    public void updateName(String newName) {
        // Update the name in the model
        model.setName(newName);
        UsersRepository repository = new UsersRepository();
        // Update the name in the database through the repository
        repository.updateName(model.getUsername(), newName);

        // Update the user's name label in the view
        if (view != null) {
            view.updateUserNameLabel(newName);
        }
    }
}
