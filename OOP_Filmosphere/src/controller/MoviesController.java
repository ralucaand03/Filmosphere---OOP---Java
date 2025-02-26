package controller;

import model.MoviesModel;
import model.SignUpModel;
import repository.MoviesRepository;
import view.MainPageView;
import view.MoviesView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoviesController {
    private final SignUpModel model;
    private List<MoviesModel> modelList;

    MoviesView view;

    MoviesRepository repository=new MoviesRepository();

    public MoviesController(SignUpModel model) {
        {
            this.model = model;
        }

    }

    public List<MoviesModel> handleGenreSelection(String selectedGenre) {
        List<MoviesModel> moviesList;

        // Fetch movies based on the selected genre
        if ("ALL".equals(selectedGenre)) {
            moviesList = repository.getAllMovies();
        } else {
            moviesList = repository.getMoviesByGenre(selectedGenre);
        }

        return moviesList;

    }

    public MoviesView getView() {
        return view;
    }
    public List<MoviesModel> handleSearch(String searchQuery) {
        List<MoviesModel> searchResult = repository.searchMoviesByTitle(searchQuery);
        return searchResult;
    }

    public List<MoviesModel> handleGetMoviesOrderedByTitle(String genre) throws SQLException {
        List<MoviesModel> orderedMovies = repository.getMoviesOrderedByTitle(genre);
        return orderedMovies;
    }
    public List<MoviesModel> handleGetMoviesOrderedByTitle() {
        try {
            return repository.getMoviesOrderedByTitle();
        } catch (SQLException e) {
            // Handle the exception (e.g., log it or show an error message)
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public List<MoviesModel> handleGetMoviesOrderedByReleaseDate() {
        try {
            return repository.getMoviesOrderedByReleaseDate();
        } catch (SQLException e) {
            // Handle the exception appropriately, e.g., log the error
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    public void goBackButtonClicked() throws SQLException {


        MainPageController controller = new MainPageController(model);
        MainPageView mainPageView = new MainPageView(controller, model);
        controller.setView(mainPageView);
        view.setVisible(false);  // Hide the LogInView
        //mainPageView.setVisible(true);  // Show the MainPageView
    }
    public void setView(MoviesView view) {
        this.view = view;
    }

    // In your MoviesController class

    public void handleInsertMovie(String title, int duration, Date releaseDate, String description, String posterUrl, int genre_id) throws SQLException {
        // Create a new MoviesModel with the provided data
        MoviesModel newMovie = new MoviesModel();
        newMovie.setTitle(title);
        newMovie.setDuration(duration);
        newMovie.setRelease_date(releaseDate);
        newMovie.setDescription(description);
        newMovie.setPosterUrl(posterUrl);

        try {
            repository.insertMovie(newMovie,genre_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Refresh the movie table with the updated data
        List<MoviesModel> moviesList = repository.getAllMovies();
        view.updateMovieTable(moviesList);
    }

    public void handleDeleteMovie(List<String> selectedTitles) {
        // Check if there are selected movies
        if (!selectedTitles.isEmpty()) {
            // Prompt the user with a confirmation dialog
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected movies?", "Confirmation", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // User clicked "Yes", proceed with deletion
                try {
                    for (String title : selectedTitles) {
                        repository.deleteMovie(title);
                    }

                    // Refresh the movie table with the updated data
                    List<MoviesModel> moviesList = repository.getAllMovies();
                    view.updateMovieTable(moviesList);
                } catch (SQLException e) {
                    // Handle the exception, e.g., show an error message to the user
                    e.printStackTrace();
                }
            }
            // If the user clicked "No" or closed the dialog, do nothing
        }
    }

}
