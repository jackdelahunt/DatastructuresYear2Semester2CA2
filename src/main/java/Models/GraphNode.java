package Models;

import java.util.ArrayList;
import java.util.List;

public class GraphNode<E> {

    private E data;
    private int x, y;
    private String name;

    private List<GraphEdge> adjList = new ArrayList<>();

    public GraphNode(E data) {
        this.data = data;
    }

    public GraphNode(String nodeName, int x, int y) {
        setName(nodeName);
        setX(x);
        setY(y);
    }

    public GraphNode(E data, String nodeName, int x, int y) {
        this.data = data;
        setName(nodeName);
        setX(x);
        setY(y);
    }

    public void connectToNodeDirected(GraphNode<?> destNode, int cost) {
        adjList.add(new GraphEdge(destNode, cost));
    }

    public void connectToNodeUndirected(GraphNode<?> destNode, int cost) {
        adjList.add(new GraphEdge(destNode, cost));
        destNode.adjList.add(new GraphEdge(this, cost));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if (x < 0)
            return;

        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y < 0)
            return;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 24)
            return;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Coordinates: (" + x + ", " + y + ").";
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public List<GraphEdge> getAdjList() {
        return adjList;
    }
}
