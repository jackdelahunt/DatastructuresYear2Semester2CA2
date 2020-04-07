import java.util.ArrayList;
import java.util.List;

public class GraphNode<E> {

    private E data;
    private int xCoordinate, yCoordinate;
    private String name;

    private List<GraphNode<E>> adjList = new ArrayList<>();

    public GraphNode(E data) {
        this.data = data;
    }

    public GraphNode(String nodeName, int x, int y) {
        this.setName(nodeName);
        this.setxCoordinate(x);
        this.setyCoordinate(y);
    }

    public void connectToNodeDirected(GraphNode<E> destNode) {
        adjList.add(destNode);
    }

    public void connectToNodeUndirected(GraphNode<E> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Coordinates: (" + xCoordinate + "," + yCoordinate + ").";
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
