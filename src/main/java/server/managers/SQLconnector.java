package server.managers;

import java.sql.*;

public class SQLconnector {
    public Connection getConnectToSQL() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://hostname:port/dbname", "username", "408729")) {
            return connection;
        } catch (SQLException e) {
            System.out.print("не удалось подключиться к PostgreSQL");
        }
        return null;
    }
}
