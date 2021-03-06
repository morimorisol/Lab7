package Server.databaseHandlers;


import Server.ServerConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private final String url;
    private final String username;
    private final String password;

    public DatabaseConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getNewConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            ServerConfig.logger.info("DB driver not found!");
            return null;
        }
        return DriverManager.getConnection(url, username, password);
    }
}
