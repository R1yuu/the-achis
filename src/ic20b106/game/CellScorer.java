package ic20b106.game;

import ic20b106.game.astar.Scorer;

public class CellScorer implements Scorer<Cell> {
    @Override
    public double computeCost(Cell from, Cell to) {
        return Math.max(
          Math.abs(from.getPosition().x - to.getPosition().x),
          Math.abs(from.getPosition().y - to.getPosition().y)
        );
    }
}