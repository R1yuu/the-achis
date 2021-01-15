package ic20b106.client.game.buildings;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.HeadquartersSubMenu;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Core is the Main Building of a Player.
 * When it gets Destroyed, the Player loses.
 */
public class Headquarters extends Building implements StorageBuilding {

    protected HashMap<Material, Integer> storage;

    /**
     * Constructor
     *
     * @param cell Cell the Core is placed on
     * @param storage Storage content of the Core
     */
    public Headquarters(Cell cell, HashMap<Material, Integer> storage) {
        super("/images/neutral/buildings/headquaters.png",
          new HashMap<>(), cell, false);

        this.storage = storage;
        this.isConstructionSite = false;
    }

    public Material popStoredMaterial(Material material) {
        Integer matQuantity = storage.getOrDefault(material, 0);
        if (matQuantity > 0) {
            storage.put(material, matQuantity - 1);
            return material;
        }
        return null;
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new HeadquartersSubMenu(this.cell, this);
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/Headquarters.fxml";
    }
}
