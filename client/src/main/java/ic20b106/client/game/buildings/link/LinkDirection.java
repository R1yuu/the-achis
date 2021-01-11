package ic20b106.client.game.buildings.link;

/**
 * @author Andre Schneider
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

    /**
     * Creates a LinkDirection out of a String
     *
     * @param str Direction String
     * @return LinkDirection of the corresponding String
     */
    public static LinkDirection stringToLinkDirection(String str) {
        return switch (str.toLowerCase()) {
            case "top left", "top\nleft", "top-left" -> TOP_LEFT;
            case "top right", "top\nright", "top-right" -> TOP_RIGHT;
            case "right" -> RIGHT;
            case "bottom right", "bottom\nright", "Bottom-right" -> BOTTOM_RIGHT;
            case "bottom left", "bottom\nleft", "Bottom-left" -> BOTTOM_LEFT;
            case "left" -> LEFT;
            default -> null;
        };
    }

    /**
     * toString
     *
     * @return String to corresponding Direction
     */
    @Override
    public String toString() {
        String res = "";

        if (name().contains("TOP")) {
            res += "Top-";
        } else if (name().contains("BOTTOM")) {
            res += "Bottom-";
        }

        if (name().contains("LEFT")) {
            res += "Left";
        } else if (name().contains("RIGHT")) {
            res += "Right";
        }

        return res;
    }
}
