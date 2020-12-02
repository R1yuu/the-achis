package ic20b106.client.game.board;

import ic20b106.client.game.astar.Scorer;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Scorer that calculates the distance from a Cell to a given Cell
 */
public class CellScorer implements Scorer<Cell> {
    /**
     * Computes the Cost between two Cells
     *
     * @param from Start Node
     * @param to End Node
     * @return Computed Travel Cost
     */
    @Override
    public double computeCost(Cell from, Cell to) {
        return Math.max(
          Math.abs(from.getPosition().x - to.getPosition().x),
          Math.abs(from.getPosition().y - to.getPosition().y)
        );
    }
}
