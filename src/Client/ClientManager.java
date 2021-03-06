package Client;

import Client.commandDispatcher.LineSplitter;
import Client.dataController.RequestSender;
import Client.dataController.ResponseReceiver;
import Client.usersController.AuthorizationModule;
import Client.usersController.UserAcceptor;
import Common.TextFormatter;
import Common.requestSystem.requests.*;
import Common.requestSystem.responses.*;
import javafx.util.Pair;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.util.*;

public class ClientManager {

    private static boolean isWorking = true;
    private static Pair<String, String> loginData;
    private final int port;
    private final AuthorizationModule authorizationModule = new AuthorizationModule();
    private final Scanner SCANNER = new Scanner(System.in);
    private Selector selector;


    public ClientManager(int port) {
        this.port = port;
    }

    public void startClient() {
        TextFormatter.printMessage("Enter hostname: ");
        String hostname = SCANNER.nextLine();
        InetSocketAddress hostAddress = new InetSocketAddress(hostname, port);

        try {
            selector = Selector.open();
            SocketChannel client = SocketChannel.open(hostAddress);
            TextFormatter.printMessage("Connected!");
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_WRITE);
            startSelectorLoop(client, SCANNER);
            selector.close();
            client.close();
        } catch (ConnectException e) {
            TextFormatter.printErrorMessage("Server with this host is temporarily unavailable. Try again later");
            startClient();
        } catch (StreamCorruptedException e) {
            TextFormatter.printErrorMessage("Disconnected.");
        } catch (ClassNotFoundException e) {
            TextFormatter.printErrorMessage("Trying to serialize non-serializable object");
        } catch (InterruptedException e) {
            TextFormatter.printErrorMessage("Thread was interrupt while sleeping. Restart client");
        } catch (UnresolvedAddressException e) {
            TextFormatter.printErrorMessage("Server with this host not found. Try again");
            startClient();
        } catch (IOException e) {
            TextFormatter.printErrorMessage("Server invalid or closed. Try to connect again");
            startClient();
        } catch (Exception e) {
            TextFormatter.printErrorMessage("Unknown error. Try to connect again");
            startClient();
        }
    }

    private void startSelectorLoop(SocketChannel channel, Scanner sc) throws Exception {
        while (isWorking) {
            selector.select();
            if (!startIteratorLoop(channel, sc)) {
                break;
            }
        }
    }

    private boolean startIteratorLoop(SocketChannel channel, Scanner sc) throws Exception {
        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();

            if (key.isReadable()) {
                ResponseReceiver responseReceiver = new ResponseReceiver(channel, key, selector);
                Response response = responseReceiver.receive();
                if (response.getType().equals(ResponseType.SIGN_IN)) {
                    SignInResponse signInResponse = (SignInResponse) response;
                    UserAcceptor acceptor = new UserAcceptor(authorizationModule, signInResponse);
                    loginData = acceptor.acceptAuthorization();
                } else if (response.getType().equals(ResponseType.SIGN_UP)) {
                    SignUpResponse signUpResponse = (SignUpResponse) response;
                    UserAcceptor acceptor = new UserAcceptor(authorizationModule, signUpResponse);
                    loginData = acceptor.acceptRegistration();
                } else {
                    CommandResponse commandResponse = (CommandResponse) response;
                    TextFormatter.printInfoMessage(commandResponse.getMessage());
                    if (commandResponse.getSpaceMarines() != null) {
                        TextFormatter.printMessage(commandResponse.getSpaceMarines().toString());
                    }
                }
            } else if (key.isWritable()) {
                //authorization
                if (!authorizationModule.isAuthorizationDone()) {
                    Request authorizationRequest = authorizationModule.greet();
                    RequestSender requestSender = new RequestSender(channel, authorizationRequest, selector, loginData);
                    requestSender.send();
                    continue;
                }

                TextFormatter.printInfoMessage("Enter command (to check available commands type \"help\"): ");
                try {
                    String input = sc.nextLine();
                    List<String> splittedLine = LineSplitter.smartSplit(input);
                    if (splittedLine.get(0).equalsIgnoreCase("execute_script") && splittedLine.size() == 2) {
                        ScriptReader scriptReader = new ScriptReader(input);
                        startSelectorLoop(channel, new Scanner(scriptReader.getPath()));
                        scriptReader.stopScriptReading();
                    } else if (input.equals("exit")) {
                        channel.close();
                        isWorking = false;
                        TextFormatter.printMessage("Client closed");
                        break;
                    }
                    try {
                        RequestSender requestSender = new RequestSender(channel, input, selector, loginData);
                        requestSender.send();
                    } catch (NullPointerException | IOException e) {
                        TextFormatter.printErrorMessage("Channel closed or invalid");
                        isWorking = false;
                        break;
                    }
                } catch (NoSuchElementException e) {
                    TextFormatter.printErrorMessage(e.getMessage());
                    isWorking = false;
                    return false;
                } catch (IllegalArgumentException e) {
                    isWorking = false;
                    TextFormatter.printErrorMessage(e.getMessage());
                    break;
                }
            }
        }
        return true;
    }
}
