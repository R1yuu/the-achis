package ic20b106.client.game.link;

import ic20b106.shared.Buildable;
import ic20b106.shared.utils.IntPair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * A Link is a Connection between Cells/Buildings
 */
public class Link extends ImageView implements Buildable {

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
          new Image(getClass().getResource("/images/buildings/link-" + textureName + ".png").toString(),
            200, 0, true, false, true));

        this.setFitHeight(50);
        this.setFitWidth(50);

        this.linkDirection = linkDirection;
    }


    private final LinkDirection linkDirection;

    @Override
    public ImageView getTexture() {
        return null;
    }

    @Override
    public IntPair getPosition() {
        return null;
    }

    @Override
    public boolean isConstructionSite() {
        return false;
    }
}
