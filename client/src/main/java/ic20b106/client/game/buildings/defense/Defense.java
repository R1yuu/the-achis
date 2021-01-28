package ic20b106.client.game.buildings.defense;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.buildings.storage.Storable;
import ic20b106.shared.PlayerColor;
import javafx.beans.property.SimpleMapProperty;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Defensive Buildings allow combat and extend a players area
 */
public abstract class Defense extends Building {

    /**
     * Constructor
     *
     * @param texture Path to the texture of this building
     * @param buildingCost Material cost to build the building
     * @param cell Cell the building is placed
     * @param area Area to extend
     */
    protected Defense(Image texture, Map<Storable, Integer> buildingCost, HashMap<Storable, Integer> storage,
                      Cell cell, int area) {
        super(texture, buildingCost, storage, cell);
        cell.extendArea(PlayerColor.BLUE, area);
    }
}
