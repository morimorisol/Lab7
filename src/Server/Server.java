package Server;

import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws SQLException {
        ServerManager serverManager = new ServerManager();
        serverManager.run();
    }
}
