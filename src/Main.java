
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Rome Route Finder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        test();
        launch(args);
    }

    public static void test() {
        Searching<String> searching = new Searching<>();
        GraphNode<String> a = new GraphNode<>("A");
        GraphNode<String> b = new GraphNode<>("B");
        GraphNode<String> c = new GraphNode<>("C");
        GraphNode<String> d = new GraphNode<>("D");
        GraphNode<String> e = new GraphNode<>("E");

        GraphNode<String> one = new GraphNode<>("1");
        GraphNode<String> two = new GraphNode<>("2");
        GraphNode<String> three = new GraphNode<>("3");
        GraphNode<String> four = new GraphNode<>("4");
        GraphNode<String> five = new GraphNode<>("5");
        GraphNode<String> six = new GraphNode<>("6");
        GraphNode<String> seven = new GraphNode<>("7");
        GraphNode<String> eight = new GraphNode<>("8");

        a.connectToNodeUndirected(b, 1);
        b.connectToNodeUndirected(c, 1);
        c.connectToNodeUndirected(d, 1);
        d.connectToNodeUndirected(e, 1);

        a.connectToNodeUndirected(one, 1);
        one.connectToNodeUndirected(two, 1);
        three.connectToNodeUndirected(e, 1);
        three.connectToNodeUndirected(four, 0);
        four.connectToNodeUndirected(five, 0);
        five.connectToNodeUndirected(six, 0);
        five.connectToNodeUndirected(seven, 0);
        six.connectToNodeUndirected(eight, 0);
        seven.connectToNodeUndirected(eight, 0);

        one.connectToNodeUndirected(six, 0);

        //searching.BFS(a, eight);
    }
}
