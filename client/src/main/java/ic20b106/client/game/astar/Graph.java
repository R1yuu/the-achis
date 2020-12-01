package ic20b106.client.game.astar;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Graham Cox
 * @version Last modified: October 3. 2020
 * https://www.baeldung.com/java-a-star-pathfinding
 *
 * @param <T> Node of the Graph
 */
public class Graph<T extends GraphNode> {
    private final Collection<T> nodes;
    private final Map<String, Set<String>> connections;

    /**
     * Constructor
     *
     * @param nodes Nodes that build the Graph
     * @param connections Connections in the Graph
     */
    public Graph(Collection<T> nodes, Map<String, Set<String>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    /**
     * Gets Node from the Graph
     *
     * @param id Id of the Node
     * @return Returns the corresponding Node
     */
    public T getNode(String id) {
        return nodes.stream()
          .filter(node -> node.getId().equals(id))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
    }

    /**
     * Gets the Connections of a Node
     *
     * @param node Node to gets Connections of
     * @return Returns a Set of Connections
     */
    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
          .map(this::getNode)
          .collect(Collectors.toSet());
    }
}
