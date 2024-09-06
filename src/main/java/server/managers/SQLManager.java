package server.managers;


import lib.spaceMarine.*;
import java.sql.*;
import java.util.LinkedList;

import static javax.management.remote.JMXConnectorFactory.connect;

public class SQLManager {
    private SQLconnector sqlconnector;

    public SQLManager(SQLconnector sqlconnector) {
        this.sqlconnector = sqlconnector;
    }

    //createSpaceMarine
    public void createSpaceMarine(SpaceMarine spaceMarine, Coordinates coordinates, Chapter chapter, MeleeWeapon meleeWeapon, AstartesCategory astartesCategory) {

        try (Connection connection = sqlconnector.getConnectToSQL()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO space_marine (NAME, COORDINATE_X, COORDINATE_Y, CREATION_DATE, HEALTH, HEART_COUNT, ASTARTES_CATEGORY, MELEE_WEAPON, CHAPTER_NAME, CHAPTER_WORLD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, spaceMarine.getName());
            preparedStatement.setLong(2, coordinates.getX());
            preparedStatement.setFloat(3, coordinates.getY());
            preparedStatement.setDate(4, java.sql.Date.valueOf(spaceMarine.getCreationDate().toLocalDate()));
            preparedStatement.setLong(5, spaceMarine.getHealth());
            preparedStatement.setInt(6, spaceMarine.getHeartCount());
            preparedStatement.setString(7, astartesCategory.getCategory());
            preparedStatement.setString(8, meleeWeapon.getWeapon());
            preparedStatement.setString(9, chapter.getName());
            preparedStatement.setString(10, chapter.getWorld());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Не удалось добавить SpaceMarine в SQL");
        }
    }

    //deleteSpaceMarine
    public int deleteSpaceMarine(long id) {
        String SQL = "DELETE FROM Space_Marine WHERE Space_Marine_id = ?";
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

    //updateSpaceMarine
    public void updateSpaceMarine(SpaceMarine spaceMarine) {
        String SQL = "UPDATE Space_Marine SET NAME=?, COORDINATE_X=?, COORDINATE_Y=?, HEALTH=?, HEART_COUNT=?, ASTARTES_CATEGORY=?, MELEE_WEAPON=?, CHAPTER_NAME=?, CHAPTER_WORLD=? WHERE Space_Marine_id = ? WHERE ID = ?";
        try (Connection conn = sqlconnector.getConnectToSQL()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.execute(SQL);
        } catch (SQLException ex) {
            System.out.println("Не удалось обновить элемент в SQL");
        }
    }

    //select
    public LinkedList<SpaceMarine> select() {
        LinkedList<SpaceMarine> spaceMarines = new LinkedList<>();
        try {
            String SQL = "SELECT * FROM SpaceMarine;";
            try (Connection conn = sqlconnector.getConnectToSQL()) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL);
                ResultSet rs = preparedStatement.executeQuery(SQL);
                while (rs.next()) {
                    Coordinates coordinates = new Coordinates(rs.getLong("COORDINATE_X"), rs.getFloat("COORDINATE_Y"));
//                    spaceMarines.add(new SpaceMarine(rs.getLong("ID"), rs.getString("NAME"), coordinates,  rs.getDate("CREATION_DATE").toLocalDate(), rs.getLong("HEALTH"), rs.getInt("HEART_COUNT"), rs.getString("ASTARTES_CATEGORY"),MeleeWeapon.getValue(rs.getString("MELEE_WEAPON")), new Chapter(rs.getString("CHAPTER_WORLD"), rs.getString("CHAPTER_NAME"))));
                }
                rs.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            return null;
        } catch (Exception e) {
        }
        return spaceMarines;
    }

}


