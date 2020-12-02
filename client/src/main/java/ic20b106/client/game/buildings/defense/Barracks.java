package ic20b106.client.game.buildings.defense;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.CoreSubMenu;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Barracks are the smallest defensive building
 */
public class Barracks extends Defense {

    /**
     * Constructor
     *
     * @param cell Cell on which the Barracks are placed
     */
    protected Barracks(Cell cell) {
        super("/images/buildings/core.png",
          new HashMap<>() {{
              put(Material.PEARL, 7);
              put(Material.METAL, 7);
          }}, cell, 2);
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
        return "/fxml/buildings/Barracks.fxml";
    }
}
