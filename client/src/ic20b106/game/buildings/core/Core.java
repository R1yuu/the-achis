package ic20b106.game.buildings.core;

import ic20b106.game.board.Cell;
import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.Material;
import ic20b106.game.menus.BuildingSubMenu;
import ic20b106.game.menus.buildings.CoreSubMenu;
import javafx.scene.image.Image;

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

    public Core(Cell cell, HashMap<Material, Integer> storage) {
        super(new Image("/images/buildings/core2.png", 128, 128, true, false, true),
          null, cell);

        this.storage = storage;
        this.isConstructionSite = false;
    }

    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new CoreSubMenu(this.cell, this);
    }

    @Override
    public String getMenuPath() {
        return "/ic20b106/fxml/game/submenus/buildings/core/Core.fxml";
    }

    protected HashMap<Material, Integer> storage = new HashMap<>();
}
