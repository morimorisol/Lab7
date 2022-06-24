package Server.databaseHandlers;

import Common.entities.SpaceMarine;
import Server.ServerConfig;
import javafx.util.Pair;
import java.sql.*;
import java.time.LocalDate;

public class DatabaseWorker {
    private final Connection connection;
    private final Pair<String, String> userData;

    public DatabaseWorker(Connection dbConnection, Pair<String, String> userData) {
        this.connection = dbConnection;
        this.userData = userData;
    }

    public synchronized boolean addSpaceMarine(SpaceMarine SpaceMarine) {
        try {
            PreparedStatement statement = connection.prepareStatement(Statements.addSpaceMarine.getStatement());
            SpaceMarine.setId((Integer) generateId());
            statement.setLong(1, SpaceMarine.getId());
            statement.setString(2, SpaceMarine.getName());
            statement.setDate(3, Date.valueOf(LocalDate.now()));
            statement.setLong(4, SpaceMarine.getHealth());
            statement.setString(5, SpaceMarine.getAchivements());
            statement.setInt(6, SpaceMarine.getCoordinates().getX());
            statement.setFloat(7, SpaceMarine.getCoordinates().getY());
            if (SpaceMarine.getAchivements() == null) {
                statement.setString(8,null);
            } else {
                statement.setString(8, String.valueOf(SpaceMarine.getAchivements()));
            }
            statement.setString(9, String.valueOf(SpaceMarine.getWeaponType()));
            statement.setString(10, SpaceMarine.getChapter().getName());
            statement.setString(11, SpaceMarine.getChapter().getParentLegion());
            statement.setLong(12, SpaceMarine.getChapter().getMarinesCount());
            statement.setString(13, userData.getKey());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean removeById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(Statements.removeById.getStatement());
            statement.setString(1, userData.getKey());
            statement.setLong(2, id);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            ServerConfig.logger.info("SQL problem with removing by id");
            return false;
        }
    }

    public synchronized boolean updateById(SpaceMarine SpaceMarine, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(Statements.updateById.getStatement());
            statement.setString(1, SpaceMarine.getName());
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            statement.setLong(3, SpaceMarine.getHealth());
            statement.setString(4, SpaceMarine.getAchivements());
            statement.setInt(5, SpaceMarine.getCoordinates().getX());
            statement.setFloat(6, SpaceMarine.getCoordinates().getY());
            if (SpaceMarine.getAchivements() == null) {
                statement.setString(7,null);
            } else {
                statement.setString(7, String.valueOf(SpaceMarine.getAchivements()));
            }
            statement.setString(8, String.valueOf(SpaceMarine.getWeaponType()));
            statement.setString(9, SpaceMarine.getChapter().getName());
            statement.setString(10, SpaceMarine.getChapter().getParentLegion());
            statement.setLong(11, SpaceMarine.getChapter().getMarinesCount());
            statement.setString(12, userData.getKey());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            ServerConfig.logger.info("SQL problem with removing by id");
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean clear() {
        try {
            PreparedStatement statement = connection.prepareStatement(Statements.clearByUser.getStatement());
            statement.setString(1, userData.getKey());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            ServerConfig.logger.info("SQL problem with removing by id");
            e.printStackTrace();
            return false;
        }
    }

    private synchronized Comparable<Integer> generateId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Statements.getNextId.getStatement());
            if (resultSet.next()) {
                return resultSet.getInt("nextval");
            }
            return null;
        } catch (SQLException e) {
            ServerConfig.logger.info("SQL problem with generating id");
            return null;
        }
    }

    public String getUsername() {
        return userData.getKey();
    }
}
