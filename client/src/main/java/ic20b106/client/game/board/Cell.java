package ic20b106.client.game.board;

import ic20b106.client.Game;
import ic20b106.client.game.astar.GraphNode;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.link.Link;
import ic20b106.client.game.buildings.link.LinkDirection;
import ic20b106.client.game.menus.submenus.BuildSubMenu;
import ic20b106.client.game.menus.submenus.LinkSubMenu;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.utils.IntPair;
import ic20b106.shared.utils.Pair;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Random;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Cells are the Buildings Fields
 */
public class Cell extends StackPane implements GraphNode {

    private final IntPair position;
    private final EnumMap<LinkDirection, Pair<Link, Cell>> links = new EnumMap<>(LinkDirection.class);
    private final Terrain cellTerrain;
    private PlayerColor owner;
    private Building building;
    private static final Random rand = new Random();

    /**
     * Constructor
     *
     * @param row Row Position of Cell
     * @param col Col Position of Cell
     * @param owner Owner of Cell
     */
    public Cell(int row, int col, PlayerColor owner) {

        this.owner = owner;

        int randInt = rand.nextInt(3);

        if (randInt == 0) {
            this.cellTerrain = Terrain.GRASS;
        } else if (randInt == 1) {
            this.cellTerrain = Terrain.TALL_GRASS;
        } else {
            this.cellTerrain = Terrain.FLOWERS;
        }

        this.setBackground(new Background(this.cellTerrain.bgImg));

        this.setPrefHeight(Game.cellSize);
        this.setPrefWidth(Game.cellSize);

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
     * Getter
     *
     * @return Background Terrain
     */
    public Terrain getCellTerrain() {
        return this.cellTerrain;
    }


    /**
     * Places/Replaces Building
     *
     * @param building A Building that can be built on the Cell
     */
    public void placeBuilding(Building building) {
        if (this.building != null) {
            this.getChildren().remove(this.building.getTexture());
        }

        this.building = building;
        StackPane.setAlignment(this.building.getTexture(), Pos.CENTER);
        this.getChildren().add(this.building.getTexture());
    }

    /**
     * Getter
     *
     * @return Building
     */
    public Building getBuilding() {
        return this.building;
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
    public static IntPair getNeighbourCoordsByCellCoords(IntPair cellCoords, LinkDirection linkDirection) {
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
    public void extendArea(PlayerColor owner, int radius) {
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
    public void setOwner(PlayerColor newOwner) {
        this.owner = newOwner;

        String bgImgPath = newOwner.getPlayerTexturePath() + "/bg-border.png";

        this.setBackground(new Background(this.cellTerrain.bgImg, new BackgroundImage(
          new Image(getClass().getResource(bgImgPath).toString(),
            Game.resolution, 0, true, false, true),
          BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
          new BackgroundSize(Game.cellSize, Game.cellSize, true, true, true, true))));
    }

    /**
     * Getter
     *
     * @return Owner-Color of the Cell
     */
    public PlayerColor getOwner() {
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
