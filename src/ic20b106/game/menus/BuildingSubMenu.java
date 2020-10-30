package ic20b106.game.menus;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Building;

import java.io.IOException;

public class BuildingSubMenu extends SubMenu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildingSubMenu(Cell selectedCell, Building building) throws IOException {
        super(selectedCell, building.getMenuPath());
    }
}
