package ic20b106.board;

import ic20b106.board.buildings.Core;
import ic20b106.board.buildings.Link;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.Random;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * The Game Board with all the Cells.
 */
public class Board extends GridPane {

    /**
     * Constructor
     * Fills the Board with Cells
     *
     * @param boardWidth Amount of Horizontal Cells
     * @param boardHeight Amount of Vertical Cells
     */
    public Board(int boardWidth, int boardHeight) {

        Cell cell;

        for (int col=0; col < boardWidth; col++) {
            for (int row=0; row < boardHeight; row++) {
                cell = new Cell(row, col, 50, 50);
                if (row % 2 == 1) cell.setStyle("-fx-translate-x: 25");

                this.add(cell, col, row);
            }
        }

        Random coreRdm = new Random();
        int coreX = Math.abs(coreRdm.nextInt() % boardWidth - 10);
        int coreY = Math.abs(coreRdm.nextInt() % boardHeight - 10);

        System.out.println("X: " + coreX + ", Y: " + coreY);

        getCell(coreX, coreY).setBuilding(new Core(coreX, coreY));
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

        for (Node child : getChildren()) {
            if(getRowIndex(child) == row && getColumnIndex(child) == column) {
                result = (Cell)child;
                break;
            }
        }

        return result;
    }
}
