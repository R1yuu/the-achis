package ic20b106.client.game.buildings;

import ic20b106.shared.Buildable;
import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.menus.submenus.BuildingSubMenu;
import ic20b106.shared.utils.IntPair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A Building has various Tasks.
 * It can be Placed on a Cell.
 */
public abstract class Building implements Buildable {

    protected Cell cell;
    protected HashMap<Material, Integer> buildingCost;
    protected ImageView texture;
    protected boolean isConstructionSite;
    private final ImageView constructionTexture;
    private static final Image constructionImage;
    static {
        constructionImage = new Image(Building.class.getResource("/images/buildings/construction-site.png").toString(),
          200, 0, true, false, true);
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
        this.texture = new ImageView(new Image(getClass().getResource(texturePath).toString(),
          200, 0, true, false, true));
        this.texture.setFitHeight(Game.cellSize);
        this.texture.setFitWidth(Game.cellSize);

        this.isConstructionSite = isConstructionSite;
        this.constructionTexture = new ImageView(constructionImage);
        this.constructionTexture.setFitHeight(Game.cellSize);
        this.constructionTexture.setFitWidth(Game.cellSize);

        this.buildingCost = buildingCost;

        this.cell = cell;
    }

    /**
     * Texture Getter
     *
     * @return Returns Texture
     */
    @Override
    public ImageView getTexture() {
        if (isConstructionSite) {
            return constructionTexture;
        }
        return texture;
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
