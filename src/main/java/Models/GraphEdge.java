package Models;

public class GraphEdge {

    private GraphNode<?> destinationNode;
    private int cost;

    public GraphEdge(GraphNode<?> destinationNode, int cost) {
        this.destinationNode = destinationNode;
        this.cost = cost;
    }

    public GraphNode<?> getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(GraphNode<?> destinationNode) {
        this.destinationNode = destinationNode;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        if (cost < 0)
            cost = 0;
        this.cost = cost;
    }

    public String toString() {
        return destinationNode.getName() + "(" + cost  + ")";
    }
}
