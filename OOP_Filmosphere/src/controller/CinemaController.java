package controller;

import model.CinemaModel;
import model.MoviesModel;
import model.SignUpModel;
import repository.CinemaRepository;
import repository.MoviesRepository;
import repository.UsersRepository;
import view.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaController {
    private List<CinemaModel> model;
    private CinemaView view;
    SignUpModel signUpModel;
    private CinemaRepository repository = new CinemaRepository();


    public CinemaController(SignUpModel signUpModel) {
        this.signUpModel = signUpModel;
    }

    public void handleMoviesButtonClick(String cinemaName) {
        // Retrieve the list of movies for the selected cinema from the repository
        List<MoviesModel> moviesList = repository.getMoviesForCinema(cinemaName);

        // Create a new CinemaMoviesView with the cinema name and moviesList
        CinemaMoviesView cinemaMoviesView = new CinemaMoviesView(cinemaName, this);
        cinemaMoviesView.setVisible(true);
    }


    public CinemaController(CinemaRepository repository) {
        this.repository = repository;
    }

    public List<String> getCinemaNames() {
        return repository.getCinemaNames();
    }
   public String getCinemaLocation(String cinemaName){
        return repository.getCinemaLocation(cinemaName);
   }
    public List<MoviesModel> getMoviesForCinema(String cinemaName) {
        return repository.getMoviesForCinema(cinemaName);
    }

    public List<String> getSelectedCinemas(int[] selectedRows) {
        List<String> selectedCinemaNames = new ArrayList<>();

        for (int selectedRow : selectedRows) {
            String cinemaName = (String) view.getCinemaTableModel().getValueAt(selectedRow, 0);
            selectedCinemaNames.add(cinemaName);
        }

        return selectedCinemaNames;
    }

    public CinemaView getView() {
        return view;
    }

    public void setView(CinemaView view) {
        this.view = view;
    }
    public void goBackButtonClicked() throws SQLException {


        MainPageController controller = new MainPageController(signUpModel);
        MainPageView mainPageView = new MainPageView(controller, signUpModel);
        controller.setView(mainPageView);
        view.setVisible(false);  // Hide the LogInView
        //mainPageView.setVisible(true);  // Show the MainPageView
    }

    public List<String>  handleSortByName() throws SQLException {
        return repository.getCinemasOrderedByNames();
       // List<CinemaModel> sortedCinemas = repository.getCinemasOrderedByName();
        //view.updateCinemaTable(sortedCinemas);
    }

    public List<String>  handleSortByLocation() throws SQLException {
        return repository.getCinemasOrderedByLocations();
        //view.updateCinemaTable(sortedCinemas);
    }
    public void handleInsertCinema(String cinemaName, String location) throws SQLException {
        // Create a new CinemaModel with the provided data
        CinemaModel newCinema = new CinemaModel();
        newCinema.setCinemaName(cinemaName);
        newCinema.setLocation(location);

        try {
            repository.insertCinema(newCinema);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Refresh the cinema table with the updated data
        List<CinemaModel> cinemasList = repository.getAllCinemas();
        view.updateCinemaTable(cinemasList);
    }


    // In your CinemaController class

    public void handleDeleteCinema(List<String> selectedCinemaNames) {
        // Check if there are selected cinemas
        if (!selectedCinemaNames.isEmpty()) {
            // Prompt the user with a confirmation dialog
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected cinemas?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // User clicked "Yes", proceed with deletion
                try {
                    for (String cinemaName : selectedCinemaNames) {
                        repository.deleteCinema(cinemaName);
                    }

                    // Refresh the cinema table with the updated data
                    List<CinemaModel> cinemasList = repository.getAllCinemas();
                    view.updateCinemaTable(cinemasList);
                } catch (SQLException e) {
                    // Handle the exception, e.g., show an error message to the user
                    e.printStackTrace();
                }
            }
            // If the user clicked "No" or closed the dialog, do nothing
        }
    }

}
