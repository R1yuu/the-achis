package ic20b106.client.exceptions;

public class InvalidHashException extends Exception {

    /**
     * Constructor
     */
    public InvalidHashException() {
        super();
    }

    /**
     * Constructor
     *
     * @param msg Message of the Exception
     */
    public InvalidHashException(String msg) {
        super(msg);
    }
}
