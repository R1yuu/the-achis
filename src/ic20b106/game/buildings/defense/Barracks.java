package ic20b106.game.buildings.defense;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Material;
import javafx.scene.image.Image;

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
    public String getMenuPath() {
        return "/ic20b106/fxml/game/submenus/buildings/core/Core.fxml";
    }
}
