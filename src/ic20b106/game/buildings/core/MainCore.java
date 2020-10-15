package ic20b106.game.buildings.core;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Material;
import javafx.scene.image.Image;

import java.util.HashMap;

public class MainCore extends Core {
    public MainCore(Cell cell) {
        super(new Image("/images/buildings/core.png", 200, 0, true, false, true),
          new HashMap<>() {{
              put(Material.PEARL, 10);
              put(Material.METAL, 5);
          }}, cell);
    }
}
