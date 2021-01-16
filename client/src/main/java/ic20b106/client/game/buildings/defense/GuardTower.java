package ic20b106.client.game.buildings.defense;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.defense.GuardTowerSubMenu;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Barracks are the smallest defensive building
 */
public class GuardTower extends Defense {

    /**
     * Constructor
     *
     * @param cell Cell on which the Barracks are placed
     */
    protected GuardTower(Cell cell) {
        super("/images/neutral/buildings/construction-site.png",
          new HashMap<>(){{
              put(Material.WOOD, 3);
              put(Material.ROCK, 5);
          }}, cell, 3);
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new GuardTowerSubMenu(this.cell, this);
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
        return "/fxml/buildings/defense/GuardTower.fxml";
    }
}
