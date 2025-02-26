package controller;

import model.SignUpModel;
import repository.UsersRepository;
import view.CinemaView;
import view.MainPageView;
import view.MoviesView;
import view.YourAccountView;

public class MainPageController {
    public MainPageView view;
    UsersRepository repository= new UsersRepository();
    SignUpModel model;
    public MainPageController(SignUpModel model){
        this.model =model;
    }

    public void  accountButtonClicked(){

        YourAccountController controller=new YourAccountController(model);
        YourAccountView yourAccountView= new YourAccountView(controller, model);
        controller.setView(yourAccountView);
        view.setVisible(false);
    }
    public void moviesButtonClicked(){
        MoviesController controller = new MoviesController(model);
        MoviesView moviesView = new MoviesView(controller,model);
        controller.setView(moviesView);
        view.setVisible(false);
    }
    public void cinemasButtonClicked(){
        CinemaController controller= new CinemaController(model);
        CinemaView cinemaView=new CinemaView(controller,model);
        controller.setView(cinemaView);
        view.setVisible(false);
    }




    public MainPageView getView() {
        return view;
    }

    public void setView(MainPageView view) {
        this.view = view;
    }



}