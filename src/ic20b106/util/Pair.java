package ic20b106.util;

public class Pair<X, Y> {

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public void setXY(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X x;
    public Y y;
}
