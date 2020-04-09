import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField nodeName, addPointX, addPointY;
    @FXML
    private Button addMarkerBtn;
    private static File file;
    private Image img;
    ArrayList<GraphNode<?>> agendaList = new ArrayList<>();

    public void initialize() throws IOException {
        fileChooser();
        readDatabaseFile();
        addCulturalNodesOnMap();
    }

    public void fileChooser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.jfif", "*.gif"));
        file = fc.showOpenDialog(null);

        if (file != null) {
            String path = file.toURI().toString();
            int imageHeight = 800, imageWidth = 800;
            img = new Image(path, imageWidth, imageHeight, false, true);
            imageView.setImage(img);
        }
    }

    public void readDatabaseFile() throws IOException {
        String nodesFile = "src/CulturalNodes.txt";
        String line;

        BufferedReader nodesFileBr = new BufferedReader(new FileReader(nodesFile));
        while ((line = nodesFileBr.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 3) {
                nodesFileBr.close();
                throw new IOException("Invalid line in nodes file " + line);
            }
            String name = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            GraphNode<?> node = new GraphNode<>(name, x, y);
            agendaList.add(node);
            System.out.println(node.toString());
        }
        nodesFileBr.close();
    }

    //Adds icons(buttons) to cultural node position on the map
    public void addCulturalNodesOnMap() {
        int btnSize = 25;
        int btnIndex = 0;
        Button[] culturalBtns = new Button[agendaList.size()];

        for (int i = 0; i < agendaList.size(); i++) {
            culturalBtns[btnIndex] = new Button();
            culturalBtns[btnIndex].getStyleClass().clear();
            culturalBtns[btnIndex].getStyleClass().add("culturalNodeIcon");
            culturalBtns[btnIndex].setMinSize(btnSize, btnSize);
            culturalBtns[btnIndex].setMaxSize(btnSize, btnSize);
            culturalBtns[btnIndex].setPrefSize(btnSize, btnSize);
            culturalBtns[btnIndex].setTranslateX(agendaList.get(btnIndex).getxCoordinate());
            culturalBtns[btnIndex].setTranslateY(agendaList.get(btnIndex).getyCoordinate());

            int finalBtnIndex = btnIndex;
            culturalBtns[btnIndex].setOnAction(e -> {
                nodeName.setText(agendaList.get(finalBtnIndex).getName());
                addPointX.setText(String.valueOf(agendaList.get(finalBtnIndex).getxCoordinate()));
                addPointY.setText(String.valueOf(agendaList.get(finalBtnIndex).getyCoordinate()));
            });

            ((Pane) imageView.getParent()).getChildren().add(culturalBtns[btnIndex++]);
        }
    }

    public void addMarkerOnMap(int x, int y) {
        //new GraphNode(x,y);

        Button markerBtn = new Button();
        markerBtn.getStyleClass().add("mapBtn");
        //Makes button circular
        double radius = 5;
        markerBtn.setShape(new Circle(radius));
        markerBtn.setMinSize(2 * radius, 2 * radius);
        markerBtn.setMaxSize(2 * radius, 2 * radius);

        markerBtn.setTranslateX(x - radius);
        markerBtn.setTranslateY(y - radius);

        ((Pane) imageView.getParent()).getChildren().add(markerBtn);
    }

    public void mapClicked(MouseEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        //Displays x and y values in textfield's
        addPointX.setText(String.valueOf((int) e.getX()));
        addPointY.setText(String.valueOf((int) e.getY()));

        addMarkerOnMap(x, y);
    }
}