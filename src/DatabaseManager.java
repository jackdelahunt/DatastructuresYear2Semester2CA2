import javafx.collections.ObservableList;

import java.io.*;

//Manages the database files
public class DatabaseManager {

    public static void loadAll() {
        loadListFromTextFile(ListManager.getNodesList());
    }

    private static void loadListFromTextFile(ObservableList<GraphNode<?>> list) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/landmarks.txt"));

            String line;

            if (list == ListManager.getNodesList()) {
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length != 3) {
                        reader.close();
                        throw new IOException("Invlaid line in file " + line);
                    }
                    String name = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    GraphNode<?> node = new GraphNode<>(name, x, y);
                    MainController.agendaList.add(node);
                    System.out.println(node.toString());
                }
                reader.close();
            } else {
                throw new IOException("Unsupported List");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}