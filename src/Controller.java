import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;


public class Controller {

    @FXML
    private ImageView imageView;

    @FXML
    private TextField nodeName, addPointX, addPointY;

    @FXML
    private Slider duoColourSlider;

    private Image startImage;

    private static File file;
    public static ArrayList<GraphNode<?>> agendaList = new ArrayList<>();
    int limit = 0;

    public void initialize() {
        fileChooser();
        ListManager.initialize();
        loadAll();
        addCulturalNodesOnMap();


        // this is used so when editing the image we
        // can use the original image instead of the
        // already edited image
        startImage = imageView.getImage();
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

    public void convertImageToBlackAndWhite() {
        imageView.setImage(ImageProcessor.convertImageToBlackAndWhite(startImage, duoColourSlider.getValue()));
    }


    // temp point coords for now
    private int xPoint1 = -1;
    private int xPoint2 = -1;
    private int yPoint1 = -1;
    private int yPoint2 = -1;

    // this will be called every time a point is added
    // if the two points are selected then it will start the search
    public void findPathBetweenTwoSelectedPoints(int x, int y) {

        // this is what will make the point1 set be assigned first then point2 set
        // if there is only one point set so far then it will stop the method from
        // searching with these default values ^^, because that would be stupid
        if (xPoint1 == -1) {
            xPoint1 = x;
            yPoint1 = y;
            return;
        } else {
            xPoint2 = x;
            yPoint2 = y;
        }

        // nodes created from the current image
        GraphNode[] nodes = ImageProcessor.createEdgesBetweenNodesFromImage(imageView.getImage(), ImageProcessor.createGraphNodesFromBlackAndWhiteImage(imageView.getImage()));

        GraphNode start = null;
        GraphNode end = null;

        // will go through each node to find what node was pressed
        // it will then assign to start and end node where appropriate
        for (GraphNode node : nodes) {

            if (node == null)
                continue;

            if (node.getxCoordinate() == xPoint1 && node.getyCoordinate() == yPoint1) {
                start = node;
            } else if (node.getxCoordinate() == xPoint2 && node.getyCoordinate() == yPoint2) {
                end = node;
            }
        }

        System.out.println("[ " + xPoint1 + ", " + yPoint1 + " ]");
        System.out.println("[ " + xPoint2 + ", " + yPoint2 + " ]");

    }

    //Adds icons(buttons) to cultural node position on the map
    public void addCulturalNodesOnMap() {
        if (file != null) {
            int btnSize = 25;

            Button[] culturalBtns = new Button[agendaList.size()];

            for (int i = 0; i < agendaList.size(); i++) {
                culturalBtns[i] = new Button();
                culturalBtns[i].getStyleClass().clear();
                culturalBtns[i].getStyleClass().add("culturalNodeIcon");
                culturalBtns[i].setMinSize(btnSize, btnSize);
                culturalBtns[i].setMaxSize(btnSize, btnSize);
                culturalBtns[i].setPrefSize(btnSize, btnSize);
                culturalBtns[i].setTranslateX(agendaList.get(i).getxCoordinate());
                culturalBtns[i].setTranslateY(agendaList.get(i).getyCoordinate());

                int finalBtnIndex = i;
                culturalBtns[i].setOnAction(e -> {
                    nodeName.setText(agendaList.get(finalBtnIndex).getName());
                    addPointX.setText(String.valueOf(agendaList.get(finalBtnIndex).getxCoordinate()));
                    addPointY.setText(String.valueOf(agendaList.get(finalBtnIndex).getyCoordinate()));
                });

                ((Pane) imageView.getParent()).getChildren().add(culturalBtns[i]);
            }
        } else System.err.println("No Image Selected");
    }

    public void addMarkerOnMap(int x, int y) {
        // create a new node, with the data start or end based on the limit
        GraphNode<?> temp = new GraphNode<>(limit == 0 ? "Start" : "End", x, y);

        // adds that new node to the agenda list
        agendaList.add(temp);

        // create a new button for the marker to be displayed
        Button markerBtn = new Button();

        //determines if its the start or end node, sets color accordingly
        markerBtn.getStyleClass().add(limit == 0 ? "mapStartBtn" : "mapEndBtn");

        //Makes button circular and sets key values
        double radius = 5;
        markerBtn.setShape(new Circle(radius));
        markerBtn.setMinSize(2 * radius, 2 * radius);
        markerBtn.setMaxSize(2 * radius, 2 * radius);

        // centres the circle on the cursor
        markerBtn.setTranslateX(x - radius);
        markerBtn.setTranslateY(y - radius);

        // adds the new button to the image view
        ((Pane) imageView.getParent()).getChildren().add(markerBtn);
    }

    public void mapClicked(MouseEvent e) {

        // gets the coords of the mouse
        int x = (int) e.getX();
        int y = (int) e.getY();
        //Displays x and y values in textfield's
        addPointX.setText(String.valueOf((int) e.getX()));
        addPointY.setText(String.valueOf((int) e.getY()));

        // if the start and end points have not been placed yet
        if (limit < 2) {
            addMarkerOnMap(x, y);
            // print the limit and then add one
            System.out.println(limit++);
        }

        findPathBetweenTwoSelectedPoints(x, y);
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