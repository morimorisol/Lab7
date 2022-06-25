package Server;

import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerConfig {
    public static final CollectionManager MANAGER = new CollectionManager();
    public static final Logger logger = LogManager.getLogManager().getLogger("");
    protected static final int SERVER_PORT = 8080;
    protected static final Scanner scanner = new Scanner(System.in);
}
