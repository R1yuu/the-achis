package ic20b106.board;

import javafx.collections.ObservableList;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Cell extends HBox {

    public Cell() {
        super();
    }

    public void setBuilding(ImageView building) {
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
    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    private Terrain terrain;
}