package Client;

public final class Client {

    private static final int PORT = 45677;

    public static void main(String[] args) {
        ClientManager ClientManager = new ClientManager(PORT);
        ClientManager.startClient();
    }
}
