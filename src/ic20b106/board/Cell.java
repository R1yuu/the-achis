package ic20b106.board;

import ic20b106.board.buildings.Building;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Cells are the Buildings Fields
 */
public class Cell extends HBox {

    /**
     * Default Constructor
     */
    public Cell() {
        this(defaultHeight, defaultWidth, defaultBorder);
    }

    /**
     * Constructor
     * Sets Height and Width of HBox
     *
     * @param height Height of Box
     * @param width Width of Box
     */
    public Cell(double height, double width) {
        this(height, width, defaultBorder);
    }

    /**
     * Constructor
     *
     * @param border Sets the Border of the Cell (important for Debug)
     */
    public Cell(Border border) {
        this(defaultHeight, defaultWidth, border);
    }

    /**
     * Constructor
     *
     * @param height Height of Box
     * @param width Width of Box
     * @param border Sets the Border of the Cell (important for Debug)
     */
    public Cell(double height, double width, Border border) {
        if (border != null) setBorder(border);

        this.setPrefHeight(height);
        this.setPrefWidth(width);
    }


    /**
     * Places Buildings
     *
     * @param building A Building that can be built on the Cell
     */
    public void setBuilding(Building building) {

        this.getChildren().add(building);
    }

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
    private static final double defaultHeight = 50;
    private static final double defaultWidth = 50;
    private static final Border defaultBorder = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

}
