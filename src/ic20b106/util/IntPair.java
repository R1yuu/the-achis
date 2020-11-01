package ic20b106.util;

public class IntPair extends Pair<Integer, Integer> {

    public IntPair() {
        super(0, 0);
    }

    public IntPair(Integer x, Integer y) {
        super(x, y);
    }

    public static IntPair fromString(String pairString) {
        IntPair result = new IntPair();
        pairString = pairString.replaceAll("([()])", "");
        System.out.println(pairString);
        String[] values = pairString.split(",");

        result.x = Integer.valueOf(values[0]);
        result.y = Integer.valueOf(values[1]);

        System.out.println(result.x);
        System.out.println(result.y);
        return result;
    }
}
