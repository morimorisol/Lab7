package Server;

import Common.requestSystem.requests.Request;
import Common.requestSystem.responses.Response;
import Server.databaseHandlers.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ServerManager {

    private static final String password = System.getenv("password");
    private static final DatabaseConnector CONNECTOR =
            new DatabaseConnector("jdbc:postgresql://localhost:5432/lab07",
                    "postgres", password);
    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private static boolean isWorking = true;
    private final ExecutorService readExecutor = Executors.newFixedThreadPool(THREADS);
    private final ExecutorService handleExecutor = Executors.newFixedThreadPool(THREADS);
    private final ExecutorService sendExecutor = Executors.newFixedThreadPool(THREADS);
    private volatile Connection dbConnection;
    private volatile Selector selector;
    private volatile Set<SelectionKey> workingKeys = Collections.synchronizedSet(new HashSet<>());

    public void run() throws SQLException {
     //   try {
            dbConnection = CONNECTOR.getNewConnection();
            DatabaseInitializer initializer = new DatabaseInitializer(dbConnection);
            initializer.initialize();
            initializer.fillCollection(dbConnection);
      /*  } catch (SQLException e) {
            ServerConfig.logger.info("Problems during SQL DB initialization");
            isWorking = false;
        } */
        ConsoleThread consoleThread = new ConsoleThread();
        if (isWorking) {
            consoleThread.start();
            startServer();
        }
        readExecutor.shutdown();
        handleExecutor.shutdown();
        sendExecutor.shutdown();
    }

    private void startServer() {
        ServerConfig.logger.info("Server started");
        try {
            selector = Selector.open();
            ServerSocketChannel server = initChannel(selector);
            startSelectorLoop(server);
        } catch (IOException e) {
            ServerConfig.logger.info("Some problems with IO. Try again");
            isWorking = false;
        } catch (ClassNotFoundException e) {
            ServerConfig.logger.info("Trying to serialize non-serializable object");
            isWorking = false;
        }
    }

    private void startSelectorLoop(ServerSocketChannel channel) throws IOException, ClassNotFoundException {
        while (channel.isOpen() && isWorking) {
            if (selector.select(1) != 0) {
                startIteratorLoop(channel);
            }
        }
    }

    private void startIteratorLoop(ServerSocketChannel channel) throws IOException {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isValid() && !workingKeys.contains(key)) {
                if (key.isAcceptable()) {
                    accept(channel);
                } else if (key.isReadable()) {
                    workingKeys.add(key);
                    ServerConfig.logger.info("Client " + ((SocketChannel) key.channel()).getLocalAddress() + " sent message");
                    Supplier<Request> requestReader = new RequestReader(key);
                    Consumer<Response> responseSender = new ResponseSender(key, workingKeys);
                    CompletableFuture
                            .supplyAsync(requestReader, readExecutor)
                            .thenApplyAsync(request -> {
                                if (request != null) {
                                    RequestHandler requestHandler = new RequestHandler(request, dbConnection);
                                    return requestHandler.handle(request);
                                } else return null;
                            }, handleExecutor)
                            .thenAcceptAsync(responseSender, sendExecutor);
                }
            }
        }
    }

    private void accept(ServerSocketChannel channel) throws IOException {
        SocketChannel socketChannel = channel.accept();
        ServerConfig.logger.info("Server get connection from " + socketChannel.getLocalAddress());
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private ServerSocketChannel initChannel(Selector selector) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        ServerConfig.logger.info("Socket opened");
        server.socket().bind(new InetSocketAddress(ServerConfig.SERVER_PORT));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        return server;
    }

    public static void closeServer() {
        isWorking = false;
    }
}
