package ic20b106.shared;

import ic20b106.shared.utils.IntPair;
import javafx.scene.image.ImageView;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Interface for all Buildable Objects
 */
public interface Buildable {
    ImageView getTexture();
    IntPair getPosition();
    boolean isConstructionSite();
}
