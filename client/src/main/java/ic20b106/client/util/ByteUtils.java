package ic20b106.client.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class ByteUtils {

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    // Credit: https://gist.github.com/jeffjohnson9046/c663dd22bbe6bb0b3f5e
    public static byte[] getBytesFromUUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }

    // Credit: https://stackoverflow.com/a/9855338
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static Byte[] toObjectArr(byte[] byteArr) {
        Byte[] byteObjectArr = new Byte[byteArr.length];

        int i = 0;
        for (byte b : byteArr) {
            byteObjectArr[i++] = b;
        }

        return byteObjectArr;
    }

    public static byte[] toPrimitiveArr(Byte[] byteObjArr) {
        byte[] byteArr = new byte[byteObjArr.length];

        int i = 0;
        for (Byte b : byteObjArr) {
            byteArr[i++] = b;
        }

        return byteArr;
    }

    public static byte[] concatByteArrays(byte[]... byteArrays) {
        byte[] res = new byte[0];

        int resArrPos = 0;
        for (byte[] byteArr : byteArrays) {
            res = Arrays.copyOf(res, res.length + byteArr.length);
            for (int i = 0; i < byteArr.length; i++, resArrPos++) {
                res[resArrPos] = byteArr[i];
            }
        }
        return res;

    }

    // Credit: https://stackoverflow.com/a/29132118
    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    // Credit: https://stackoverflow.com/a/29132118
    public static long bytesToLong(final byte[] bytes, final int offset) {
        assert bytes.length >= Long.BYTES + offset;
        long result = 0;
        for (int i = offset; i < Long.BYTES + offset; i++) {
            result <<= Long.BYTES;
            result |= (bytes[i] & 0xFF);
        }
        return result;
    }
}
