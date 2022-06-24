package Server;

import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerConfig {
    public static final CollectionManager manager = new CollectionManager();
    public static final Logger logger = LogManager.getLogManager().getLogger("");
    protected static final int SERVER_PORT = 8100;
    protected static final Scanner scanner = new Scanner(System.in);
}
