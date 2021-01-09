package ic20b106.client.game.buildings.defense;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import ic20b106.shared.PlayerColor;

import java.util.HashMap;

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
     * @param texturePath Path to the texture of this building
     * @param buildingCost Material cost to build the building
     * @param cell Cell the building is placed
     * @param area Area to extend
     */
    protected Defense(String texturePath, HashMap<Material, Integer> buildingCost, Cell cell, int area) {
        super(texturePath, buildingCost, cell);
        cell.extendArea(PlayerColor.BLUE, area);
    }
}
