package ic20b106.client.util;

import org.hashids.Hashids;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Hash Utility Class
 */
public class HashUtils {

    /**
     * Checks the Hashs validity
     *
     * @param hash hash to check
     * @return boolean if hash is valid or invalid
     */
    public static boolean checkHashValidity(String hash) {
        Logger.getGlobal().info("Checking Hash validity...");
        if (hash == null || hash.equals("")) {
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

    /**
     * Generates a new Player Hash
     *
     * @return Generated Hash String
     */
    public static String generateHash() {
        Logger.getGlobal().info("Generating Hash...");
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
