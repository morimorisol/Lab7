package Server;

import Common.entities.CollectionManager;

import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerConfig {
    protected static final CollectionManager manager = new CollectionManager();
    protected static final Logger logger = LogManager.getLogManager().getLogger("");
    protected static final int SERVER_PORT = 8100;
    protected static final Scanner scanner = new Scanner(System.in);
}
