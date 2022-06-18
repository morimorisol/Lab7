package Server;

import Common.commands.CommandAbstract;
import Common.handlers.HistorySaver;
import Common.requestSystem.Response;
import Common.requestSystem.Serializer;
import Server.fileHandlers.GSONReader;
import Server.fileHandlers.GSONWriter;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public final class Server {

    private static Selector selector;
    static File file;

    private Server() {
        throw new UnsupportedOperationException("Не может быть создан экземпляр класса");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        ConsoleThread consoleThread = new ConsoleThread();
        consoleThread.start();
        startServer(args);
        consoleThread.shutdown();
    }

    private static void startServer(String[] args) throws IOException {
        ServerConfig.logger.info("Сервер готов к работе");
        String collectionPath = System.getenv("labCollection");
        file = new File(collectionPath);
        fillCollectionFromFile(file);
        try {
            selector = Selector.open();
            ServerSocketChannel server = initChannel(selector);
            startSelectorLoop(server);
        } catch (IOException e) {
            ServerConfig.logger.info("Некоторые проблемы с IO. Попробуйте снова");
        } catch (ClassNotFoundException e) {
            ServerConfig.logger.info("Попробуйте сериализовать несериализованный объект");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ServerSocketChannel initChannel(Selector selector) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        ServerConfig.logger.info("Сокет готов к работе");
        server.socket().bind(new InetSocketAddress(ServerConfig.SERVER_PORT));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        return server;
    }

    private static void startSelectorLoop(ServerSocketChannel channel) throws IOException, ClassNotFoundException, InterruptedException {
        while (channel.isOpen()) {
            selector.select();
            startIteratorLoop(channel);
        }
    }

    private static void startIteratorLoop(ServerSocketChannel channel) throws IOException, ClassNotFoundException {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isAcceptable()) {
                SocketChannel socketChannel = channel.accept();
                ServerConfig.logger.info("Сервер соединен с " + socketChannel.getLocalAddress());
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            } else if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ServerConfig.logger.info("Клиент " + socketChannel.getLocalAddress() + " отправил сообщение");
                CommandAbstract command = IOController.getCommand(socketChannel);
                ServerConfig.logger.info("Сервер получил [" + command.getName() + "] команду");
                HistorySaver.addCommandInHistory(command);
                try {
                    Response response = IOController.buildResponse(command, ServerConfig.manager);
                    ByteBuffer buffer = Serializer.serializeResponse(response);
                    socketChannel.write(buffer);
                    ServerConfig.logger.info("Сервер написал ответ клиенту");
                } catch (Exception e) {
                    GSONWriter.write(file, ServerConfig.manager);
                    ServerConfig.logger.info("Клиент " + socketChannel.getLocalAddress() + " отсоединен. Коллекция успешно сохранена");
                    socketChannel.close();
                    break;
                }
            }
        }
    }

    private static void fillCollectionFromFile(File file) {
        GSONReader reader = new GSONReader();
        try {
            reader.read(file);
            ServerConfig.logger.info("Коллекция успешно заполнена");
        } catch (IOException e) {
            ServerConfig.logger.info("Файл не существует");
            System.exit(0);
        } catch (StreamException e) {
            ServerConfig.logger.info("Файл пустой");
            System.exit(0);
        } catch (NullPointerException | ConversionException e) {
            ServerConfig.logger.info("Данные некорректны");
            System.exit(0);
        }
    }
}
