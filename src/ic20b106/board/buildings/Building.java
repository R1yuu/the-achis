package ic20b106.board.buildings;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Building has various Tasks.
 * It can be Placed on a Cell.
 */
public abstract class Building extends ImageView {

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     * @param texture Texture of the Building
     */
    public Building(Image texture) {
        super(texture);
    }
}
