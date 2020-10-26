package ic20b106.game;

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

    public static LinkDirection stringToLinkDirection(String str) {
        return switch (str) {
            case "Top Left", "Top\nLeft", "Top-Left" -> TOP_LEFT;
            case "Top Right", "Top\nRight", "Top-Right" -> TOP_RIGHT;
            case "Right" -> RIGHT;
            case "Bottom Right", "Bottom\nRight", "Bottom-Right" -> BOTTOM_RIGHT;
            case "Bottom Left", "Bottom\nLeft", "Bottom-Left" -> BOTTOM_LEFT;
            case "Left" -> LEFT;
            default -> null;
        };
    }

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
