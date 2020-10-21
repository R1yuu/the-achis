package ic20b106.game.menus;

import ic20b106.game.Cell;

import java.io.IOException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Contains the Current BuildMenu and Cell
 */
public class BuildMenu extends Menu {

    /**
     * Constructor
     *
     * @param selectedCell Cell on which to Build
     */
    public BuildMenu(Cell selectedCell) throws IOException {
        super(selectedCell, "/ic20b106/menus/game/submenus/BuildingSubMenu.fxml");
    }
}
