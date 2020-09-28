package ic20b106.board;

import ic20b106.board.buildings.Core;
import javafx.beans.NamedArg;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
    public Board(@NamedArg("boardWidth") int boardWidth, @NamedArg("boardHeight") int boardHeight) {

        Cell cell;

        for (int width=0; width < boardWidth; width++) {
            for (int height=0; height < boardHeight; height++) {
                cell = new Cell();
                cell.setPrefHeight(50);
                cell.setPrefWidth(50);
                cell.setBorder(
                        new Border(
                                new BorderStroke(
                                        new Color(0, 0, 0, 1),
                                        new BorderStrokeStyle(null, null, null, 1, 1, null),
                                        new CornerRadii(10),
                                        new BorderWidths(5))));

                //cell.setBuilding(new Core());
                this.add(cell, width, height);
            }
        }
    }
}
