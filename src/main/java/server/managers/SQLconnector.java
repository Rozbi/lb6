package server.managers;

import java.sql.*;

public class SQLconnector {
    public Connection getConnectToSQL() {
        try  {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "408729");
        } catch (SQLException e) {
          System.out.print("не удалось подключиться к PostgreSQL");
        }
        return null;
    }
}
