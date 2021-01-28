package ic20b106.client.game.buildings.storage;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.HeadquartersSubMenu;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Core is the Main Building of a Player.
 * When it gets Destroyed, the Player loses.
 */
public class Headquarters extends Building {

    private static final Image texture = new Image(Headquarters.class.getResource("/images/neutral/buildings/headquaters.png").toString(),
      Game.resolution, 0, true, false, true);

    /**
     * Constructor
     *
     * @param cell Cell the Core is placed on
     * @param storage Storage content of the Core
     */
    public Headquarters(Cell cell, HashMap<Storable, Integer> storage) {
        super(texture, Collections.emptyMap(), storage, cell, false);
    }

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    @Override
    public BuildingSubMenu getBuildingSubMenu() throws IOException {
        return new HeadquartersSubMenu(this.cell, this);
    }

    /**
     * What happenes when Building gets Destroyed
     */
    @Override
    public void demolish() {}

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    @Override
    public String getMenuPath() {
        return "/fxml/buildings/Headquarters.fxml";
    }
}
