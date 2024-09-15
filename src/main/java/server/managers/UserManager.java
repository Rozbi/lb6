package server.managers;


import lib.utility.User;

import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Objects;

public class UserManager {
    private SQLconnector sqlconnector;
    private ServerSendingManager sendingManager;
    private InetSocketAddress adress;

    public UserManager(SQLconnector sqlconnector, ServerSendingManager sendingManager) {
        this.sqlconnector = sqlconnector;
        this.sendingManager = sendingManager;
    }

    //add user
    public boolean addUser(String name, String password) {
        try (Connection connection = sqlconnector.getConnectToSQL()) {
            String SQL = "INSERT INTO USERS (USER_NAME, USER_PASSWORD) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password.trim());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

//    public void updateUser(User user) {
//        String SQL = "UPDATE Users USER_NAME=?, USER_PASSWORD=? WHERE ID = ?";
//        try (Connection conn = sqlconnector.getConnectToSQL()) {
//            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
//            preparedStatement.setString(1, user.getLogin());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.execute(SQL);
//        } catch (SQLException ex) {
//            System.out.println("Не удалось обновить элемент в SQL");
//        }
//    }


    public LinkedList<User> select() {
        LinkedList<User> linkedListUsers = new LinkedList<>();
        try {
            String SQL = "SELECT * FROM USERS;";
            try (Connection conn = sqlconnector.getConnectToSQL()) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL);
                ResultSet rs = preparedStatement.executeQuery(SQL);
                while (rs.next()) {
                    linkedListUsers.add(new User(rs.getInt("ID"), rs.getString("USER_NAME"), rs.getString("USER_PASSWORD")));
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            return null;
        } catch (Exception e) {
        }
        return linkedListUsers;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хэширования пароля: алгоритм SHA-1 не найден", e);
        }
    }

    public boolean checkUser(String userLogin, String userPassword) {
        try {
            String SQL = "SELECT * FROM USERS WHERE USER_NAME = ?";
            try (Connection conn = sqlconnector.getConnectToSQL()) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL);
                preparedStatement.setString(1, userLogin);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    String password = rs.getString("USER_PASSWORD");
                    return Objects.equals(password, userPassword);
                }
            } catch (SQLException e) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public long getUserId(String userLogin, String userPassword) {
        try {
            String SQL = "SELECT * FROM USERS WHERE USER_NAME = ? and USER_PASSWORD = ?";
            try (Connection conn = sqlconnector.getConnectToSQL()) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL);
                preparedStatement.setString(1, userLogin);
                preparedStatement.setString(2, userPassword);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return rs.getLong("ID");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
        }
        return 0;
    }
}
