package ic20b106.game.link;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * A Link is a Connection between Cells/Buildings
 */
public class Link extends ImageView {

    /**
     * Constructor
     *
     * @param linkDirection Direction of the Link
     */
    public Link(LinkDirection linkDirection) {
        super();

        //TODO: Use linkDirection.toString();
        String textureName = switch (linkDirection) {
            case LEFT -> "left";
            case TOP_LEFT -> "top-left";
            case BOTTOM_LEFT -> "bottom-left";
            case BOTTOM_RIGHT -> "bottom-right";
            case RIGHT -> "right";
            case TOP_RIGHT -> "top-right";
        };

        this.setImage(
                new Image("/images/buildings/link-" + textureName + ".png", 200, 0, true, false, true));

        this.setFitHeight(50);
        this.setFitWidth(50);

        this.linkDirection = linkDirection;

    }

    private final LinkDirection linkDirection;
}