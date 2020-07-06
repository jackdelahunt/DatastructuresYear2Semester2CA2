package Models;

import Models.GraphNode;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    private static Scene settingsScene;
    private static Stage settingsStage;

    private static Scene saveScene;
    private static Stage saveStage;

    private static Scene finderScene;
    private static Stage finderStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../main.fxml"));
        primaryStage.setTitle("Rome Route Finder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        root = FXMLLoader.load(getClass().getResource("../settings.fxml"));
        settingsScene = new Scene(root);

        root = FXMLLoader.load(getClass().getResource("../save.fxml"));
        saveScene = new Scene(root);

        root = FXMLLoader.load(getClass().getResource("../finder.fxml"));
        finderScene = new Scene(root);
    }

    public static void main(String[] args) {
        try {
            saveNodeToFile(getLandmarks());
        } catch (Exception e) {
            System.out.println(e);
        }
        launch(args);
    }

    public static void openSettingsStage() {
        settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        settingsStage.setScene(settingsScene);

        settingsStage.show();
    }

    public static void openSaveStage() {
        saveStage = new Stage();
        saveStage.setTitle("Save");
        saveStage.setScene(saveScene);

        saveStage.show();
    }

    public static void openFinderStage() {
        finderStage = new Stage();
        finderStage.setTitle("Finder");
        finderStage.setScene(finderScene);

        finderStage.show();
    }

    public static void closeSettings() {
        settingsStage.close();
    }

    public static void closeSave() {
        saveStage.close();
    }

    public static GraphNode<String>[] loadNodesFromFile() throws IOException, ClassNotFoundException {

        GraphNode<String>[] nodes;

        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("saves/Nodes.xml"));
        nodes = (GraphNode<String>[]) is.readObject();
        is.close();

        return nodes;
    }

    public static void saveNodeToFile(GraphNode<String>[] nodes) throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("saves/Nodes.xml"));
        out.writeObject(nodes);
        out.close();
    }

    public static GraphNode<String>[] getLandmarks() {
        GraphNode<String> vatican = new GraphNode<>("Vatican City", "Vatican City", 327, 361);
        GraphNode<String> pantheon = new GraphNode<>("Pantheon", "Pantheon", 670, 492);
        GraphNode<String> forum = new GraphNode<>("Roman Forum", "Roman Forum", 756, 601);
        GraphNode<String> colosseum = new GraphNode<>("The Colosseum", "The Colosseum", 857, 644);
        GraphNode<String> trevi = new GraphNode<>("Trevi Fountain", "Trevi Fountain", 734, 451);

        vatican.connectToNodeUndirected(pantheon, 10);
        trevi.connectToNodeUndirected(pantheon, 10);
        pantheon.connectToNodeUndirected(forum, 10);
        forum.connectToNodeUndirected(colosseum, 10);
        
        GraphNode<String>[] landmarks = new GraphNode[5];

        landmarks[0] = vatican;
        landmarks[1] = pantheon;
        landmarks[2] = forum;
        landmarks[3] = colosseum;
        landmarks[4] = trevi;

        return landmarks;
    }
}
