package repository;

import model.CinemaModel;
import model.MoviesModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CinemaRepository {
    public List<CinemaModel> getCinemas() {
        List<CinemaModel> cinemas = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            String selectQuery = "SELECT * FROM CinemaMoviesView";
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CinemaModel cinemaModel = new CinemaModel();
                cinemaModel.setCinemaId(resultSet.getInt("cinema_id"));
                cinemaModel.setCinemaName(resultSet.getString("cinema_name"));
                cinemaModel.setLocation(resultSet.getString("location"));
                cinemaModel.setContactNumber(resultSet.getString("contact_number"));
                cinemaModel.setShowtimeId(resultSet.getInt("showtime_id"));
                cinemaModel.setMovieId(resultSet.getInt("movie_id"));
                cinemaModel.setMovieTitle(resultSet.getString("movie_title"));
                cinemaModel.setDuration(resultSet.getInt("duration"));
                cinemaModel.setReleaseDate(resultSet.getDate("release_date"));
                cinemaModel.setDescription(resultSet.getString("description"));
                cinemaModel.setPosterUrl(resultSet.getString("poster_url"));
                cinemaModel.setStartTime(resultSet.getDate("start_time"));
                cinemaModel.setEndTime(resultSet.getDate("end_time"));
                // cinemaModel.setAvailableSeats(resultSet.getInt("available_seats"));

                cinemas.add(cinemaModel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return cinemas;
    }

    public List<MoviesModel> getMoviesForCinema(String cinemaName) {
        List<MoviesModel> moviesList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT * FROM CinemaMoviesView WHERE cinema_name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cinemaName);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MoviesModel cinemaModel = new MoviesModel();
                //   cinemaModel.setCinemaId(resultSet.getInt("cinema_id"));
                // cinemaModel.setCinemaName(resultSet.getString("cinema_name"));
                // cinemaModel.setLocation(resultSet.getString("location"));
                //cinemaModel.setContactNumber(resultSet.getString("contact_number"));
                //  cinemaModel.setShowtimeId(resultSet.getInt("showtime_id"));
                cinemaModel.setMovie_id(resultSet.getInt("movie_id"));
                cinemaModel.setTitle(resultSet.getString("movie_title"));
                cinemaModel.setDuration(resultSet.getInt("duration"));
                cinemaModel.setRelease_date(resultSet.getDate("release_date"));
                cinemaModel.setDescription(resultSet.getString("description"));
                cinemaModel.setPosterUrl(resultSet.getString("poster_url"));
                // cinemaModel.setStartTime(resultSet.getDate("start_time"));
                //cinemaModel.setEndTime(resultSet.getDate("end_time"));
                // cinemaModel.setAvailableSeats(resultSet.getInt("available_seats"));

                moviesList.add(cinemaModel);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return moviesList;
    }

    public MoviesModel getMovieDetailsByTitle(String movieTitle) {
        MoviesModel movie = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT * FROM Movies WHERE title = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, movieTitle);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                movie = new MoviesModel();
                movie.setMovie_id(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRelease_date(resultSet.getDate("release_date"));
                movie.setDescription(resultSet.getString("description"));
                movie.setPosterUrl(resultSet.getString("poster_url"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return movie;
    }

    public List<String> getCinemaNames() {
        List<String> cinemaNames = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            String selectQuery = "SELECT DISTINCT cinema_name FROM CinemaMoviesView";
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cinemaNames.add(resultSet.getString("cinema_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return cinemaNames;
    }

    public String getCinemaLocation(String cinemaName) {
        String location = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            String selectQuery = "SELECT location FROM Cinemas WHERE name = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, cinemaName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                location = resultSet.getString("location");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return location;
    }


    private CinemaModel createCinemaModelFromResultSet(ResultSet resultSet) throws SQLException {
        CinemaModel cinemaModel = new CinemaModel();
        cinemaModel.setCinemaName(resultSet.getString("name"));
        cinemaModel.setLocation(resultSet.getString("location"));
        return cinemaModel;
    }


    public List<String> getCinemasOrderedByNames() throws SQLException {
        List<String> cinemaNames = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            String selectQuery = "SELECT DISTINCT cinema_name FROM CinemaMoviesView ORDER BY cinema_name";
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cinemaNames.add(resultSet.getString("cinema_name"));
            }
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return cinemaNames;
    }
    public List<String> getCinemasOrderedByLocations() throws SQLException {
        List<String> cinemaNames = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            String selectQuery = "SELECT DISTINCT cinema_name, location FROM CinemaMoviesView ORDER BY location";
            preparedStatement = connection.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cinemaNames.add(resultSet.getString("cinema_name"));
            }
        } finally {
            // Close resources in the finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return cinemaNames;
    }
    public void insertCinema(CinemaModel newCinema) throws SQLException {
        try (
                Connection connection = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO cinemas (name, location) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            // Set parameters for the new cinema
            preparedStatement.setString(1, newCinema.getCinemaName());
            preparedStatement.setString(2, newCinema.getLocation());

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Retrieve the generated keys (if any)
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedCinemaId = generatedKeys.getInt(1);
                    newCinema.setCinemaId(generatedCinemaId);
                }
            }
        } // Connection and PreparedStatement are automatically closed due to try-with-resources
    }

    public List<CinemaModel> getAllCinemas() throws SQLException {
        List<CinemaModel> cinemas = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cinemas");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Populate CinemaModel and add to the list
                CinemaModel cinemaModel = createCinemaModelFromResultSet(resultSet);
                cinemas.add(cinemaModel);
            }

        } // Resources are automatically closed here

        return cinemas;
    }

    public void deleteCinema(String cinemaName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();

            // Retrieve cinema_id based on the cinemaName
            int cinemaId = getCinemaIdByName(cinemaName);

            if (cinemaId != -1) {
                // Delete from Cinema_Movies table
                String deleteMoviesQuery = "DELETE FROM showtimes WHERE cinema_id = ?";
                preparedStatement = connection.prepareStatement(deleteMoviesQuery);
                preparedStatement.setInt(1, cinemaId);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                // Delete from Cinema_Reviews table
//                String deleteReviewsQuery = "DELETE FROM cinema_reviews WHERE cinema_id = ?";
//                preparedStatement = connection.prepareStatement(deleteReviewsQuery);
//                preparedStatement.setInt(1, cinemaId);
//                preparedStatement.executeUpdate();
//                preparedStatement.close();

                // Finally, delete from Cinemas table
                String deleteCinemaQuery = "DELETE FROM cinemas WHERE cinema_id = ?";
                preparedStatement = connection.prepareStatement(deleteCinemaQuery);
                preparedStatement.setInt(1, cinemaId);
                preparedStatement.executeUpdate();
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public int getCinemaIdByName(String cinemaName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT cinema_id FROM Cinemas WHERE name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cinemaName);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("cinema_id");
            }

            // If cinema not found, return -1 or throw an exception based on your design
            return -1;
        } finally {
            // Close resources in the reverse order of their creation to avoid potential issues
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

}



