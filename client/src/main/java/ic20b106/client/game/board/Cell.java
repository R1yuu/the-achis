package ic20b106.client.game.board;

import ic20b106.client.Game;
import ic20b106.client.game.astar.GraphNode;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.link.Link;
import ic20b106.client.game.buildings.link.LinkDirection;
import ic20b106.client.game.menus.submenus.BuildSubMenu;
import ic20b106.client.game.menus.submenus.LinkSubMenu;
import ic20b106.shared.utils.IntPair;
import ic20b106.shared.utils.Pair;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.EnumMap;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Cells are the Buildings Fields
 */
public class Cell extends Pane implements GraphNode {

    private final IntPair position;
    private final EnumMap<LinkDirection, Pair<Link, Cell>> links = new EnumMap<>(LinkDirection.class);
    private Color owner;
    private Building building;
    private static final Border defaultBorder = new Border(new BorderStroke(
      Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

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


        this.position = new IntPair(row, col);

        this.setId(this.position.toString());

        this.setOnMouseClicked(this::onMouseClick);
    }

    /**
     * MouseEvent
     *
     * @param mouseEvent mouseEvent that gets Triggered on Click
     */
    private void onMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (owner == Game.playerColor.toColor()) {

                if (Game.activeSubMenu != null) {
                    Game.activeSubMenu.close();
                }

                boolean neighbourHasBuilding = false;
                for (LinkDirection linkDirection : LinkDirection.values()) {
                    Cell neighbourCell = getNeighbourByCell(this, linkDirection);
                    if (neighbourCell != null && neighbourCell.building != null) {
                        neighbourHasBuilding = true;
                    }
                }

                try {
                    if (this.building != null) {
                        Game.activeSubMenu = this.building.getBuildingSubMenu();
                    } else if (!this.links.isEmpty() || neighbourHasBuilding) {
                        Game.activeSubMenu = new LinkSubMenu(this);
                    } else {
                        Game.activeSubMenu = new BuildSubMenu(this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Places Buildings
     *
     * @param building A Building that can be built on the Cell
     */
    public void placeBuilding(Building building) {
        if (this.building == null) {
            this.building = building;
            this.getChildren().add(this.building.getTexture());
        }
    }

    /**
     * Removes the Building of this Cell
     */
    public void removeBuilding() {
        if (this.building != null) {
            this.getChildren().remove(this.building.getTexture());
            this.removeLinks(LinkDirection.values());
            this.building = null;
        }
    }

    /**
     * Adds a Link to another Cell just with the Direction
     *
     * @param linkDirections Directions of the Cells to link
     */
    public void addLinks(LinkDirection... linkDirections) {

        for (LinkDirection linkDirection : linkDirections) {
            if (!links.containsKey(linkDirection)) {
                Cell linkCell = getNeighbourByCell(this, linkDirection);
                Link newLink = new Link(linkDirection, this);
                Game.gameBoard.addLink(this, linkCell);

                Pair<Link, Cell> alreadyLinkedCell = this.links.putIfAbsent(linkDirection, new Pair<>(newLink, linkCell));
                if (alreadyLinkedCell == null) {
                    this.getChildren().add(newLink.getTexture());
                    newLink.getTexture().toBack();
                }

                linkCell.addLinks(LinkDirection.getOpposite(linkDirection));
            }
        }
    }

    /**
     * Removes Links from the Cell
     *
     * @param linkDirections Links to be removed
     */
    public void removeLinks(LinkDirection... linkDirections) {
        for (LinkDirection linkDirection : linkDirections) {
            if (links.containsKey(linkDirection)) {
                Link link = links.get(linkDirection).x;
                Cell linkedCell = links.get(linkDirection).y;
                Game.gameBoard.removeLink(this, linkedCell);

                links.remove(linkDirection);

                this.getChildren().remove(link.getTexture());
                linkedCell.removeLinks(LinkDirection.getOpposite(linkDirection));
            }
        }
    }

    /**
     * Gets Neighouring Cell through Cell
     *
     * @param cell Cell to get Neighbour of
     * @param linkDirection Direction of the Neighbour Cell
     * @return Neighbouring Cell
     */
    public static Cell getNeighbourByCell(Cell cell, LinkDirection linkDirection) {
        return Game.gameBoard.getCell(getNeighbourCoordsByCellCoords(
          new IntPair(cell.position.x, cell.position.y), linkDirection));
    }

    /**
     * Gets Neighbouring Cell through Coordinates
     *
     * @param cellCoords Coordinates to get Neighbour of
     * @param linkDirection Direction of the Neighbour Cell
     * @return Neighbouring Cell Coordinates
     */
    public static IntPair getNeighbourCoordsByCellCoords(IntPair cellCoords,
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

        return new IntPair(cellRow, cellCol);
    }

    /**
     * Changes the Cell Owner of every Cell in a given radius
     *
     * @param owner Color of the new Owner
     * @param radius Radius in which the Cells should be Changes
     */
    public void extendArea(Color owner, int radius) {
        IntPair firstCellCoords = new IntPair(position.x, position.y);
        IntPair nextCellCoords;

        Cell firstCell;
        Cell nextCell;

        int repeatActionCnt;

        for (int ringNum = 0; ringNum < radius; ringNum++) {

            firstCellCoords = getNeighbourCoordsByCellCoords(firstCellCoords, LinkDirection.RIGHT);

            if ((firstCell = Game.gameBoard.getCell(firstCellCoords)) != null) {
                firstCell.setOwner(owner);
            }

            nextCellCoords = firstCellCoords;

            for (LinkDirection linkDirection : LinkDirection.values()) {

                repeatActionCnt = 0;

                do {

                    nextCellCoords = getNeighbourCoordsByCellCoords(nextCellCoords, linkDirection);

                    if ((nextCell = Game.gameBoard.getCell(nextCellCoords)) != null) {
                        nextCell.setOwner(owner);
                    }

                    repeatActionCnt++;
                } while (repeatActionCnt <= ringNum);
            }
        }
    }

    /**
     * Setter
     *
     * @param newOwner Color of Owner
     */
    public void setOwner(Color newOwner) {
        this.owner = newOwner;
        this.setBackground(new Background(new BackgroundFill(newOwner, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Getter
     *
     * @return Owner-Color of the Cell
     */
    public Color getOwner() {
        return this.owner;
    }

    /**
     * links Getter
     *
     * @return Returns Every Link of the Cell
     */
    public EnumMap<LinkDirection, Pair<Link, Cell>> getLinks() {
        return links;
    }

    /**
     * Getter
     *
     * @return Position of the Cell
     */
    public IntPair getPosition() {
        return position;
    }

}
