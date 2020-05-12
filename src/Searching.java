import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Searching {

    // cost of last is the cost of the route last found, once you
    // find a path it then stores the cost instead of having to repeat the
    // search, this may lead to the wrong cost being displayed eventually
    // but for now it worked
    static int costOfLast = 0;

    public static <T> List<GraphNode<?>> findPathBreadthFirst(GraphNode<?> startNode, GraphNode<?> endNode, int totalCost) {

        costOfLast = totalCost;

        List<List<GraphNode<?>>> agenda = new ArrayList<>(); //Agenda comprised of path lists here!
        List<GraphNode<?>> firstAgendaPath = new ArrayList<>(), resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath = findPathBreadthFirst(agenda, null, endNode); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
    }

    public static <T> List<GraphNode<?>> findPathBreadthFirst(List<List<GraphNode<?>>> agenda, List<GraphNode<?>> encountered, GraphNode<?> endNode) {
        if (agenda.isEmpty()) return null; //Search failed
        List<GraphNode<?>> nextPath = agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNode<?> currentNode = nextPath.get(0); //The first item in the next path is the current node

        System.out.println(currentNode.getxCoordinate() + " : " + currentNode.getyCoordinate());
        if (currentNode.equals(endNode)) {
            return nextPath; //If that's the goal, we've found our path (so return it)
        }
        if (encountered == null)
            encountered = new ArrayList<>(); //First node considered in search so create new (empty) encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again

        List<GraphEdge> currentAdjList = currentNode.getAdjList();
        List<GraphNode<?>> adjNodes = new ArrayList<>();

        for (GraphEdge edge : currentAdjList)
            adjNodes.add(edge.getDestinationNode());

        for (int i = 0; i < adjNodes.size(); i++)
            if (!encountered.contains(adjNodes.get(i))) { //If it hasn't already been encountered
                List<GraphNode<?>> newPath = new ArrayList<>(nextPath); //Create a new path list as a copy of

                costOfLast += currentAdjList.get(i).getCost();

                newPath.add(0, adjNodes.get(i)); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda, encountered, endNode); //Tail call
    }

    //Adjusted above method to work for the cultural nodes

//    public static <T> List<GraphNode<?>> findPathBreadthFirst(GraphNode<?> startNode, GraphNode<?> destinationNode, int totalCost) {
//
//        costOfLast = totalCost;
//
//        List<List<GraphNode<?>>> agenda = new ArrayList<>(); //Agenda comprised of path lists here!
//        List<GraphNode<?>> firstAgendaPath = new ArrayList<>(), resultPath;
//        firstAgendaPath.add(startNode);
//        agenda.add(firstAgendaPath);
//        resultPath = findPathBreadthFirst(agenda, null, destinationNode); //Get single BFS path (will be shortest)
//        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
//        return resultPath;
//    }
//
//    public static <T> List<GraphNode<?>> findPathBreadthFirst(List<List<GraphNode<?>>> agenda, List<GraphNode<?>> encountered, GraphNode<?> destinationNode) {
//        if (agenda.isEmpty()) return null; //Search failed
//        List<GraphNode<?>> nextPath = agenda.remove(0); //Get first item (next path to consider) off agenda
//        GraphNode<?> currentNode = nextPath.get(0); //The first item in the next path is the current node
//
//        if (currentNode.equals(destinationNode)) {
//            return nextPath; //If that's the goal, we've found our path (so return it)
//        }
//        if (encountered == null)
//            encountered = new ArrayList<>(); //First node considered in search so create new (empty) encountered list
//        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
//
//        List<GraphEdge> currentAdjList = currentNode.getAdjList();
//        List<GraphNode<?>> adjNodes = new ArrayList<>();
//
//        for (GraphEdge edge : currentAdjList)
//            adjNodes.add(edge.getDestinationNode());
//
//        for (int i = 0; i < adjNodes.size(); i++)
//            if (!encountered.contains(adjNodes.get(i))) { //If it hasn't already been encountered
//                List<GraphNode<?>> newPath = new ArrayList<>(nextPath); //Create a new path list as a copy of
//
//                costOfLast += currentAdjList.get(i).getCost();
//
//                newPath.add(0, adjNodes.get(i)); //And add the adjacent node to the front of the new copy
//                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
//            }
//        return findPathBreadthFirst(agenda, encountered, destinationNode); //Tail call
//    }
}
