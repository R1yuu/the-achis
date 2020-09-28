package ic20b106.board;

import ic20b106.board.buildings.Building;
import javafx.scene.layout.HBox;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Cells are the Buildings Fields
 */
public class Cell extends HBox {

    /**
     * Places Buildings
     *
     * @param building A Building that can be built on the Cell
     */
    public void setBuilding(Building building) {

        this.getChildren().add(building);
    }



    /*
    public Cell(Image image) {
        super(image);
        this.terrain = new Terrain();
    }

    public Cell(Image image, Terrain terrain) {
        super(image);
        this.terrain = terrain;
    }

    public Cell(Image image, Terrain terrain, double fitWidth, double fitHeight) {
        super(image);
        this.terrain = terrain;
        this.fitWidthProperty().set(fitWidth);
        this.fitHeightProperty().set(fitHeight);
    }
*/

    /**
     * Terrain Getter
     *
     * @return Returns the Terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Terrain Setter
     *
     * @param terrain Terrain of the Cell
     */
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    private Terrain terrain;
}
