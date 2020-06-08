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
import javafx.stage.Window;

import java.io.File;
import java.util.ArrayList;
import java.util.PrimitiveIterator;


public class Controller {

    @FXML
    private ImageView imageView;

    @FXML
    private TextField nodeName, addPointX, addPointY;

    @FXML
    private Slider duoColourSlider;

    private Image startImage;

    private static File file;

    // just used an array instead of having four ints
    // 0 -> x1, 1 -> y1, 2 -> x2, 3 -> y2
    private int[] pointCoordinates = new int[4];

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

        // creates a reference to the images dir in the project
        File imagesFolder = new File("images/");

        // sets the start directory to the images folder
        fc.setInitialDirectory(imagesFolder);

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.jfif", "*.gif"));
        file = fc.showOpenDialog(null);

        if (file != null) {
            String path = file.toURI().toString();
            int imageHeight = 400, imageWidth = 400;
            Image img = new Image(path, imageWidth, imageHeight, false, true);
            imageView.setImage(img);
        }
    }

    public void findPathBetweenSelectedPoints() {
        // setting up the data to get the start and end node
        Image blackWhiteImage = ImageProcessor.convertImageToBlackAndWhite(startImage, duoColourSlider.getValue());
        GraphNode<Color>[] nodes = ImageProcessor.createGraphNodesFromBlackAndWhiteImage(blackWhiteImage);
        GraphNode<Color>[] nodesWithEdges = ImageProcessor.createEdgesBetweenNodesFromImage(blackWhiteImage, nodes);
        GraphNode<Color> start = ImageProcessor.getNodesBasedOnMouseCoordinates(blackWhiteImage, pointCoordinates[0], pointCoordinates[1], nodes);
        GraphNode<Color> end = ImageProcessor.getNodesBasedOnMouseCoordinates(blackWhiteImage, pointCoordinates[2], pointCoordinates[3], nodes);

        Searching searching = new Searching<>();

        // perform the search and print the cost
        imageView.setImage(ImageProcessor.drawPathOnImage(startImage, searching.BFS(start, end)));
    }

    public void convertImageToBlackAndWhite() {
        // using start image so the image remains true to the ------------¬
        // original image and is not distorted by multiple                |
        // iterations on black/white conversion                           v
        imageView.setImage(ImageProcessor.convertImageToBlackAndWhite(startImage, duoColourSlider.getValue()));
    }

    public void getValueOfSelectedPoints() {

        // get the nodes based on the black and white image
        GraphNode<Color>[] nodes = ImageProcessor.createGraphNodesFromBlackAndWhiteImage(imageView.getImage());

        // get the start node based on the mouse coords
        GraphNode<Color> start = ImageProcessor.getNodesBasedOnMouseCoordinates(imageView.getImage(), pointCoordinates[0], pointCoordinates[1], nodes);
        GraphNode<Color> end = ImageProcessor.getNodesBasedOnMouseCoordinates(imageView.getImage(), pointCoordinates[2], pointCoordinates[3], nodes);

        // print the colour and x, y of the node
        System.out.println("Selected node data: ");
        System.out.println("Start: (Colour) " + start.getData().toString() + " [" + start.getxCoordinate() + ", " + start.getyCoordinate() + "]");
        System.out.println("End: (Colour) " + end.getData().toString() + " [" + end.getxCoordinate() + ", " + end.getyCoordinate() + "]");
    }

    public void resetValueOfPoints() {
        limit = 0;
        pointCoordinates = new int[4];
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
        //((Pane) imageView.getParent()).getChildren().add(markerBtn);
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
            limit++;

            // this stores the value of mouse x & y based on if the
            // first two values are the default int value - 0
            // if the first point is the default then set the values to the
            // next two points
            if (pointCoordinates[0] == 0) {
                pointCoordinates[0] = x;
                pointCoordinates[1] = y;
            } else {
                pointCoordinates[2] = x;
                pointCoordinates[3] = y;
            }
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