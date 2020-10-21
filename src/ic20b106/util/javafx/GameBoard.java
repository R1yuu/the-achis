package ic20b106.util.javafx;

import ic20b106.Game;
import ic20b106.game.Cell;
import ic20b106.menus.game.EscapeMenuController;
import ic20b106.util.Pair;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Game Board with all the Cells.
 */
public class GameBoard extends GridPane {

    /**
     * Constructor
     * Fills the Board with Cells
     *
     * @param boardWidth Amount of Horizontal Cells
     * @param boardHeight Amount of Vertical Cells
     */
    public GameBoard(int boardWidth, int boardHeight) {

        this.setFocusTraversable(true);

        this.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                if (!Game.escapeMenuOpen) {
                    VBox escapeMenu = null;
                    try {
                        escapeMenu = FXMLLoader.load(getClass().getResource("/ic20b106/menus/game/EscapeMenu.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Game.primaryPane.getChildren().add(escapeMenu);
                } else {
                    EscapeMenuController.closeEscapeMenu();
                }
            }
        });

        Cell cell;

        for (int col=0; col < boardWidth; col++) {
            for (int row=0; row < boardHeight; row++) {
                cell = new Cell(row, col, Color.INDIANRED);
                if (row % 2 == 1) cell.setStyle("-fx-translate-x: 25");

                this.add(cell, col, row);
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
        Cell result = null;

        System.out.println(getChildren().size());

        for (Node child : getChildren()) {
            if(getRowIndex(child) == row && getColumnIndex(child) == column) {
                result = (Cell)child;
                break;
            }
        }

        return result;
    }

    public Cell getCell(final Pair<Integer, Integer> coordinates) {
        return getCell(coordinates.x, coordinates.y);
    }
}
