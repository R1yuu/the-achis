package ic20b106.game.buildings.production;

import ic20b106.game.Cell;
import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.Material;
import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * @author Andre_Schneider
 * @version 1.0
 *
 * Produces Energy Particles
 */
public class Factory extends Building {

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     */
    public Factory(Cell cell) {
        super(new Image("/images/buildings/factory.png", 200, 0, true, false, true),
          new HashMap<>() {{
              put(Material.PEARL, 10);
              put(Material.METAL, 5);
          }}, cell);
    }

    @Override
    public String getMenuPath() {
        return "/ic20b106/fxml/game/submenus/buildings/production/Factory.fxml";
    }
}
