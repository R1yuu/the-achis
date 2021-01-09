package ic20b106.client.game.buildings.defense;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.defense.CastleSubMenu;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Castles are the biggest defensive buildings
 */
public class Castle extends Defense {

    /**
     * Constructor
     *
     * @param cell Cell on which the Barracks are placed
     */
    protected Castle(Cell cell) {
        super("/images/neutral/buildings/construction-site.png",
          new HashMap<>() {{
              put(Material.WOOD, 4);
              put(Material.ROCK, 7);
          }}, cell, 6);
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new CastleSubMenu(this.cell, this);
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/defense/Castle.fxml";
    }
}
