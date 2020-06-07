import javafx.scene.paint.Color;

import java.util.*;

public class Searching <E> {

    public void BFS(GraphNode<E> start, GraphNode<E> end) {

        // this is the queue of the nodes to look at
        Queue<GraphNode<E>> nodeQueue = new LinkedList<>();

        // set of nodes seen so far
        Set<GraphNode<E>> seen = new HashSet<>();

        Map<GraphNode<E>, GraphNode<E>> map = new HashMap<>();

        nodeQueue.add(start);

        while(!nodeQueue.isEmpty()){
            GraphNode<E> currentNode = nodeQueue.poll();

            if(!seen.contains(currentNode)) {
                seen.add(currentNode);
            }

            if(currentNode.equals(end)){
                break;
            }

            for(GraphEdge edge : currentNode.getAdjList()){
                GraphNode<E> nextNode  = (GraphNode<E>) edge.getDestinationNode();
                if(!seen.contains(nextNode)){
                    nodeQueue.add(nextNode);
                    map.put(nextNode, currentNode);
                }
            }
        }
        printMap(map, start, end);
    }

    private void printMap(Map<GraphNode<E>, GraphNode<E>> map, GraphNode<E> start, GraphNode<E> end) {
        GraphNode<E> currentNode = end;
        while(!currentNode.equals(start)){
            System.out.println(currentNode.getxCoordinate() + " : " + currentNode.getyCoordinate());
            currentNode = map.get(currentNode);
        }
    }
}
