package ic20b106.client.game.buildings.production;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.FactorySubMenu;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * Produces Energy Particles
 */
public class Factory extends Building {

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     */
    public Factory(Cell cell) {
        super("/images/buildings/factory.png",
          new HashMap<>() {{
              put(Material.PEARL, 4);
          }},
          cell);
    }

    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new FactorySubMenu(this.cell, this);
    }

    @Override
    public String getMenuPath() {
        return "/fxml/buildings/Factory.fxml";
    }
}
