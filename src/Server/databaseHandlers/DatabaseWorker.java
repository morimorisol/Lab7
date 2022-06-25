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

    public synchronized boolean addSpaceMarine(SpaceMarine spaceMarine) {
        try {
            PreparedStatement statement = connection.prepareStatement(Statements.addSpaceMarine.getStatement());
            spaceMarine.setId(generateId());
            statement.setLong(1, spaceMarine.getId());
            statement.setString(2, spaceMarine.getName());
            statement.setDate(3, Date.valueOf(LocalDate.now()));
            statement.setLong(4, spaceMarine.getHealth());
            statement.setString(5, spaceMarine.getAchivements());
            statement.setInt(6, spaceMarine.getCoordinates().getX());
            statement.setFloat(7, spaceMarine.getCoordinates().getY());
            if (spaceMarine.getAstartesCategory() == null) {
                statement.setString(8,null);
            } else {
                statement.setString(8, String.valueOf(spaceMarine.getAstartesCategory()));
            }
            statement.setString(9, spaceMarine.getChapter().getName());
            statement.setString(10, spaceMarine.getChapter().getName());
            statement.setString(11, spaceMarine.getChapter().getParentLegion());
            statement.setString(12, String.valueOf(spaceMarine.getChapter().getMarinesCount()));
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

    public synchronized boolean updateById(SpaceMarine spaceMarine, long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(Statements.updateById.getStatement());
            statement.setString(1, spaceMarine.getName());
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            statement.setLong(3, spaceMarine.getHealth());
            statement.setString(4, spaceMarine.getAchivements());
            statement.setInt(5, spaceMarine.getCoordinates().getX());
            statement.setFloat(6, spaceMarine.getCoordinates().getY());
            statement.setString(7, spaceMarine.getAstartesCategory() == null ? null : String.valueOf(spaceMarine.getAstartesCategory()));
            statement.setString(8, spaceMarine.getChapter().getName());
            statement.setString(8, spaceMarine.getChapter().getParentLegion());
            statement.setLong(9, spaceMarine.getChapter().getMarinesCount());
            statement.setString(10, String.valueOf(spaceMarine.getWeaponType()));
            statement.setLong(11, id);
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

    private synchronized int generateId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Statements.getNextId.getStatement());
            if (resultSet.next()) {
                return resultSet.getInt("nextval");
            }
            return Integer.parseInt(null);
        } catch (SQLException e) {
            ServerConfig.logger.info("SQL problem with generating id");
            return Integer.parseInt(null);
        }
    }

    public String getUsername() {
        return userData.getKey();
    }
}
