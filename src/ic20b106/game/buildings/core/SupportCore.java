package ic20b106.game.buildings.core;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Material;
import javafx.scene.image.Image;

import java.util.HashMap;

public class SupportCore extends Core {
    public SupportCore(Cell cell) {
        super(new Image("/images/buildings/core.png", 200, 0, true, false, true),
          new HashMap<>() {{
              put(Material.PEARL, 5);
              put(Material.METAL, 3);
          }}, cell);
    }

    @Override
    public String getMenuPath() {
        return "/ic20b106/menus/game/submenus/buildings/SupportCore.fxml";
    }
}
