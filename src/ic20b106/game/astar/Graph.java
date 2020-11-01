package ic20b106.game.astar;

import ic20b106.util.IntPair;
import ic20b106.util.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<T extends GraphNode> {
    private final Collection<T> nodes;
    private final Map<String, Set<String>> connections;

    public Graph(Collection<T> nodes, Map<String, Set<String>> connections) {
        this.nodes = nodes;
        this.connections = connections;
    }

    public T getNode(String id) {
        return nodes.stream()
          .filter(node -> node.getId().equals(id))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
    }

    public Set<T> getConnections(T node) {
        return connections.get(node.getId()).stream()
          .map(this::getNode)
          .collect(Collectors.toSet());
    }
}
