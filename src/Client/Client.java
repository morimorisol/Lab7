package Client;

public final class Client {

    private static final int PORT = 45846;
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        ClientManager ClientManager = new ClientManager(PORT);
        ClientManager.startClient();
    }
}
