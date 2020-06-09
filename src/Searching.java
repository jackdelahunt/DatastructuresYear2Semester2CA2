import javafx.scene.paint.Color;

import java.util.*;

public class Searching <E> {

    public List<GraphNode<E>> BFS(GraphNode<E> start, GraphNode<E> end) {

        // this is the queue of the nodes to look at
        Queue<GraphNode<E>> nodeQueue = new LinkedList<>();

        // set of nodes seen so far
        Set<GraphNode<E>> seen = new HashSet<>();

        Map<GraphNode<E>, GraphNode<E>> map = new HashMap<>();

        nodeQueue.add(start);

        while(!nodeQueue.isEmpty()){
            System.out.println(nodeQueue.size());
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
        return getNodeListFromMap(map, start, end);
    }

    private List<GraphNode<E>> getNodeListFromMap(Map<GraphNode<E>, GraphNode<E>> map, GraphNode<E> start, GraphNode<E> end) {
        GraphNode<E> currentNode = end;
        List<GraphNode<E>> nodeList = new ArrayList<>();
        while(!currentNode.equals(start)){
            nodeList.add(currentNode);
            currentNode = map.get(currentNode);
        }
        return nodeList;
    }
}
