import java.util.ArrayList;
import java.util.List;

public class GraphNode<E> {

    private E data;

    private List<GraphNode<E>> adjList = new ArrayList<>();

    public GraphNode(E data) {
        this.data = data;
    }

    public void connectToNodeDirected(GraphNode<E> destNode) {
        adjList.add(destNode);
    }
    public void connectToNodeUndirected(GraphNode<E> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public List<GraphNode<E>> getAdjList() {
        return adjList;
    }

    public void setAdjList(List<GraphNode<E>> adjList) {
        this.adjList = adjList;
    }
}
