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
        sqlStatement.executeUpdate("CREATE SEQUENCE IF NOT EXISTS ids START 3;");

        createDragonsTable();
        createUsersTable();
    }

    private void createDragonsTable() throws SQLException {
        sqlStatement.executeUpdate("CREATE TABLE IF NOT EXISTS s335103Dragons (" +
                "id bigint PRIMARY KEY DEFAULT (nextval('ids')), " +
                "name varchar(70) NOT NULL CHECK (name<>'')," +
                "creationDate date DEFAULT (current_date)," +
                "age int NOT NULL CHECK (age > 0)," +
                "wingspan int NOT NULL CHECK (wingspan > 0)," +
                "xCoord int NOT NULL CHECK (xCoord < 603), " +
                "yCoord real NOT NULL," +
                "color varchar(6) " +
                "CHECK (color='GREEN' OR " +
                "color='BLUE' OR " +
                "color='YELLOW' OR " +
                "color='ORANGE' OR " +
                "color='WHITE' OR " +
                "color IS NULL)," +
                "caveDepth real," +
                "caveNumOfTreasures int NOT NULL CHECK (caveNumOfTreasures > 0)," +
                "dragonCharacter varchar(12) NOT NULL " +
                "CHECK (dragonCharacter='WISE' OR " +
                "dragonCharacter='GOOD' OR " +
                "dragonCharacter='CHAOTIC' OR " +
                "dragonCharacter='CHAOTIC_EVIL' OR " +
                "dragonCharacter='FICKLE')," +
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
            try{
            SpaceMarine dragon = new SpaceMarine();
            dragon.setId(resultSet.getInt(1));
            dragon.setName(resultSet.getString(2));
            dragon.setCreationDate(resultSet.getDate(3));
            dragon.setHealth(resultSet.getLong(4));
            dragon.setAchievements(resultSet.getString(5));
            dragon.setCoordinates(new Coordinates(resultSet.getInt(6), resultSet.getFloat(7)));
            dragon.setAstartesCategory(resultSet.getString(8) != null ?
                    AstartesCategory.valueOf(resultSet.getString(8)) : null);
                dragon.setWeaponType(resultSet.getString(11) != null ?
                        WeaponType.BOLT_RIFLE.valueOf(resultSet.getString(11)) : null);
            dragon.setChapter(new Chapter(resultSet.getString(9), resultSet.getString(9), resultSet.getLong(10)));
            dragon.setAuthorName(resultSet.getString(12));
            ServerConfig.MANAGER.addSpaceMarine(dragon);
            } catch (Exception e) {
                ServerConfig.logger.info("Dragon with id = " + resultSet.getLong(1) + " is incorrect");
                System.exit(1);
            }
        }
        ServerConfig.logger.info("Collection successfully filled");
    }

    public Connection getConnection() {
        return this.connection;
    }
}
