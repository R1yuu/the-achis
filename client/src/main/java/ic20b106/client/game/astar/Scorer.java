package ic20b106.client.game.astar;

public interface Scorer<T extends GraphNode> {
    double computeCost(T from, T to);
}
