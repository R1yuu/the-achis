package ic20b106.client.exceptions;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Custom Exception for Hash related Problems
 */
public class HashException extends Exception {

    /**
     * Constructor
     */
    public HashException() {
        super();
    }

    /**
     * Constructor
     *
     * @param msg Message of the Exception
     */
    public HashException(String msg) {
        super(msg);
    }
}
