package ic20b106.client.game.buildings.defense;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.defense.GuardHouseSubMenu;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Map;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Guard House is the smallest defensive building
 */
public class GuardHouse extends Defense {

    private static final Image texture = new Image(GuardHouse.class.getResource("/images/neutral/buildings/construction-site.png").toString(),
      Game.resolution, 0, true, false, true);

    /**
     * Constructor
     *
     * @param cell Cell on which the Barracks are placed
     */
    protected GuardHouse(Cell cell) {
        super(texture, Map.of(Material.WOOD, 5),
          null, cell, 2);
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new GuardHouseSubMenu(this.cell, this);
    }

    /**
     * Demolishes Building
     */
    @Override
    public void demolish() {

    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/defense/GuardHouse.fxml";
    }
}
