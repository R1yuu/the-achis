package ic20b106.client.manager;

import ic20b106.client.Lobby;
import ic20b106.client.util.ByteUtils;
import ic20b106.shared.Buildable;
import ic20b106.shared.ClientCommands;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.RemoteCommands;
import ic20b106.shared.NetworkConstants;
import org.hashids.Hashids;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.Iterator;

public class NetworkManager extends UnicastRemoteObject implements ClientCommands, Serializable {

    private static NetworkManager singleInstance;
    private String playerHash;
    public RemoteCommands serverStub;

    private NetworkManager() throws IOException, NotBoundException {

        String hash = FileManager.getInstance().readHashid();
        if (hash.equals("")) {
            hash = generateHash();
            FileManager.getInstance().writeHashid(hash);
            System.out.println("Generated!");
        }

        System.out.println("Hash:");
        System.out.println(hash);

        this.playerHash = hash;

        Socket socket = new Socket(NetworkConstants.HOST, NetworkConstants.PORT);

        PrintStream printer = new PrintStream(socket.getOutputStream(), true);
        printer.println(this.playerHash);

        BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if (!buffer.readLine().equals("RECEIVED")) {
            throw new ConnectException("Connection Error!");
        }

        buffer.close();
        socket.close();

        this.serverStub = (RemoteCommands)Naming.lookup(NetworkConstants.RMI_HOST + ":" +
          NetworkConstants.RMI_PORT + "/" + this.playerHash);



        this.serverStub.sendClientStub(this);
    }

    @Override
    public void updateBuilding(Buildable buildable) {

    }

    @Override
    public void sendMessage(Object message) {
        System.out.println(message);
    }

    @Override
    public void close() {
        singleInstance = null;
    }

    public static NetworkManager getInstance() {
        if (singleInstance == null) {
            try {
                singleInstance = new NetworkManager();
            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }
        return singleInstance;
    }

    public static String generateHash() throws SocketException {
        Iterator<NetworkInterface> netInts = NetworkInterface.getNetworkInterfaces().asIterator();

        long hardwareLong = 0L;
        while (netInts.hasNext()) {
            NetworkInterface netInt = netInts.next();
            if (netInt.getHardwareAddress() != null) {
                byte[] leadingBytes = { 0, 0 };
                byte[] hardwareBytes =
                  ByteUtils.concatByteArrays(leadingBytes, netInt.getHardwareAddress());
                hardwareLong = ByteUtils.bytesToLong(hardwareBytes, 0);
                break;
            }
        }

        if (hardwareLong == 0L) {
            // TODO: Handle Exception in a better way
            throw new IllegalStateException("No working Network-Card found!");
        }

        SecureRandom secureRandom = new SecureRandom();
        long rdmLong = secureRandom.nextLong();
        while (rdmLong < 0L || rdmLong > 9007199254740992L) {
            rdmLong = secureRandom.nextLong();
        }

        System.out.println(hardwareLong);
        System.out.println(rdmLong);

        Hashids hashids = new Hashids();
        return hashids.encode(
          hardwareLong,
          rdmLong
        );

    }

    @Override
    public void updateColors(PlayerColor freeColor, PlayerColor takenColor) {
        Lobby.colorComboBox.getItems().remove(takenColor.toString());
        Lobby.colorComboBox.getItems().add(freeColor.toString());
    }

    @Override
    public void updateLobby() {
        Lobby.updateTable();
    }
}
