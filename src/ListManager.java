import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Manages list of landmark nodes read from the database file
public class ListManager {

    private static ObservableList<GraphNode<?>> nodesList;

    public static void initialize() {
        nodesList = FXCollections.observableArrayList();
    }

    public static GraphNode<?> getNodeByName(String name) {
        for (int i = 0; i < getNodesList().size(); i++) {
            GraphNode<?> graphNode = nodesList.get(i);
            if (graphNode.getName().equals(name)) {
                return graphNode;
            }
        }
        return null;
    }

    public static ObservableList<GraphNode<?>> getNodesList() {
        return nodesList;
    }
}