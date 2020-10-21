package ic20b106.game.menus;

import ic20b106.game.Cell;

import java.io.IOException;

public class BuildingMenu extends Menu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildingMenu(Cell selectedCell, String subMenuPath) throws IOException {
        super(selectedCell, subMenuPath);
    }

}
