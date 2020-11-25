package ic20b106.client.util;

import org.hashids.Hashids;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;

public class HashUtils {

    public static boolean checkHashValidity(String hash) {
        if (hash.equals("")) {
            return false;
        }

        Hashids hashids = new Hashids();
        long[] hashLongs = hashids.decode(hash);

        if (hashLongs.length != 2) {
            return false;
        }

        // If first != Mac Hash and if second is larger than possible
        return Long.numberOfLeadingZeros(hashLongs[0]) >= 16 && hashLongs[1] <= 9007199254740992L;
    }

    public static String generateHash() {
        long hardwareLong = 0L;

        try {
            Iterator<NetworkInterface> netInts = NetworkInterface.getNetworkInterfaces().asIterator();

            while (netInts.hasNext()) {
                NetworkInterface netInt = netInts.next();
                if (netInt.getHardwareAddress() != null) {
                    byte[] leadingBytes = {0, 0};
                    byte[] hardwareBytes =
                      ByteUtils.concatByteArrays(leadingBytes, netInt.getHardwareAddress());
                    hardwareLong = ByteUtils.bytesToLong(hardwareBytes, 0);
                    break;
                }
            }
        } catch (SocketException socketException) {
            socketException.printStackTrace();
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

        Hashids hashids = new Hashids();
        return hashids.encode(
          hardwareLong,
          rdmLong
        );

    }
}
