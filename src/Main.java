
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

        GraphNode<String> a = new GraphNode<>("Cherry");
        GraphNode<String> b = new GraphNode<>("Apple");
        GraphNode<String> c = new GraphNode<>("Plum");
        GraphNode<String> d = new GraphNode<>("Mango");
        GraphNode<String> e = new GraphNode<>("Kiwi");
        GraphNode<String> f = new GraphNode<>("Coconut");
        GraphNode<String> g = new GraphNode<>("Pear");
        GraphNode<String> h = new GraphNode<>("Orange");


        a.connectToNodeDirected(b);
        a.connectToNodeDirected(c);
        b.connectToNodeDirected(c);
        b.connectToNodeDirected(g);
        c.connectToNodeDirected(d);
        g.connectToNodeDirected(e);
        d.connectToNodeDirected(e);
        f.connectToNodeDirected(e);
        e.connectToNodeDirected(h);

        ArrayList<GraphNode<?>> agenda = new ArrayList<>();
        agenda.add(a);

        Searching.breadthFirstTraverse(agenda, null);
        System.out.println("\n \n");

        List<GraphNode<?>> bfsPath = Searching.findPathBreadthFirst(a, "Kiwi");
        for (GraphNode<?> n : bfsPath) System.out.println(n.getData());
    }
}
