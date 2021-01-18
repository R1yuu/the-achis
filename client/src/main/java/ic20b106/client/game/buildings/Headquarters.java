package ic20b106.client.game.buildings;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.client.game.menus.submenus.buildings.HeadquartersSubMenu;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.HashMap;
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

    protected final HashMap<Material, Integer> storage;

    /**
     * Constructor
     *
     * @param cell Cell the Core is placed on
     * @param storage Storage content of the Core
     */
    public Headquarters(Cell cell, HashMap<Material, Integer> storage) {
        super(texture, new HashMap<>(), cell, false);

        this.storage = storage;

        this.neededMaterials.removeListener(this.neededMaterialChangeListener);
        this.neededMaterials.addListener((MapChangeListener<Material, Integer>) change -> {
            if (change.getValueAdded() < Objects.requireNonNullElse(change.getValueRemoved(), 0)) {
                synchronized (this.storage) {
                    Integer stored = this.storage.getOrDefault(change.getKey(), 0);
                    this.storage.put(change.getKey(), stored + (change.getValueRemoved() - change.getValueAdded()));
                }
            }
        });
    }

    /**
     * Getter
     *
     * @return Storage of Headquaters
     */
    public HashMap<Material, Integer> getStorage() {
        synchronized (this.storage) {
            return this.storage;
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
