package ic20b106.game.buildings.core;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.Material;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Core is the Main Building of a Player.
 * When it gets Destroyed, the Player loses.
 */
public abstract class Core extends Building {

    /**
     * Constructor
     *
     */
    protected Core(Image texture, HashMap<Material, Integer> buildingCost, Cell cell) {
        super(texture, buildingCost, cell);
    }

    protected HashMap<Material, Integer> storage = new HashMap<>();
}
