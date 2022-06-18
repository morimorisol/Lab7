package Client;

import Client.commandDispatcher.LineSplitter;
import Client.dataController.CommandSender;
import Client.dataController.ResponseReceiver;
import Common.handlers.TextFormatter;
import Common.requestSystem.Response;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.*;


public final class Client {

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    private static final int PORT = 8100;
    private static final int SLEEP_TIME = 500;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static Selector selector;

    public static void main(String[] args) {
        TextFormatter.printMessage("Enter hostname: ");
        String hostname = "localhost";//SCANNER.nextLine();
        InetSocketAddress hostAddress = new InetSocketAddress(hostname, PORT);

        try {
            selector = Selector.open();

            SocketChannel client = SocketChannel.open(hostAddress);
            TextFormatter.printMessage("Connected!");
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_WRITE);
            startSelectorLoop(client, SCANNER);
        } catch (ConnectException e) {
            TextFormatter.printErrorMessage("Server with this host is temporarily unavailable. Try again later");
            main(args);
        } catch (StreamCorruptedException e) {
            TextFormatter.printErrorMessage("Disconnected.");
        } catch (ClassNotFoundException e) {
            TextFormatter.printErrorMessage("Trying to serialize non-serializable object");
        } catch (InterruptedException e) {
            TextFormatter.printErrorMessage("Thread was interrupt while sleeping. Restart client");
        } catch (UnresolvedAddressException e) {
            TextFormatter.printErrorMessage("Server with this host not found. Try again");
            main(args);
        } catch (IOException e) {
            TextFormatter.printErrorMessage("Server invalid or closed. Try to connect again");
            main(args);
        }
    }

    private static void startSelectorLoop(SocketChannel channel, Scanner sc) throws IOException, ClassNotFoundException, InterruptedException {
        while (true) {
            selector.select();
            if (!startIteratorLoop(channel, sc)) {
                break;
            }
        }
    }

    private static boolean startIteratorLoop(SocketChannel channel, Scanner sc) throws IOException, ClassNotFoundException, InterruptedException {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isReadable()) {
                ResponseReceiver responseReceiver = new ResponseReceiver(channel, key, selector);
                Response response = responseReceiver.receive();
                TextFormatter.printInfoMessage(response.getMessage());
                if (response.getSpaceMarines() != null) {
                    TextFormatter.printMessage(response.getSpaceMarines().toString());
                }
            } else if (key.isWritable()) {
                TextFormatter.printInfoMessage("Enter command (to check available commands type \"help\"): ");
                try {
                    String input = sc.nextLine();
                    List<String> splittedLine = LineSplitter.smartSplit(input);
                    if (splittedLine.get(0).equalsIgnoreCase("execute_script") && splittedLine.size() == 2) {
                        ScriptReader scriptReader = new ScriptReader(input);
                        startSelectorLoop(channel, new Scanner(scriptReader.getPath()));
                        scriptReader.stopScriptReading();
                    }
                    try {
                        CommandSender commandSender = new CommandSender(channel, input, selector);
                        commandSender.send();
                    } catch (NullPointerException | IOException e) {
                        TextFormatter.printErrorMessage(e.getMessage());
                    }
                } catch (NoSuchElementException e) {
                    TextFormatter.printErrorMessage(e.getMessage());
                    return false;
                } catch (IllegalArgumentException e) {
                    TextFormatter.printErrorMessage(e.getMessage());
                }
            }
        }
        return true;
    }
}
