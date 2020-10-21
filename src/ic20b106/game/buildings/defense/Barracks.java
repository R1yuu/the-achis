package ic20b106.game.buildings.defense;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.game.buildings.Material;
import ic20b106.game.menus.BuildingMenu;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.HashMap;

public class Barracks extends Defense {

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     */
    protected Barracks(Cell cell) {
        super(new Image("/images/buildings/core.png", 200, 0, true, false, true),
          new HashMap<>() {{
              put(Material.PEARL, 7);
              put(Material.METAL, 7);
          }}, cell, 2);
    }

    @Override
    public void openMenu(Cell selectedCell) throws IOException {
        Game.activeBuildingMenu =
          new BuildingMenu(selectedCell,"/ic20b106/menus/game/submenus/buildings/MainCore.fxml");
    }
}
