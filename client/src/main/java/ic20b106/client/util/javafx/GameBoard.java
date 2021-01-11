package ic20b106.client.util.javafx;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.menus.EscapeMenuController;
import ic20b106.client.game.board.CellScorer;
import ic20b106.client.game.astar.Graph;
import ic20b106.client.game.astar.RouteFinder;
import ic20b106.shared.PlayerColor;
import ic20b106.shared.utils.IntPair;
import ic20b106.shared.utils.Pair;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Game Board with all the Cells.
 */
public class GameBoard extends GridPane {

    public Map<String, Set<String>> links = new HashMap<>();

    /**
     * Constructor
     * Fills the Board with Cells
     *
     * @param boardWidth Amount of Horizontal Cells
     * @param boardHeight Amount of Vertical Cells
     */
    public GameBoard(int boardWidth, int boardHeight) {

        this.setFocusTraversable(true);

        this.setOnKeyPressed(this::onKeyPressed);

        Cell cell;

        for (int col=0; col < boardWidth; col++) {
            for (int row=0; row < boardHeight; row++) {
                cell = new Cell(row, col, PlayerColor.NONE);
                if (row % 2 == 1) cell.setStyle("-fx-translate-x: 25");

                this.add(cell, col, row);
            }
        }
    }

    /**
     * On KeyPressed Event Handler
     *
     * @param keyEvent Key related Event
     */
    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            if (!Game.escapeMenuOpen) {
                VBox escapeMenu = null;
                try {
                    escapeMenu = FXMLLoader.load(getClass().getResource("/fxml/menus/EscapeMenu.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Game.primaryPane.getChildren().add(escapeMenu);
            } else {
                EscapeMenuController.closeEscapeMenu();
            }
        }
    }

    /**
     * Credit: https://stackoverflow.com/a/20828724
     * Changed up for Project use
     *
     * @param row Row of the Cell
     * @param column Column of the Cell
     * @return Returns Found Cell
     */
    public Cell getCell (final int row, final int column) {
        for (Node child : getChildren()) {
            if(GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column) {
                return (Cell) child;
            }
        }
        return null;
    }

    /**
     * Get Cell for intPairs
     *
     * @param coordinates IntPair of Position
     * @return Cell on position
     */
    public Cell getCell(final IntPair coordinates) {
        return getCell(coordinates.x, coordinates.y);
    }

    /**
     * Adds Link between two Cells
     *
     * @param from From Cell
     * @param to To Cell
     */
    public void addLink(Cell from, Cell to) {
        String fromId = from.getPosition().toString();
        String toId = to.getPosition().toString();
        Set<String> fromConns = links.get(fromId);
        Set<String> toConns = links.get(toId);

        if (fromConns != null) {
            fromConns.add(toId);
        } else {
            Game.gameBoard.links.put(fromId, Stream.of(toId).collect(Collectors.toSet()));
        }

        if (toConns != null) {
            toConns.add(fromId);
        } else {
            Game.gameBoard.links.put(toId, Stream.of(fromId).collect(Collectors.toSet()));
        }
    }

    /**
     * Removes Link between two Cells
     *
     * @param from From Cell
     * @param to To Cell
     */
    public void removeLink(Cell from, Cell to) {
        String fromId = from.getPosition().toString();
        String toId = to.getPosition().toString();
        Set<String> fromConns = links.get(fromId);
        Set<String> toConns = links.get(toId);

        if (fromConns != null) {
            fromConns.remove(toId);
        }

        if (toConns != null) {
            toConns.remove(fromId);
        }
    }

    /**
     * Find Route between two Cells
     *
     * @param from From Cell
     * @param to To Cell
     * @return List of Route Cells
     */
    public List<Cell> findRoute(Cell from, Cell to) {
        HashSet<Cell> cells = new HashSet<>();
        for (Node child : getChildren()) {
            if (child.getClass() == Cell.class) {
                cells.add((Cell) child);
            }
        }

        Graph<Cell> cellGraph = new Graph<>(cells, links);
        RouteFinder<Cell> cellRouteFinder = new RouteFinder<>(cellGraph, new CellScorer(), new CellScorer());
        return cellRouteFinder.findRoute(from, to);
    }
}
