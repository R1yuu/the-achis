package ic20b106.client.exceptions;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Custom Exception for if a Hash that already exists is used
 */
public class HashTakenException extends HashException {

    /**
     * Constructor
     */
    public HashTakenException() {
        super();
    }

    /**
     * Constructor
     *
     * @param msg Message of the Exception
     */
    public HashTakenException(String msg) {
        super(msg);
    }
}
