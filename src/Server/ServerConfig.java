package Server;

import Common.entities.CollectionManager;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerConfig {
    protected static final CollectionManager manager = new CollectionManager();
    protected static final Logger logger = LogManager.getLogManager().getLogger("");
    protected static final File starting = new File(System.getProperty("user.dir")); // Get current user directory
    protected static final int SERVER_PORT = 8100;
    protected static final Scanner scanner = new Scanner(System.in);
}
