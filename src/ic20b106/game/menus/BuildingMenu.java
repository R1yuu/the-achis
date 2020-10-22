package ic20b106.game.menus;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Building;

import java.io.IOException;

public class BuildingMenu extends Menu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildingMenu(Cell selectedCell, Building building) throws IOException {
        super(selectedCell, building.getMenuPath());
    }
}
