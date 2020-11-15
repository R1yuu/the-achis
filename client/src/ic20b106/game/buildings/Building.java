package ic20b106.game.buildings;

import ic20b106.Buildable;
import ic20b106.Game;
import ic20b106.game.board.Cell;
import ic20b106.game.menus.BuildingSubMenu;
import ic20b106.utils.IntPair;
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

    /**
     * Constructor
     * Sets the View Texture of a Building
     *
     * @param texture Texture of the Building
     */
    protected Building(Image texture, HashMap<Material, Integer> buildingCost, Cell cell) {
        this.texture = new ImageView(texture);
        this.texture.setFitHeight(50);
        this.texture.setFitWidth(50);

        this.constructionTexture = new ImageView(
          new Image("/images/buildings/construction-site.png", 200, 0, true, false, true));
        constructionTexture.setFitHeight(50);
        constructionTexture.setFitWidth(50);

        this.buildingCost = buildingCost;

        this.cell = cell;

        this.isConstructionSite = true;
    }

    /**
     * Texture Getter
     *
     * @return Returns Texture
     */
    @Override
    public ImageView getTexture() {
        if (isConstructionSite) {
            return this.constructionTexture;
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

    public abstract String getMenuPath();

    public abstract BuildingSubMenu getBuildingSubMenu() throws IOException;

    protected Cell cell;
    protected HashMap<Material, Integer> buildingCost;
    protected ImageView texture;
    protected boolean isConstructionSite;
    private ImageView constructionTexture;
}
