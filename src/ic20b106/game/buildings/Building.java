package ic20b106.game.buildings;

import ic20b106.game.Cell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Building has various Tasks.
 * It can be Placed on a Cell.
 */
public abstract class Building implements Buildable {

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     * @param texture Texture of the Building
     */
    protected Building(Image texture, HashMap<Material, Integer> buildingCost, Cell cell) {
        this.texture = new ImageView(texture);
        this.texture.setFitHeight(50);
        this.texture.setFitWidth(50);

        this.buildingCost = buildingCost;

        this.cell = cell;
    }

    public ImageView getTexture() {
        return texture;
    }

    protected Cell cell;
    protected HashMap<Material, Integer> buildingCost;
    protected ImageView texture;

}
