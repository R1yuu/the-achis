package ic20b106.shared.utils;

import java.io.Serializable;

public class Pair<X, Y> implements Serializable {

    public X x;
    public Y y;

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
}
