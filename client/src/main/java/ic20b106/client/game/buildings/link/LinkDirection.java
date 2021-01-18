package ic20b106.client.game.buildings.link;

import ic20b106.client.Game;
import javafx.scene.image.Image;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Directions a Link can have
 */
public enum LinkDirection {
    TOP_LEFT("Top-Left"), LEFT("Left"), BOTTOM_LEFT("Bottom-Left"),
    BOTTOM_RIGHT("Bottom-Right"), RIGHT("Right"), TOP_RIGHT("Top-Right");

    public final String displayName;
    public final Image texture;

    LinkDirection(String displayName) {
        this.displayName = displayName;
        texture = new Image(getClass().getResource("/images/neutral/buildings/links/" +
          name().toLowerCase() + ".png").toString(),
          Game.resolution, 0, true, false, true);
    }

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
            case "bottom right", "bottom\nright", "bottom-right" -> BOTTOM_RIGHT;
            case "bottom left", "bottom\nleft", "bottom-left" -> BOTTOM_LEFT;
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
        return this.displayName;
    }
}
