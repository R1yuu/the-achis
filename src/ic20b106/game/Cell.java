package ic20b106.game;

import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.Link;
import ic20b106.util.Pair;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
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
     * Constructor
     * Sets Height and Width of HBox
     *
     */
    public Cell(int row, int col, Color owner) {
        this(row, col, defaultBorder, owner);
    }

    /**
     * Constructor
     *
     * @param border Sets the Border of the Cell (important for Debug)
     */
    public Cell(int row, int col, Border border, Color owner) {
        if (border != null) setBorder(border);

        this.owner = owner;

        this.setBackground(new Background(new BackgroundFill(owner, CornerRadii.EMPTY, Insets.EMPTY)));




        this.setPrefHeight(50);
        this.setPrefWidth(50);

        this.row = row;
        this.col = col;

        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (GameStage.activeBuildMenu != null) {
                    GameStage.activeBuildMenu.close();
                }

                try {
                    GameStage.activeBuildMenu = new BuildMenu(this);
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
            this.getChildren().add(building.getTexture());
        }
    }

    /**
     * Adds a Link to another Cell just with the Direction
     *
     * @param linkDirection Direction of the linked Cell
     */
    public void addLink(LinkDirection linkDirection) {

        Cell linkCell = getNeighbourByCell(this, linkDirection);
        Cell alreadyLinkedCell = this.links.putIfAbsent(linkDirection, linkCell);

        if (alreadyLinkedCell == null) {
            this.getChildren().add(new Link(linkDirection));
        }

        linkCell.addLink(LinkDirection.getOpposite(linkDirection), this);
    }

    /**
     * Adds a Link to another Cell
     *
     * @param linkDirection Direction to linked Cell
     * @param linkedCell Cell to Link to
     */
    public void addLink(LinkDirection linkDirection, Cell linkedCell) {
        Cell alreadyLinkedCell = this.links.putIfAbsent(linkDirection, linkedCell);
        if (alreadyLinkedCell == null) {
            this.getChildren().add(new Link(linkDirection));
        }
    }

    public static Cell getNeighbourByCell(Cell cell, LinkDirection linkDirection) {
        return GameStage.gameBoard.getCell(getNeighbourCoordsByCellCoords(
          new Pair<>(cell.row, cell.col), linkDirection));
    }



    public static Pair<Integer, Integer> getNeighbourCoordsByCellCoords(Pair<Integer, Integer> cellCoords,
                                                                    LinkDirection linkDirection) {
        int cellRow = cellCoords.x;
        int cellCol = cellCoords.y;
        switch (linkDirection) {
            case BOTTOM_RIGHT -> {
                cellRow++;
                if (cellCoords.x % 2 == 1) cellCol++;
            }
            case TOP_RIGHT -> {
                cellRow--;
                if (cellCoords.x % 2 == 1) cellCol++;
            }
            case RIGHT -> cellCol++;
            case LEFT -> cellCol--;
            case BOTTOM_LEFT -> {
                cellRow++;
                if (cellCoords.x % 2 == 0) cellCol--;
            }
            case TOP_LEFT -> {
                cellRow--;
                if (cellCoords.x % 2 == 0) cellCol--;
            }
        }

        return new Pair<>(cellRow, cellCol);
    }

    public void extendArea(Color owner, int radius) {
        Pair<Integer, Integer> firstCellCoords = new Pair<>(row, col);
        Pair<Integer, Integer> nextCellCoords;

        Cell firstCell;
        Cell nextCell;

        int repeatActionCnt;

        for (int ringNum = 0; ringNum < radius; ringNum++) {

            firstCellCoords = getNeighbourCoordsByCellCoords(firstCellCoords, LinkDirection.RIGHT);

            if ((firstCell = GameStage.gameBoard.getCell(firstCellCoords)) != null) {
                firstCell.setOwner(owner);
            }

            nextCellCoords = firstCellCoords;

            for (LinkDirection linkDirection : LinkDirection.values()) {

                repeatActionCnt = 0;

                do {

                    nextCellCoords = getNeighbourCoordsByCellCoords(nextCellCoords, linkDirection);

                    if ((nextCell = GameStage.gameBoard.getCell(nextCellCoords)) != null) {
                        nextCell.setOwner(owner);
                    }

                    repeatActionCnt++;
                } while (repeatActionCnt <= ringNum);
            }
        }
    }

    public void setOwner(Color newOwner) {
        this.owner = newOwner;
        this.setBackground(new Background(new BackgroundFill(newOwner, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * links Getter
     *
     * @return Returns Every Link of the Cell
     */
    public HashMap<LinkDirection, Cell> getLinks() {
        return links;
    }


    private final int row;
    private final int col;

    private Color owner;

    private HashMap<LinkDirection, Cell> links = new HashMap<>();
    private Building building;
    private static final Border defaultBorder = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

}
