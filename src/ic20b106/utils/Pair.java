package ic20b106.utils;

public class Pair<X, Y> {

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x.toString() + ", " + y.toString() + ")";
    }

    public void setXY(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X x;
    public Y y;
}
