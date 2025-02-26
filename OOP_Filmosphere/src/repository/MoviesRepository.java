package repository;

import model.MoviesModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesRepository {
    private Connection connection;



    // Retrieve all movies from the 'movies' table
    public List<MoviesModel> getAllMovies() {
        List<MoviesModel> movies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT * FROM movies";
             preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MoviesModel movie = new MoviesModel();
                movie.setMovie_id(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRelease_date(resultSet.getDate("release_date"));
                movie.setDescription(resultSet.getString("description"));
                movie.setPosterUrl(resultSet.getString("poster_url"));

                movies.add(movie);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    // Retrieve movies by genre from the 'movies' table
    public List<MoviesModel> getMoviesByGenre(String genre) {
        List<MoviesModel> movies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT m.* FROM movies m " +
                    "JOIN movie_genres mg ON m.movie_id = mg.movie_id " +
                    "JOIN genres g ON mg.genre_id = g.genre_id " +
                    "WHERE g.genre_name = ?";
             preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, genre);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MoviesModel movie = new MoviesModel();
                movie.setMovie_id(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRelease_date(resultSet.getDate("release_date"));
                movie.setDescription(resultSet.getString("description"));
                movie.setPosterUrl(resultSet.getString("poster_url"));

                movies.add(movie);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }
    public List<MoviesModel> searchMoviesByTitle(String title) {
        List<MoviesModel> movies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT * FROM movies WHERE title LIKE ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + title + "%"); // Use LIKE for a partial match
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MoviesModel movie = new MoviesModel();
                movie.setMovie_id(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRelease_date(resultSet.getDate("release_date"));
                movie.setDescription(resultSet.getString("description"));
                movie.setPosterUrl(resultSet.getString("poster_url"));

                movies.add(movie);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return movies;
    }
    public void insertMovie(MoviesModel newMovie, int genreId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();

            String insertMovieQuery = "INSERT INTO movies (title, duration, release_date, description, poster_url) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertMovieQuery, Statement.RETURN_GENERATED_KEYS);

            // Set parameters for the new movie
            preparedStatement.setString(1, newMovie.getTitle());
            preparedStatement.setInt(2, newMovie.getDuration());
            preparedStatement.setDate(3, new java.sql.Date(newMovie.getRelease_date().getTime()));
            preparedStatement.setString(4, newMovie.getDescription());
            preparedStatement.setString(5, newMovie.getPosterUrl());

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Retrieve the generated keys (if any)
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int generatedMovieId = generatedKeys.getInt(1);
                newMovie.setMovie_id(generatedMovieId);

                // Insert into movie_genres table
                String insertGenreQuery = "INSERT INTO movie_genres (movie_id, genre_id) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(insertGenreQuery);

                preparedStatement.setInt(1, generatedMovieId);
                preparedStatement.setInt(2, genreId);

                // Execute the query to insert into movie_genres
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


    private int getMovieIdByTitle(String title) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT movie_id FROM movies WHERE title = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("movie_id");
            }
        } finally {
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

        // Return -1 if movie with the given title is not found
        return -1;
    }
    public void deleteMovie(String title) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();

            // Retrieve movie_id based on the title
            int movieId = getMovieIdByTitle(title);

            if (movieId != -1) {
                // Delete from Movie_Genres table
                String deleteGenresQuery = "DELETE FROM Movie_Genres WHERE movie_id = ?";
                preparedStatement = connection.prepareStatement(deleteGenresQuery);
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                // Delete from Movie_Crew table
                String deleteCrewQuery = "DELETE FROM Movie_Crew WHERE movie_id = ?";
                preparedStatement = connection.prepareStatement(deleteCrewQuery);
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                // Delete from Movie_TicketTypes table
                String deleteTicketTypesQuery = "DELETE FROM Movie_TicketTypes WHERE movie_id = ?";
                preparedStatement = connection.prepareStatement(deleteTicketTypesQuery);
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                // Delete from FavoritesMovies table
                String deleteFavoritesQuery = "DELETE FROM FavoritesMovies WHERE movie_id = ?";
                preparedStatement = connection.prepareStatement(deleteFavoritesQuery);
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                // Delete from Reviews table
                String deleteReviewsQuery = "DELETE FROM Reviews WHERE movie_id = ?";
                preparedStatement = connection.prepareStatement(deleteReviewsQuery);
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                // Delete from Showtimes table
                String deleteShowtimesQuery = "DELETE FROM Showtimes WHERE movie_id = ?";
                preparedStatement = connection.prepareStatement(deleteShowtimesQuery);
                preparedStatement.setInt(1, movieId);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                // Finally, delete from Movies table
                String deleteMovieQuery = "DELETE FROM Movies WHERE movie_id = ?";
                preparedStatement = connection.prepareStatement(deleteMovieQuery);
                preparedStatement.setInt(1, movieId);
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

    public List<MoviesModel> getMoviesOrderedByTitle(String genre) throws SQLException {
        List<MoviesModel> movies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();
            String query = "SELECT * FROM Movies " +
                    "JOIN movie_genres mg ON Movies.movie_id = mg.movie_id " +
                    "JOIN genres g ON mg.genre_id = g.genre_id " +
                    "WHERE g.genre_name = ? " +
                    "ORDER BY title";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, genre);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MoviesModel movie = new MoviesModel();
                movie.setMovie_id(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRelease_date(resultSet.getDate("release_date"));
                movie.setDescription(resultSet.getString("description"));
                movie.setPosterUrl(resultSet.getString("poster_url"));

                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return movies;
    }
    public List<MoviesModel> getMoviesOrderedByTitle() throws SQLException {
        List<MoviesModel> movies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();
            String query = "SELECT * FROM Movies " +
                    "ORDER BY title";
            preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MoviesModel movie = new MoviesModel();
                movie.setMovie_id(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRelease_date(resultSet.getDate("release_date"));
                movie.setDescription(resultSet.getString("description"));
                movie.setPosterUrl(resultSet.getString("poster_url"));

                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return movies;
    }

    public List<MoviesModel> getMoviesOrderedByReleaseDate() throws SQLException {
        List<MoviesModel> movies = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();
            String query = "SELECT * FROM Movies ORDER BY release_date";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MoviesModel movie = new MoviesModel();
                movie.setMovie_id(resultSet.getInt("movie_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDuration(resultSet.getInt("duration"));
                movie.setRelease_date(resultSet.getDate("release_date"));
                movie.setDescription(resultSet.getString("description"));
                movie.setPosterUrl(resultSet.getString("poster_url"));

                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in the finally block to ensure they are closed even if an exception occurs
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return movies;
    }

}
