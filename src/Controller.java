import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;


public class Controller {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField nodeName, addPointX, addPointY;
    private static File file;
    public static ArrayList<GraphNode<?>> agendaList = new ArrayList<>();
    int limit = 0;

    public void initialize() {
        fileChooser();
        ListManager.initialize();
        loadAll();
        addCulturalNodesOnMap();
    }

    public void fileChooser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.jfif", "*.gif"));
        file = fc.showOpenDialog(null);

        if (file != null) {
            String path = file.toURI().toString();
            int imageHeight = 800, imageWidth = 800;
            Image img = new Image(path, imageWidth, imageHeight, false, true);
            imageView.setImage(img);
        }
    }

    //Adds icons(buttons) to cultural node position on the map
    public void addCulturalNodesOnMap() {
        if (file != null) {
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
        } else System.err.println("No Image Selected");
    }

    public void addMarkerOnMap(int x, int y) {

        GraphNode<?> temp = new GraphNode<>(limit == 0 ? "Start" : "End",x,y);
        agendaList.add(temp);
        Button markerBtn = new Button();
        //determines if its the start or end node, sets color accordingly
        markerBtn.getStyleClass().add(limit == 0 ? "mapStartBtn" : "mapEndBtn");
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

        if (limit < 2) {
            addMarkerOnMap(x, y);
            System.out.println(limit++);
        }
    }

    //loads data from databaseManager
    public void loadAll() {
        DatabaseManager.loadAll();
    }

    //Helper method to get distance between two points
//    private int calculateDistance(){
//        double p1 = Math.pow((this.getEnd().getXCoordinate() - this.getStart().getXCoordinate()), 2);
//        double p2 = Math.pow((this.getEnd().getYCoordinate() - this.getStart().getYCoordinate()), 2);
//        double sqrtIn = p1-p2;
//
//        if(sqrtIn < 0)
//            sqrtIn = sqrtIn * -1;
//
//        return (int)Math.abs(Math.sqrt(sqrtIn));
//    }
}