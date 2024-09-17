package server.managers;


import lib.spaceMarine.*;
import lib.utility.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import static javax.management.remote.JMXConnectorFactory.connect;

public class SQLManager {
    private SQLconnector sqlconnector;

    public SQLManager(SQLconnector sqlconnector) {
        this.sqlconnector = sqlconnector;
    }

    //createSpaceMarine
    public void createSpaceMarine(PriorityQueue<SpaceMarine> collection, User user) {
        try (Connection connection = sqlconnector.getConnectToSQL()) {
            String selectSQL = "SELECT COUNT(*) FROM spacemarine WHERE USER_NAME=? AND NAME=?" ;
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO spacemarine (USER_NAME, NAME, COORDINATE_X, COORDINATE_Y, CREATION_DATE, HEALTH, HEART_COUNT, CATEGORY, MELEE_WEAPON, CHAPTER_NAME, CHAPTER_WORLD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (SpaceMarine spaceMarine : collection) {
                try (PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {
                    selectStatement.setString(1, user.getLogin());
                    selectStatement.setString(2, spaceMarine.getName());

                    ResultSet resultSet = selectStatement.executeQuery();
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        // Если количество найденных записей больше 0, существует дублирующая запись, пропускаем
                        continue;
                    }
                }
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, spaceMarine.getName());
                preparedStatement.setLong(3, spaceMarine.getCoordinates().getX());
                preparedStatement.setFloat(4, spaceMarine.getCoordinates().getY());
                preparedStatement.setTimestamp(5, Timestamp.valueOf(spaceMarine.getCreationDate()));
                preparedStatement.setLong(6, spaceMarine.getHealth());
                preparedStatement.setInt(7, spaceMarine.getHeartCount());
                String category = spaceMarine.getCategory() != null ? spaceMarine.getCategory().getCategory() : "null" ;
                preparedStatement.setString(8, category);
                String meleeWeapon = spaceMarine.getMeleeWeapon() != null ? spaceMarine.getMeleeWeapon().getWeapon() : "null" ;
                preparedStatement.setString(9, meleeWeapon);
                String chapterName = spaceMarine.getChapter() != null && spaceMarine.getChapter().getName() != null ? spaceMarine.getChapter().getName() : "null" ;
                preparedStatement.setString(10, chapterName);
                String chapterWorld = spaceMarine.getChapter() != null && spaceMarine.getChapter().getWorld() != null ? spaceMarine.getChapter().getWorld() : "null" ;
                preparedStatement.setString(11, chapterWorld);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("Не удалось добавить SpaceMarine в SQL");
        }
    }

    //deleteSpaceMarine
    public int deleteSpaceMarine(long id) {
        String SQL = "DELETE FROM SpaceMarine WHERE id = ?";
        long affectedrows = 0;
        try (Connection conn = sqlconnector.getConnectToSQL()) {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, id);
            affectedrows = pstmt.executeUpdate();
            return (int) affectedrows;
        } catch (SQLException ex) {
            System.out.println("Не удалось удалить SpaceMarine из SQL");
        }
        return 0;
    }
    public void removeLower(long id) {
    String SQL = "DELETE FROM SpaceMarine WHERE id < ?";
    try (Connection conn = sqlconnector.getConnectToSQL()) {
        PreparedStatement pstmt = conn.prepareStatement(SQL);
        pstmt.setLong(1, id);
        pstmt.executeUpdate();
    } catch (SQLException ex) {
        System.out.println("Не удалось удалить SpaceMarine из SQL: " + ex.getMessage());
    }
}
    public void clearSpaceMarines() {
    String SQL = "DELETE FROM SpaceMarine";
    try (Connection conn = sqlconnector.getConnectToSQL()) {
        PreparedStatement pstmt = conn.prepareStatement(SQL);
        pstmt.executeUpdate();
    } catch (SQLException ex) {
        System.out.println("Не удалось удалить SpaceMarine из SQL: " + ex.getMessage());
    }
}

    //updateSpaceMarine
    public void updateSpaceMarine(SpaceMarine spaceMarine, long id) {
        String SQL = "UPDATE spacemarine SET NAME=?, COORDINATE_X=?, COORDINATE_Y=?, HEALTH=?, HEART_COUNT=?, CATEGORY=?, MELEE_WEAPON=?, CHAPTER_NAME=?, CHAPTER_WORLD=? WHERE ID=?";
        try (Connection conn = sqlconnector.getConnectToSQL()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setString(1, spaceMarine.getName());
            preparedStatement.setLong(2, spaceMarine.getCoordinates().getX());
            preparedStatement.setFloat(3, spaceMarine.getCoordinates().getY());
            preparedStatement.setLong(4, spaceMarine.getHealth());
            preparedStatement.setInt(5, spaceMarine.getHeartCount());
            String category = spaceMarine.getCategory() != null ? spaceMarine.getCategory().getCategory() : "null";
            preparedStatement.setString(6, category);
            String meleeWeapon = spaceMarine.getMeleeWeapon() != null ? spaceMarine.getMeleeWeapon().getWeapon() : "null";
            preparedStatement.setString(7, meleeWeapon);
            String chapterName = spaceMarine.getChapter() != null && spaceMarine.getChapter().getName() != null ? spaceMarine.getChapter().getName() : "null";
            preparedStatement.setString(8, chapterName);
            String chapterWorld = spaceMarine.getChapter() != null && spaceMarine.getChapter().getWorld() != null ? spaceMarine.getChapter().getWorld() : "null";
            preparedStatement.setString(9, chapterWorld);
            preparedStatement.setLong(10, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Не удалось обновить элемент в SQL");
        }
    }

    //select
    public List<SpaceMarine> select() {
        List<SpaceMarine> spaceMarines = new LinkedList<>();
        try {
            String SQL = "SELECT * FROM SpaceMarine;";
            try (Connection conn = sqlconnector.getConnectToSQL()) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Coordinates coordinates = new Coordinates(rs.getLong("COORDINATE_X"), rs.getFloat("COORDINATE_Y"));
                    spaceMarines.add(new SpaceMarine(rs.getLong("ID"), rs.getString("NAME"), coordinates, rs.getDate("CREATION_DATE").toLocalDate().atStartOfDay(), rs.getLong("HEALTH"), rs.getInt("HEART_COUNT"), AstartesCategory.getValue(rs.getString("CATEGORY")), MeleeWeapon.getValue(rs.getString("MELEE_WEAPON")), new Chapter(rs.getString("CHAPTER_WORLD"), rs.getString("CHAPTER_NAME"))));
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            return spaceMarines;
        } catch (Exception e) {
            return null;
        }
        }

}


