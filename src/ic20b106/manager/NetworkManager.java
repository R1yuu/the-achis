package ic20b106.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.Buffer;

public class NetworkManager {

    private NetworkManager() throws IOException {
        Socket socket = new Socket(HOST, PORT);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        this.printStream = new PrintStream(outputStream, true);
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public static NetworkManager getInstance() {
        if (singleInstance == null) {
            try {
                singleInstance = new NetworkManager();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return singleInstance;
    }

    public static class out {
        public static void println(int line) {
            synchronized (prStream) {
                prStream.println(line);
            }
        }

        public static void println(Object line) {
            synchronized (prStream) {
                prStream.println(line);
            }
        }

        public static void println(String line) {
            synchronized (prStream) {
                prStream.println(line);
            }
        }

        private static final PrintStream prStream = NetworkManager.getInstance().printStream;
    }

    public static class buffer {
        public static boolean ready() throws IOException {
            synchronized (buff) {
                return buff.ready();
            }
        }

        public static String readLine() throws IOException {
            synchronized (buff) {
                return buff.readLine();
            }
        }

        private static final BufferedReader buff = NetworkManager.getInstance().bufferedReader;
    }

    private static NetworkManager singleInstance;
    public static final short PORT = (short)2910;
    public static final String HOST = "localhost";
    private final PrintStream printStream;
    private final BufferedReader bufferedReader;
}
