package ic20b106.client.game.buildings.core;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.CoreSubMenu;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Core is the Main Building of a Player.
 * When it gets Destroyed, the Player loses.
 */
public class Core extends Building {

    protected HashMap<Material, Integer> storage;

    /**
     * Constructor
     *
     * @param cell Cell the Core is placed on
     * @param storage Storage content of the Core
     */
    public Core(Cell cell, HashMap<Material, Integer> storage) {
        super("/images/buildings/core2.png",
          null, cell);

        this.storage = storage;
        this.isConstructionSite = false;
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new CoreSubMenu(this.cell, this);
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/Core.fxml";
    }
}
