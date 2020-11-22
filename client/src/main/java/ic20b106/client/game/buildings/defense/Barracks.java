package ic20b106.client.game.buildings.defense;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.CoreSubMenu;

import java.io.IOException;
import java.util.HashMap;

public class Barracks extends Defense {

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     */
    protected Barracks(Cell cell) {
        super("/images/buildings/core.png",
          new HashMap<>() {{
              put(Material.PEARL, 7);
              put(Material.METAL, 7);
          }}, cell, 2);
    }

    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new CoreSubMenu(this.cell, this);
    }

    @Override
    public String getMenuPath() {
        return "/fxml/Barracks.fxml";
    }
}
