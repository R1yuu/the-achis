package ic20b106.client.game.buildings;

import ic20b106.shared.Buildable;
import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.shared.utils.IntPair;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Building has various Tasks.
 * It can be Placed on a Cell.
 */
public abstract class Building implements Buildable, Serializable {

    protected Cell cell;
    protected ObservableMap<Material, Integer> neededMaterials = new SimpleMapProperty<>();
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
     * @param texturePath Texture of the Building
     */
    protected Building(String texturePath, HashMap<Material, Integer> buildingCost, Cell cell) {
        this (texturePath, buildingCost, cell, true);
    }

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     * @param texturePath Texture of the Building
     */
    protected Building(String texturePath, HashMap<Material, Integer> buildingCost,
                       Cell cell, boolean isConstructionSite) {
        this.buildingImage = new Image(getClass().getResource(texturePath).toString(),
          Game.resolution, 0, true, false, true);

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

        this.neededMaterials = FXCollections.observableMap(buildingCost);

        this.neededMaterials.addListener((MapChangeListener<Material, Integer>) change -> {
            if (this.peekNeededMaterial() == null && this.isConstructionSite) {
                this.isConstructionSite = false;
                this.activeTexture.setImage(this.buildingImage);
                this.activeTexture.setFitHeight(Game.cellSize);
                this.activeTexture.setFitWidth(Game.cellSize);
            }
        });

        this.cell = cell;
    }

    /**
     * Pops a needed Material
     *
     * @return A needed Material or null if there are none needed
     */
    public Material popNeededMaterial() {
        Material neededMaterial = this.peekNeededMaterial();
        if (neededMaterial != null) {
            this.neededMaterials.put(neededMaterial, this.neededMaterials.get(neededMaterial) - 1);
        }
        return neededMaterial;
    }

    /**
     * Peeks if there is a needed Material
     *
     * @return Needed Material
     */
    public Material peekNeededMaterial() {
        for (Material material : Material.values()) {
            Integer buildMaterial = this.neededMaterials.getOrDefault(material, 0);
            if (buildMaterial > 0) {
                return material;
            }
        }
        return null;
    }

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
        return isConstructionSite;
    }

    public void openMenu(Cell selectedCell) {
        if (Game.activeSubMenu != null) {
            Game.activeSubMenu.close();
        }
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
