package ic20b106.client.exceptions;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Custom Exception for Values that are out of Range
 */
public class ArgumentOutOfBoundsException extends IllegalArgumentException {

    /**
     * Constructor
     *
     * @param msg Message of the Exception
     */
    public ArgumentOutOfBoundsException(String msg) {
        super(msg);
    }
}
