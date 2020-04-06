import java.util.ArrayList;
import java.util.List;

public class Searching {

    public static void breadthFirst(List<GraphNode<?>> agenda, List<GraphNode<?>> encountered ){
        if(agenda.isEmpty()) return;
        GraphNode<?> next= agenda.remove(0);
        System.out.println(next.getData());
        if(encountered==null) encountered =new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);
        for(GraphNode<?> adjNode : next.getAdjList())
            if(!encountered.contains(adjNode) && !agenda.contains(adjNode)) agenda.add(adjNode); //Add children to
//end of agenda
        breadthFirst(agenda, encountered ); //Tail call
    }

}
