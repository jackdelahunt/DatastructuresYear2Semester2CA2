package Models;

import Models.GraphEdge;
import Models.GraphNode;

import java.util.*;


public class Searching <E> {

    // the node that the path starts at
    private GraphNode<E> start;

    // the node that the path ends at
    private GraphNode<E> end;

    // the path from start --> end
    private List<GraphNode<E>> path;


    // do construction
    public Searching(GraphNode<E> start, GraphNode<E> end){
        this.start = start;
        this.end = end;
    }

    /*
    runs the BFS algorithm from the start node to the end node
    and sets the path field to the resulting path found : if any
     */
    public void BFS() {

        // if either node that is picked is null or generally a black pixel in the b&w image
        // then cancel the BFS and leave path as null
        if(start == null || end == null){
            return;
        }

        // this is the queue of the nodes to look at
        Queue<GraphNode<E>> nodeQueue = new LinkedList<>();

        // set of nodes seen so far
        Set<GraphNode<E>> seen = new HashSet<>();

        // this map is used to trace back the path to the start node once the end node is found
        Map<GraphNode<E>, GraphNode<E>> map = new HashMap<>();

        // adds the start node to the queue to be polled first
        nodeQueue.add(start);

        while(!nodeQueue.isEmpty()){

            // set the current node to the node at the top of the queue
            GraphNode<E> currentNode = nodeQueue.poll();

            // if we have found the end then stop this loop
            if(currentNode.equals(end)){
                break;
            }

            // go through each edge and add the node it's pointing too, to the queue
            // the add said node to the seen set and add that addition to the map
            for(GraphEdge edge : currentNode.getAdjList()){
                GraphNode<E> nextNode  = (GraphNode<E>) edge.getDestinationNode();
                if(!seen.contains(nextNode)){
                    seen.add(nextNode);
                    nodeQueue.add(nextNode);
                    map.put(nextNode, currentNode);
                }
            }
        }

        // sets path to the path found from the start node to the end node using the map that was created
        path =  getNodeListFromMap(map, start, end);
    }

    /*
    runs a dijkstra's search on the map to find the shortest path between
    the start node and the end node
     */
    public void dijkstra(GraphNode<E>[] graph) {

        // queue for which node to poll next
        Queue<GraphNode<E>> queue = new LinkedList<>();

        // shows which nodes are connected
        Map<GraphNode<E>, GraphNode<E>> previous = new HashMap<>();

        // gets the cost of the node from the start
        Map<GraphNode<E>, Integer> cost = new HashMap<>();

        // go through each node in the array and set its cost
        // to infinity
        for(GraphNode<E> node : graph) {
            if(node.equals(start))
                cost.put(node, 0);
            else
                cost.put(node, Integer.MAX_VALUE);
        }

        // add the start node to the queue as that is where we need
        // to start from
        queue.add(start);

        // while our queue is not empty then keep updating costs
        while(queue.size() != 0){

            // get the next node in the queue and remove it from the queue
            GraphNode<E> current = queue.poll();

            // go through each edge and test
            for(GraphEdge edge : current.getAdjList()){

                // this is the node that the current node is connected to
                // with the current edge we are on
                GraphNode nextNode = edge.getDestinationNode();

                // cost of the current node to the next node
                int nextCost = edge.getCost();

                // if the cost form the current node + cost of the connection is less
                // then the path we already take then update
                if(cost.get(current) + nextCost < cost.get(nextNode)){

                    // set the new cost of the node
                    cost.put(nextNode, cost.get(current) + nextCost);

                    // update its connecting node
                    previous.put(nextNode, current);

                    // add it to the queue as we need to test it's adjacent list
                    queue.add(nextNode);
                }
            }
        }

        // generate the path from the map we created, same as BFS
        path = getNodeListFromMap(previous, start, end);
    }

    /**
     * generates a path based on the start node and end node with the map the BFS method generates
     * @param map the map that is storing the micro-paths between each node (I made this expression up)
     * @param start the node that all nodes in the map point to
     * @param end the node that you want to trace from start
     * @return the path made from start to end (in reverse)
     */
    private List<GraphNode<E>> getNodeListFromMap(Map<GraphNode<E>, GraphNode<E>> map, GraphNode<E> start, GraphNode<E> end) {
        GraphNode<E> currentNode = end;
        List<GraphNode<E>> nodeList = new ArrayList<>();
        while(!currentNode.equals(start)){
            nodeList.add(currentNode);
            currentNode = map.get(currentNode);
        }
        return nodeList;
    }

    /*

    getters and setters - nothing exciting

     */
    public GraphNode<E> getStart() {
        return start;
    }

    public void setStart(GraphNode<E> start) {
        this.start = start;
    }

    public GraphNode<E> getEnd() {
        return end;
    }

    public void setEnd(GraphNode<E> end) {
        this.end = end;
    }

    public List<GraphNode<E>> getPath() {
        return path;
    }

    /**
     * adds paths from multiple BFS's into one large path
     * @param paths the paths that you want to join into one
     * @return the new single path of all the individual ones in order
     */
    public static List<GraphNode<?>> addNodePaths (List< List<GraphNode<?>> > paths) {
        List<GraphNode<?>> finalPath = new ArrayList<>(paths.get(0));

        for (int i = 1; i < paths.size(); i++) {
            finalPath.addAll(paths.get(i));
        }

        return finalPath;
    }

}
