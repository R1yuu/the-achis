package ic20b106.game.buildings.core;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.Material;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Core is the Main Building of a Player.
 * When it gets Destroyed, the Player loses.
 */
public class Core extends Building {

    public Core(Cell cell) {
        super(new Image("/images/buildings/core.png", 200, 0, true, false, true),
          new HashMap<>() {{
              put(Material.PEARL, 10);
              put(Material.METAL, 5);
          }}, cell);

        this.isConstructionSite = false;
    }

    @Override
    public String getMenuPath() {
        return "/ic20b106/fxml/game/submenus/buildings/core/Core.fxml";
    }

    protected HashMap<Material, Integer> storage = new HashMap<>();
}
