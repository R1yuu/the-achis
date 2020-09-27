package ic20b106.exceptions;

public class ArgumentOutOfBoundsException extends IllegalArgumentException {
    public ArgumentOutOfBoundsException() {
        super();
    }

    public ArgumentOutOfBoundsException(String msg) {
        super();
        this.msg = msg;
    }

    public String msg;
}
