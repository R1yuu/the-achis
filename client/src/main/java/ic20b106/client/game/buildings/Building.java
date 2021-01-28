package ic20b106.client.game.buildings;

import ic20b106.client.game.buildings.link.Link;
import ic20b106.client.game.buildings.storage.Storable;
import ic20b106.client.manager.NetworkManager;
import ic20b106.shared.Buildable;
import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.shared.utils.IntPair;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Building has various Tasks.
 * It can be Placed on a Cell.
 */
public abstract class Building implements Buildable, Serializable {

    protected Cell cell;
    protected Map<Storable, Integer> buildingCost;
    protected final ObservableMap<Storable, Integer> storage;
    protected ImageView activeTexture;
    protected Image buildingImage;
    protected boolean isConstructionSite;
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
    protected Building(Image texture, Map<Storable, Integer> buildingCost, HashMap<Storable, Integer> storage,
                       Cell cell) {
        this (texture, buildingCost, storage, cell, true);
    }

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     * @param texture Texture of the Building
     */
    protected Building(Image texture, Map<Storable, Integer> buildingCost, HashMap<Storable, Integer> storage,
                       Cell cell, boolean isConstructionSite) {
        this.buildingImage = texture;

        this.isConstructionSite = isConstructionSite;

        if (this.isConstructionSite) {
            this.activeTexture = new ImageView(Building.constructionImage);
            this.activeTexture.setFitHeight(18);
            this.activeTexture.setFitWidth(16);
        } else {
            this.activeTexture = new ImageView(this.buildingImage);
            this.activeTexture.setFitHeight(Game.cellSize);
            this.activeTexture.setFitWidth(Game.cellSize);
        }


        if (storage == null) {
            this.storage = FXCollections.observableHashMap();
        } else {
            this.storage = FXCollections.observableMap(storage);
        }

        this.storage.addListener(this::constructionListener);

        this.buildingCost = buildingCost;

        this.cell = cell;
    }

    /**
     * Listens Construction
     *
     * @param mapChange Changes in Map
     */
    protected void constructionListener(MapChangeListener.Change<? extends Storable, ? extends Integer> mapChange) {
        if (this.isConstructionSite) {
            Map<Storable, Integer> availableMaterial;
            synchronized (this.storage) {
                availableMaterial = Collections.unmodifiableMap(this.storage);
            }
            if (this.buildingCost.equals(availableMaterial)) {
                this.isConstructionSite = false;
                this.activeTexture.setImage(this.buildingImage);
                this.activeTexture.setFitHeight(Game.cellSize);
                this.activeTexture.setFitWidth(Game.cellSize);
                this.buildingCost = null;
            }
        }
    }

    /**
     * Getter
     *
     * @return Storage of Headquaters
     */
    public Map<Storable, Integer> getStorage() {
        synchronized (this.storage) {
            return this.storage;
        }
    }

    /**
     * Returns Queue of Transferable Items
     *
     * @return Queue of Storables
     */
    public Queue<Storable> getStorableQueue() {
        Queue<Storable> storableQueue;

        if (this.isConstructionSite) {
            storableQueue = new LinkedList<>();
            this.buildingCost.forEach((material, amount) -> {
                for (int i = 0; i < amount; i++) {
                    storableQueue.add(material);
                }
            });
            return storableQueue;
        }
        return null;
    }

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

    /**
     * Returns Position of Cell
     *
     * @return Cell Position
     */
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
        return isConstructionSite;
    }

    /**
     * Opens Menu
     *
     * @param selectedCell Opens Menu for a specific Cell
     */
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
