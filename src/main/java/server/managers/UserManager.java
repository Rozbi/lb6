package server.managers;


import lib.utility.Message;
import server.exeptions.InvalidInputException;
import server.utility.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.LinkedList;
import java.util.Objects;

public class UserManager {
    private SQLconnector sqlconnector;
    private ServerSendingManager sendingManager;
    private InetSocketAddress adress;

    public UserManager(SQLconnector sqlconnector, ServerSendingManager sendingManager) {
        this.sqlconnector = sqlconnector;
        this.sendingManager=sendingManager;
    }
    //add user
    public void addUser(User user) {
        try (Connection connection = sqlconnector.getConnectToSQL()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Space_Marine_Users (LOGIN, PASSWORD) VALUES (?, ?)");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
        } catch (SQLException e) {
            System.out.println("Ошибка добавления нового клиента");
        }
    }


    public LinkedList<User> select() {
        LinkedList<User> linkedListUsers = new LinkedList<>();
        try {
            String SQL = "SELECT * FROM SpaceMarineUser;";
            try (Connection conn = sqlconnector.getConnectToSQL()) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL);
                ResultSet rs = preparedStatement.executeQuery(SQL);
                while (rs.next()) {
                    linkedListUsers.add(new User(rs.getInt("ID"), rs.getString("LOGIN"), rs.getString("PASSWORD")));
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


    public boolean checkUser(String userLogin, String userPassword, InetSocketAddress address) {
        this.adress=adress;
        try {
            String SQL = "SELECT * FROM SpaceMarineUser WHERE LOGIN = ?";
            try (Connection conn = sqlconnector.getConnectToSQL()) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL);
                preparedStatement.setString(1, userLogin);
                ResultSet rs = preparedStatement.executeQuery(SQL);
                String password = rs.getString("PASSWORD");
                return Objects.equals(password, userPassword);
            } catch (SQLException e) {
                sendingManager.sendMessage(new Message("ErrorLogin", "Пользователь не найден", adress));
            }
        }catch (RuntimeException | InvalidInputException | IOException e) {
        }
        return false;
    }
    public long getUserId(String userLogin, String userPassword) {
        this.adress = adress;
        try {
            String SQL = "SELECT * FROM SpaceMarineUser WHERE LOGIN = ? and PASSWORD = ?";
            try (Connection conn = sqlconnector.getConnectToSQL()) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL);
                preparedStatement.setString(1, userLogin);
                preparedStatement.setString(2, userPassword);
                ResultSet rs = preparedStatement.executeQuery(SQL);
                return rs.getLong("USER_ID");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
        }
        return 0;
    }
    }
