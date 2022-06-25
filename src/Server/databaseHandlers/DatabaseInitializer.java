package Server.databaseHandlers;

import Common.entities.Chapter;
import Common.entities.Coordinates;
import Common.entities.SpaceMarine;
import Common.enums.AstartesCategory;
import Common.enums.WeaponType;
import Server.ServerConfig;

import java.sql.*;

public class DatabaseInitializer {
    private final Connection connection;
    private final Statement sqlStatement;

    public DatabaseInitializer(Connection connection) throws SQLException {
        this.connection = connection;
        this.sqlStatement = connection.createStatement();
    }

    public void initialize() throws SQLException {
        sqlStatement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 1;");

        createspaceMarinesTable();
        createUsersTable();
    }

    private void createspaceMarinesTable() throws SQLException {
        sqlStatement.executeUpdate("CREATE TABLE IF NOT EXISTS s337017SpaceMarines (" +
                "id bigint PRIMARY KEY DEFAULT (nextval('ids')), " +
                "name varchar(70) NOT NULL CHECK (name<>'')," +
                "creationDate date DEFAULT (current_date)," +
                "health int NOT NULL CHECK (health > 0)," +
                "achievements varchar(70) NOT NULL CHECK (achievements<>'')," +
                "xCoord int NOT NULL CHECK (xCoord < 603), " +
                "yCoord real NOT NULL," +
                "astartsetsCategory varchar(10) " +
                "CHECK (astartsetsCategory='LIBRARIAN' OR " +
                "astartsetsCategory='SCOUT' OR " +
                "astartsetsCategory='INCEPTOR' OR " +
                "astartsetsCategory='HELIX' OR " +
                "astartsetsCategory IS NULL)," +
                "chapterName varchar(70)," +
                "chapterMarinesCount int NOT NULL CHECK (chapterMarinesCount > 0)," +
                "weaponType varchar(12) NOT NULL " +
                "CHECK (weaponType='HEAVY_BOLTGUN' OR " +
                "weaponType='BOLT_RIFLE' OR " +
                "weaponType='COMBI_FLAMER' OR " +
                "weaponType='COMBI_PLASMA_GUN' OR " +
                "weaponType='GRENADE_LAUNCHER')," +
                "author varchar(75) NOT NULL);");
    }

    private void createUsersTable() throws SQLException {
        sqlStatement.executeUpdate("CREATE TABLE IF NOT EXISTS s335103Users (" +
                "name varchar(70) PRIMARY KEY NOT NULL," +
                "password varchar(255) NOT NULL);");
    }

    public void fillCollection(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Statements.getAll.getStatement());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            SpaceMarine spaceMarine = new SpaceMarine();
            spaceMarine.setId(resultSet.getInt(1));
            spaceMarine.setName(resultSet.getString(2));
            spaceMarine.setCreationDate(resultSet.getDate(3));
            spaceMarine.setHealth(resultSet.getInt(4));
            spaceMarine.setAchievements(resultSet.getString(5));
            spaceMarine.setCoordinates(new Coordinates(resultSet.getInt(6), resultSet.getFloat(7)));
            spaceMarine.setAstartesCategory(resultSet.getString(8) != null ?
                    AstartesCategory.valueOf(resultSet.getString(8)) : null);
            spaceMarine.setChapter(new Chapter(resultSet.getString(9), resultSet.getInt(10)));
            spaceMarine.setWeaponType(resultSet.getString(11) != null ?
                    WeaponType.valueOf(resultSet.getString(11)) : null);
            spaceMarine.setAuthorName(resultSet.getString(12));
            try{
                ServerConfig.MANAGER.addSpaceMarine(spaceMarine);
            } catch (Exception e){
                ServerConfig.logger.info("spaceMarine with id = " + resultSet.getLong(1) + " is incorrect");
                System.exit(1);
            }
        }
        ServerConfig.logger.info("Collection successfully filled");
    }

    public Connection getConnection() {
        return this.connection;
    }
}
