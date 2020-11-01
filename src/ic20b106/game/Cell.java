package ic20b106.game;

import ic20b106.Game;
import ic20b106.game.astar.GraphNode;
import ic20b106.game.buildings.Building;
import ic20b106.game.buildings.core.Core;
import ic20b106.game.menus.BuildSubMenu;
import ic20b106.game.menus.LinkSubMenu;
import ic20b106.util.Pair;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Cells are the Buildings Fields
 */
public class Cell extends StackPane implements GraphNode {

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


        this.position = new Pair<>(row, col);

        this.setId(this.position.toString());

        this.setOnMouseClicked(this::onMouseClick);
    }

    private void onMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (owner == Game.playerColor) {

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
                Link newLink = new Link(linkDirection);
                Game.gameBoard.addLink(this, linkCell);

                Pair<Link, Cell> alreadyLinkedCell = this.links.putIfAbsent(linkDirection, new Pair<>(newLink, linkCell));
                if (alreadyLinkedCell == null) {
                    this.getChildren().add(newLink);
                    newLink.toBack();
                }

                linkCell.addLinks(LinkDirection.getOpposite(linkDirection));
            }
        }
    }

    public void removeLinks(LinkDirection... linkDirections) {
        for (LinkDirection linkDirection : linkDirections) {
            if (links.containsKey(linkDirection)) {
                Link link = links.get(linkDirection).x;
                Cell linkedCell = links.get(linkDirection).y;
                Game.gameBoard.removeLink(this, linkedCell);

                links.remove(linkDirection);

                this.getChildren().remove(link);
                linkedCell.removeLinks(LinkDirection.getOpposite(linkDirection));
            }
        }
    }

    public static Cell getNeighbourByCell(Cell cell, LinkDirection linkDirection) {
        return Game.gameBoard.getCell(getNeighbourCoordsByCellCoords(
          new Pair<>(cell.position.x, cell.position.y), linkDirection));
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
        Pair<Integer, Integer> firstCellCoords = new Pair<>(position.x, position.y);
        Pair<Integer, Integer> nextCellCoords;

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

    public void setOwner(Color newOwner) {
        this.owner = newOwner;
        this.setBackground(new Background(new BackgroundFill(newOwner, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public Color getOwner() {
        return this.owner;
    }

    /**
     * links Getter
     *
     * @return Returns Every Link of the Cell
     */
    public HashMap<LinkDirection, Pair<Link, Cell>> getLinks() {
        return links;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public void scoreToCore() {
        Cell curr = this;
        ArrayList<Cell> prevCells = new ArrayList<>();

        while (curr != null) {
            if (curr.building != null && curr.building.getClass() == Core.class) {
                break;
            }

            boolean nextCurr = false;

            for (LinkDirection linkDirection : LinkDirection.values()) {
                if (curr.links.containsKey(linkDirection)) {
                    Cell linkCell = curr.links.get(linkDirection).y;

                    if (!prevCells.contains(linkCell)) {
                        prevCells.add(curr);
                        curr = linkCell;
                        nextCurr = true;
                        break;
                    }
                }
            }

            if (!nextCurr) {
                scoresToCore.add(-1);
            }
        }

        scoresToCore.add(prevCells.size());
    }

    public ArrayList<Integer> scoresToCore = new ArrayList<>();

    private final Pair<Integer, Integer> position;

    private final HashMap<LinkDirection, Pair<Link, Cell>> links = new HashMap<>();
    private Color owner;
    private Building building;
    private static final Border defaultBorder = new Border(new BorderStroke(
            Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

}
