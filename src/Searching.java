import java.util.*;


public class Searching <E> implements Runnable{

    private GraphNode<E> start;
    private GraphNode<E> end;
    private List<GraphNode<E>> path;


    public Searching(GraphNode<E> start, GraphNode<E> end){
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        BFS();
    }

    private void BFS() {

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

    private List<GraphNode<E>> getNodeListFromMap(Map<GraphNode<E>, GraphNode<E>> map, GraphNode<E> start, GraphNode<E> end) {
        GraphNode<E> currentNode = end;
        List<GraphNode<E>> nodeList = new ArrayList<>();
        while(!currentNode.equals(start)){
            nodeList.add(currentNode);
            currentNode = map.get(currentNode);
        }
        return nodeList;
    }

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

    public static List<GraphNode> addNodePaths (List< List<GraphNode> > paths) {
        List<GraphNode> finalPath = new ArrayList<>();

        for (List<GraphNode> path : paths) {
            finalPath = new ArrayList<>(path);
        }

        return finalPath;
    }
}
