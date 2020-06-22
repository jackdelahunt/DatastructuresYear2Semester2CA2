
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static Scene settingsScene;
    private static Stage settingsStage;

    private static Scene saveScene;
    private static Stage saveStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Rome Route Finder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        settingsScene = new Scene(root);

        root = FXMLLoader.load(getClass().getResource("save.fxml"));
        saveScene = new Scene(root);
    }

    public static void main(String[] args) {
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

    public static void closeSettings() {
        settingsStage.close();
    }

    public static void closeSave() {
        saveStage.close();
    }

    public static GraphNode[] loadNodesFromFile() throws IOException, ClassNotFoundException {

        GraphNode[] nodes;

        XStream xstream = new XStream(new DomDriver());
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("Nodes.xml"));
        nodes = (GraphNode[]) is.readObject();
        is.close();

        return nodes;
    }

    public static void saveNodeToFile(GraphNode[] nodes) throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("Nodes.xml"));
        out.writeObject(nodes);
        out.close();
    }
}
