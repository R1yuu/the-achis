package ic20b106.board;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * Directions a Link can have
 */
public enum LinkDirection {
    TOP_LEFT, LEFT, BOTTOM_LEFT,
    BOTTOM_RIGHT, RIGHT, TOP_RIGHT;

    /**
     * Return the Opposit Direction
     *
     * @param linkDirection Link Direction to find opposite of
     * @return Returns Opposite LinkDirection
     */
    public static LinkDirection getOpposite(LinkDirection linkDirection) {
        LinkDirection opposite;
        switch (linkDirection) {
            case TOP_LEFT -> opposite = BOTTOM_RIGHT;
            case BOTTOM_RIGHT -> opposite = TOP_LEFT;
            case LEFT -> opposite = RIGHT;
            case RIGHT -> opposite = LEFT;
            case TOP_RIGHT -> opposite = BOTTOM_LEFT;
            case BOTTOM_LEFT -> opposite = TOP_RIGHT;
            default -> throw new IllegalStateException("Unexpected value: " + linkDirection);
        }
        return opposite;
    }
}
