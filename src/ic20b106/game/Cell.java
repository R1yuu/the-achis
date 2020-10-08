package ic20b106.game;

import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.Link;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Cells are the Buildings Fields
 */
public class Cell extends StackPane {

    /**
     * Default Constructor
     */
    public Cell(int row, int col) {
        this(row, col, defaultHeight, defaultWidth, defaultBorder);
    }

    /**
     * Constructor
     * Sets Height and Width of HBox
     *
     * @param height Height of Box
     * @param width Width of Box
     */
    public Cell(int row, int col, double height, double width) {
        this(row, col, height, width, defaultBorder);
    }

    /**
     * Constructor
     *
     * @param border Sets the Border of the Cell (important for Debug)
     */
    public Cell(int row, int col, Border border) {
        this(row, col, defaultHeight, defaultWidth, border);
    }

    /**
     * Constructor
     *
     * @param height Height of Box
     * @param width Width of Box
     * @param border Sets the Border of the Cell (important for Debug)
     */
    public Cell(int row, int col, double height, double width, Border border) {
        if (border != null) setBorder(border);

        this.setPrefHeight(height);
        this.setPrefWidth(width);

        this.row = row;
        this.col = col;

        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (GameStage.mainGameStage.activeBuildMenu != null) {
                    GameStage.mainGameStage.activeBuildMenu.close();
                }

                try {
                    GameStage.mainGameStage.activeBuildMenu = new BuildMenu(this);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }


    /**
     * Places Buildings
     *
     * @param building A Building that can be built on the Cell
     */
    public void setBuilding(Building building) {
        if (this.building == null) {
            addLink(LinkDirection.BOTTOM_RIGHT);
            this.getChildren().add(building);
        }
    }

    /**
     * Adds a Link to another Cell just with the Direction
     *
     * @param linkDirection Direction of the linked Cell
     */
    public void addLink(LinkDirection linkDirection) {
        Cell linkedCell = findCellByLinkDirection((Board)this.getParent(), this, linkDirection);
        Cell oldCell = this.links.putIfAbsent(linkDirection, linkedCell);

        if (oldCell == null) {
            this.getChildren().add(new Link(linkDirection));
        }

        linkedCell.addLink(LinkDirection.getOpposite(linkDirection), this);
    }

    /**
     * Adds a Link to another Cell
     *
     * @param linkDirection Direction to linked Cell
     * @param linkedCell Cell to Link to
     */
    public void addLink(LinkDirection linkDirection, Cell linkedCell) {
        Cell oldCell = this.links.putIfAbsent(linkDirection, linkedCell);
        if (oldCell == null) {
            this.getChildren().add(new Link(linkDirection));
        }
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

    /**
     * Finds a neighbouring Cell by Direction
     *
     * @param board Board on which the Cells are
     * @param cell Cell to find neighbour from
     * @param linkDirection Direction of the neighouring Cell
     * @return Returns the neighbouring Cell
     */
    public static Cell findCellByLinkDirection(Board board, Cell cell, LinkDirection linkDirection) {
        int cellRow = cell.row;
        int cellCol = cell.col;
        switch (linkDirection) {
            case BOTTOM_RIGHT -> {
                cellRow++;
                if (cell.row % 2 == 1) cellCol++;
            }
            case TOP_RIGHT -> {
                cellRow--;
                if (cell.row % 2 == 1) cellCol++;
            }
            case RIGHT -> cellCol++;
            case LEFT -> cellCol--;
            case BOTTOM_LEFT -> {
                cellRow++;
                if (cell.row % 2 == 0) cellCol--;
            }
            case TOP_LEFT -> {
                cellRow--;
                if (cell.row % 2 == 0) cellCol--;
            }
        }

        return board.getCell(cellRow, cellCol);
    }

    /**
     * links Getter
     *
     * @return Returns Every Link of the Cell
     */
    public HashMap<LinkDirection, Cell> getLinks() {
        return links;
    }

    /**
     * links Setter
     *
     * @param links links to set for Cell
     */
    public void setLinks(HashMap<LinkDirection, Cell> links) {
        this.links = links;
    }

    private final int row;
    private final int col;
    private Terrain terrain;
    private HashMap<LinkDirection, Cell> links = new HashMap<>();
    private Building building;
    private static final double defaultHeight = 50;
    private static final double defaultWidth = 50;
    private static final Border defaultBorder = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

}
