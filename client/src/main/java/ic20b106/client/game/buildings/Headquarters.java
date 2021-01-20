package ic20b106.client.game.buildings;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.HeadquartersSubMenu;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public Headquarters(Cell cell, HashMap<Material, Integer> storage) {
        super(texture, new HashMap<>(), storage, cell, false);
    }

    /**
     * Override
     * Change Listener for neededMaterials Map
     *
     * @param mapChange change in Map
     */
    @Override
    protected void needMaterialsChangeListener(MapChangeListener.Change<? extends Material, ? extends Integer> mapChange) {
        if (mapChange.getValueAdded() < Objects.requireNonNullElse(mapChange.getValueRemoved(), 0)) {
            synchronized (this.storedMaterials) {
                Integer stored = this.storedMaterials.getOrDefault(mapChange.getKey(), 0);
                this.storedMaterials.put(
                  mapChange.getKey(), stored + (mapChange.getValueRemoved() - mapChange.getValueAdded())
                );
            }
        }
    }

    /**
     * Getter
     *
     * @return Storage of Headquaters
     */
    public Map<Material, Integer> getStorage() {
        synchronized (this.storedMaterials) {
            return this.storedMaterials;
        }
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

    @Override
    protected void constructionListener(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {}

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
