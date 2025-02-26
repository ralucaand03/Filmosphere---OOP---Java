package main;

import controller.LogInController;
import controller.MainPageController;
import controller.MoviesController;
import controller.SignUpController;
import model.MoviesModel;
import view.*;

public class Main {
    public static void main(String[] args) {

        LogInController logInController= new LogInController();
        LogInView logIn= new LogInView(logInController);
        logInController.setView(logIn);

        //SignUpController signUpController= new SignUpController();
        //SignUpView singUp = new SignUpView(signUpController);
       // MainPageController mainPageController = new MainPageController();
       // MainPageView mainPageView = new MainPageView(mainPageController);
        //YourAccountView yourAccountView = new YourAccountView();
        //MoviesModel model = new MoviesModel();
        //MoviesController moviesController = new MoviesController(model);
       // MoviesView moviesView = new MoviesView(moviesController,model);
    }
}