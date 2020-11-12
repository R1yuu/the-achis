import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author t
 * Link: https://stackoverflow.com/a/10131449
 */
public class ThreadedGameServer {
    static final int PORT = 2910;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                assert serverSocket != null;
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new GameThread(socket).start();
        }
    }
}
