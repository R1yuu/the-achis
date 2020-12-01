package ic20b106.client.game.astar;

/**
 * @author Graham Cox
 * @version Last modified: October 3. 2020
 * https://www.baeldung.com/java-a-star-pathfinding
 *
 * @param <T> Nodes that build the Graph
 */
public class RouteNode<T extends GraphNode> implements Comparable<RouteNode<T>> {
    private final T current;
    private T previous;
    private double routeScore;
    private double estimatedScore;

    /**
     * Constructor
     *
     * @param current Current Node
     */
    RouteNode(T current) {
        this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }


    /**
     * Constructor
     *
     * @param current Current Node
     * @param previous Last Node
     * @param routeScore Score of the whole route
     * @param estimatedScore Estimated Score
     */
    RouteNode(T current, T previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    /**
     * Getter
     *
     * @return Returns current Node
     */
    public T getCurrent() {
        return current;
    }

    /**
     * Getter
     *
     * @return Returns previous Node
     */
    public T getPrevious() {
        return previous;
    }

    /**
     * Setter
     *
     * @param previous Sets previous Node
     */
    public void setPrevious(T previous) {
        this.previous = previous;
    }

    /**
     * Getter
     *
     * @return Gets the Route Score
     */
    public double getRouteScore() {
        return routeScore;
    }

    /**
     * Setter
     *
     * @param routeScore Sets the Route Score
     */
    public void setRouteScore(double routeScore) {
        this.routeScore = routeScore;
    }

    /**
     * Setter
     *
     * @param estimatedScore Sets the Estimated Score
     */
    public void setEstimatedScore(double estimatedScore) {
        this.estimatedScore = estimatedScore;
    }

    /**
     * compareTo Override
     *
     * @param other Other RouteNode to Compare to
     * @return Returns 1, 0, -1 if the estimated score is greater, equal, smaller
     */
    @Override
    public int compareTo(RouteNode other) {
        if (this.estimatedScore > other.estimatedScore) {
            return 1;
        } else if (this.estimatedScore < other.estimatedScore) {
            return -1;
        } else {
            return 0;
        }
    }
}
