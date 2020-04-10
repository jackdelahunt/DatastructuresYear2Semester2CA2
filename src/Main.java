
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

        a.connectToNodeDirected(b, 1);
        b.connectToNodeDirected(c, 6);
        c.connectToNodeDirected(d, 2);
        d.connectToNodeDirected(e, 5);
        e.connectToNodeDirected(f, 2);
        f.connectToNodeDirected(g, 3);
        g.connectToNodeDirected(h, 6);

        ArrayList<GraphNode<?>> agenda = new ArrayList<>();
        agenda.add(a);

        List<GraphNode<?>> bfsPath = Searching.findPathBreadthFirst(a, "Plum", 0);
        for (GraphNode<?> n : bfsPath) System.out.println(n.getData());
        System.out.println(Searching.costOfLast);

        //Commented code does exact same as above but for the cultural node objects
        //the commented code in Searching is for this to work.

//        GraphNode<?> aa = new GraphNode<>("Vatican City", 230, 390);
//        GraphNode<?> bb = new GraphNode<>("Engelsburg", 340, 420);
//        GraphNode<?> cc = new GraphNode<>("Colosseum", 590, 645);
//        GraphNode<?> dd = new GraphNode<>("Trevi Fountain", 490, 450);
//        GraphNode<?> ee = new GraphNode<>("People's Square", 430, 275);
//
//        aa.connectToNodeDirected(bb, 1);
//        bb.connectToNodeDirected(cc, 6);
//        cc.connectToNodeDirected(dd, 2);
//        dd.connectToNodeDirected(ee, 5);
//
//        ArrayList<GraphNode<?>> agenda2 = new ArrayList<>();
//        agenda.add(aa);
//
//        List<GraphNode<?>> bfsPath2 = Searching.findPathBreadthFirst(aa, ee, 0);
//        for (GraphNode<?> nn : bfsPath2) System.out.println(nn.toString());
//        System.out.println("Cost of path: " + Searching.costOfLast);
    }
}
