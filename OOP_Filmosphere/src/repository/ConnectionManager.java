package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/Filmosphere";
        String dbUsername = "postgres";
        String dbPassword = "12345678";

        return DriverManager.getConnection(url, dbUsername, dbPassword);
    }
}
