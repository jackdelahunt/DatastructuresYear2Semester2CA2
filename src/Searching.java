import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Searching {

    public static void breadthFirstTraverse(List<GraphNode<?>> agenda, List<GraphNode<?>> encountered ){
        if(agenda.isEmpty()) return;
        GraphNode<?> next= agenda.remove(0);
        System.out.println(next.getData());
        if(encountered==null) encountered =new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);
        for(GraphNode<?> adjNode : next.getAdjList())
            if(!encountered.contains(adjNode) && !agenda.contains(adjNode)) agenda.add(adjNode); //Add children to
//end of agenda
        breadthFirstTraverse(agenda, encountered ); //Tail call
    }

    public static <T> List<GraphNode<?>> findPathBreadthFirst(GraphNode<?> startNode, T lookingfor){
        List<List<GraphNode<?>>> agenda=new ArrayList<>(); //Agenda comprised of path lists here!
        List<GraphNode<?>> firstAgendaPath=new ArrayList<>(),resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath=findPathBreadthFirst(agenda,null,lookingfor); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
    }

    public static <T> List<GraphNode<?>> findPathBreadthFirst(List<List<GraphNode<?>>> agenda,
                                                                List<GraphNode<?>> encountered, T lookingFor){
        if(agenda.isEmpty()) return null; //Search failed
        List<GraphNode<?>> nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNode<?> currentNode=nextPath.get(0); //The first item in the next path is the current node
        if(currentNode.getData().equals(lookingFor)) return nextPath; //If that's the goal, we've found our path (so return it)
        if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty) encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for(GraphNode<?> adjNode : currentNode.getAdjList()) //For each adjacent node
            if(!encountered.contains(adjNode)) { //If it hasn't already been encountered
                List<GraphNode<?>> newPath=new ArrayList<>(nextPath); //Create a new path list as a copy of
//the current/next path
                newPath.add(0,adjNode); //And add the adjacent node to the front of the new copy
                agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
            }
        return findPathBreadthFirst(agenda,encountered, lookingFor); //Tail call
    }

}
