package ic20b106.client.game.buildings.production;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.production.QuarrySubMenu;
import javafx.beans.property.SimpleMapProperty;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Produces Energy Particles
 */
public class Quarry extends Producer {

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     */
    public Quarry(Cell cell) {
        super("/images/neutral/buildings/production/quarry.png",
          new HashMap<>() {{
              put(Material.WOOD, 2);
          }},
          cell);
    }

    @Override
    protected void produce() {

    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new QuarrySubMenu(this.cell, this);
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/production/Quarry.fxml";
    }
}
