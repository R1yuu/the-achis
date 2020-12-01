package ic20b106.client.game.astar;

/**
 * @author Graham Cox
 * @version Last modified: October 3. 2020
 * https://www.baeldung.com/java-a-star-pathfinding
 *
 * @param <T> Nodes that build the Graph
 */
public interface Scorer<T extends GraphNode> {
    /**
     * @param from Start Node
     * @param to End Node
     * @return Computed Travel Cost
     */
    double computeCost(T from, T to);
}
