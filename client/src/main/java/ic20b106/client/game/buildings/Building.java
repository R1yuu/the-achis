package ic20b106.client.game.buildings;

import ic20b106.shared.Buildable;
import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.shared.utils.IntPair;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Building has various Tasks.
 * It can be Placed on a Cell.
 */
public abstract class Building implements Buildable, Serializable {

    protected Cell cell;
    protected final ObservableMap<Material, Integer> neededMaterials;
    protected final ObservableMap<Material, Integer> storedMaterials;
    protected ImageView activeTexture;
    protected Image buildingImage;
    protected SimpleBooleanProperty isConstructionSite = new SimpleBooleanProperty();
    private static final Image constructionImage;
    static {
        constructionImage = new Image(Building.class.getResource("/images/neutral/buildings/construction-site.png").toString(),
          Game.resolution, 0, true, false, true);
    }

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     * @param texture Texture of the Building
     */
    protected Building(Image texture, HashMap<Material, Integer> buildingCost, HashMap<Material, Integer> storage,
                       Cell cell) {
        this (texture, buildingCost, storage, cell, true);
    }

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     * @param texture Texture of the Building
     */
    protected Building(Image texture, HashMap<Material, Integer> buildingCost, HashMap<Material, Integer> storage,
                       Cell cell, boolean isConstructionSite) {
        this.buildingImage = texture;

        this.isConstructionSite.addListener(this::constructionListener);

        this.isConstructionSite.set(isConstructionSite);

        if (this.isConstructionSite.get()) {
            this.activeTexture = new ImageView(Building.constructionImage);
            this.activeTexture.setFitHeight(18);
            this.activeTexture.setFitWidth(16);
        } else {
            this.activeTexture = new ImageView(this.buildingImage);
            this.activeTexture.setFitHeight(Game.cellSize);
            this.activeTexture.setFitWidth(Game.cellSize);
        }

        this.storedMaterials =
          Objects.requireNonNullElse(FXCollections.observableMap(storage), FXCollections.observableHashMap());
        this.neededMaterials =
          Objects.requireNonNullElse(FXCollections.observableMap(buildingCost), FXCollections.observableHashMap());

        this.neededMaterials.addListener(this::needMaterialsChangeListener);

        this.cell = cell;
    }

    /**
     * Change Listener for neededMaterials Map
     *
     * @param mapChange change in Map
     */
    protected void needMaterialsChangeListener(
      MapChangeListener.Change<? extends Material, ? extends Integer> mapChange) {
        if (this.isConstructionSite.get()) {
            synchronized (this.neededMaterials) {
                for (Integer needed : this.neededMaterials.values()) {
                    if (needed > 0) {
                        return;
                    }
                }
            }
            this.isConstructionSite.set(false);
            this.activeTexture.setImage(this.buildingImage);
            this.activeTexture.setFitHeight(Game.cellSize);
            this.activeTexture.setFitWidth(Game.cellSize);
        }
    }

    /**
     * Getter
     *
     * @return Map of Needed Materials
     */
    public Map<Material, Integer> getNeededMaterials() {
        synchronized (this.neededMaterials) {

            return this.neededMaterials;
        }

    }

    /**
     * Listener for if the Building is Constructed
     */
    protected abstract void constructionListener(
      ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal);

    /**
     * Demolishes Building
     */
    public abstract void demolish();

    /**
     * Texture Getter
     *
     * @return Returns Texture
     */
    public ImageView getTexture() {
        return this.activeTexture;
    }

    @Override
    public IntPair getPosition() {
        return this.cell.getPosition();
    }

    /**
     * Construction Getter
     *
     * @return Returns if Building is in Construction
     */
    @Override
    public boolean isConstructionSite() {
        return isConstructionSite.get();
    }

    public void openMenu(Cell selectedCell) {
        if (Game.activeSubMenu != null) {
            Game.activeSubMenu.close();
        }
    }

    /**
     * Getter
     *
     * @return Cell the Building is standing on
     */
    public Cell getCell() {
        return this.cell;
    }

    /**
     * Getter
     *
     * @return Returns Path of corresponding fxml file
     */
    public abstract String getMenuPath();

    /**
     * Getter
     *
     * @return Gets Submenu Node
     * @throws IOException Thrown if fxml file couldn't be found
     */
    public abstract BuildingSubMenu getBuildingSubMenu() throws IOException;
}
