package ic20b106.shared.utils;

public class IntPair extends Pair<Integer, Integer> {

    public IntPair() {
        super(0, 0);
    }

    public IntPair(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    public IntPair clone() throws CloneNotSupportedException {
        return (IntPair) super.clone();
    }
}
