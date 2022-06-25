package Server;

import java.util.Scanner;

public class ConsoleThread extends Thread {
    private static final Scanner SCANNER = new Scanner(System.in);
    private volatile boolean running = true;

    @Override
    public void run() {
        ServerConfig.logger.info("Console thread is running");
        while (running) {
            String line = SCANNER.nextLine();
            if ("exit".equalsIgnoreCase(line)) {
                ServerConfig.logger.info("Server closed");
                ServerManager.closeServer();
                this.running = false;
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }
}
