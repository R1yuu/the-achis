package ic20b106.client.game.buildings.defense;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Headquarters;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.defense.GuardHouseSubMenu;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.HashMap;

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
        super(texture,
          new HashMap<>(){{
              put(Material.WOOD, 5);
          }}, null, cell, 2);
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

    @Override
    protected void constructionListener(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {

    }

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
