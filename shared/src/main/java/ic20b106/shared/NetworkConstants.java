package ic20b106.shared;

public final class NetworkConstants {
    public static final String HOST = "localhost";
    public static final String RMI_HOST = "rmi://" + HOST;
    public static final int PORT = 2910;
    public static final int RMI_PORT = 1509;

    private NetworkConstants() {
        throw new UnsupportedOperationException();
    }
}
