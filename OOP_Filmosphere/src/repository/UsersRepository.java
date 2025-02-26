package repository;

import model.SignUpModel;

import java.sql.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;
public class UsersRepository {

    public String insertUser(String username, String password, String email, String phone_number, String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;
        try {
            connection = ConnectionManager.getConnection();

            String query = "INSERT INTO users (username, password, email, phone_number, name) VALUES (?, ?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone_number);
            preparedStatement.setString(5, name);

            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Error at DB level: " + sqlException.getMessage();
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
        return rowsAffected + " Rows Affected. Success! Connection Closed!";
    }

    public String getUsers() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT * FROM users;";
            preparedStatement = connection.prepareStatement(query);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Display function to show the ResultSet
                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    String phoneNumber = resultSet.getString("phone_number");
                    String name = resultSet.getString("name");

                    System.out.println(userId + " " + username + " " + password + " " + email + " " + phoneNumber + " " + name);
                }
            }

        } catch (SQLException sqlException) {
            if ("23505".equals(sqlException.getSQLState())) {
                // Unique constraint violation (duplicate key)
                if (sqlException.getMessage().contains("unique constraint \"users_email_key\"")) {
                    return "Error: Email already exists. Please choose a different email.";
                } else if (sqlException.getMessage().contains("unique constraint \"users_phone_key\"")) {
                    return "Error: Phone already exists. Please choose a different phone number.";
                } else if (sqlException.getMessage().contains("unique constraint \"users_username_key\"")) {
                    return "Error: Username already exists. Please choose a different username.";
                } else {
                    return "Error: Duplicate key violation. Please check your data.";
                }
            } else {
                sqlException.printStackTrace();
                return "Error at DB level";
            }
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

        return null;
    }

    public boolean isUsernameExists(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ConnectionManager.getConnection();

            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } finally {
            // Close resources (if needed)
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return false;
    }

    public boolean isPhoneExists(String phoneNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE phone_number = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } finally {
            // Close resources (if needed)
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return false;
    }

    public String verifyUser(String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManager.getConnection();
            String verifyQuery = "SELECT username, password FROM users WHERE username = ?";


            preparedStatement = connection.prepareStatement(verifyQuery);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("Password");
                if (storedPassword.equals(password)) {
                    return "User verified successfully.";
                } else {
                    return "Error: Incorrect password.";
                }
            } else {
                return "Error: User not found.";
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Error at DB level";
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public SignUpModel getPerson(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SignUpModel model = null; // Initializing model to null

        try {
            connection = ConnectionManager.getConnection();
            String selectQuery = "SELECT * FROM users WHERE username = ?";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // If there are rows, create a new SignUpModel
                model = new SignUpModel();

                // Retrieve user information from the result set
                String storedUsername = resultSet.getString("username");
                String storedPassword = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String name = resultSet.getString("name");

                // Set properties of the SignUpModel
                model.setEmail(email);
                model.setPhoneNumber(phoneNumber);
                model.setPassword(storedPassword);
                model.setUsername(storedUsername);
                model.setName(name);
            }


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            // Handle the exception or log the error
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

        return model;
    }
    public String updateName(String username, String newName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer rowsAffected = 0;

        try {
            connection = ConnectionManager.getConnection();

            String query = "UPDATE users SET name = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, username);

            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return "Error at DB level: " + sqlException.getMessage();
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

        return rowsAffected + " Rows Affected. Success! Connection Closed!";
    }


}