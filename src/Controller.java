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

    public ArrayList<GraphNode<?>> createGraphNodesFromImage(){
        ArrayList<GraphNode<?>> nodes = new ArrayList<>();
        PixelReader pixelReader = imageView.getImage().getPixelReader();

        // nested for loop to go through each pixel in the image
        for (int i = 0; i < imageView.getImage().getHeight(); i++) {
            for (int j = 0; j < imageView.getImage().getWidth(); j++) {

                // if the pixel is white then create a node from that pixel
                // and add it to the nodes array
                if(pixelReader.getColor(i, j).equals(Color.WHITE)){
                    nodes.add(new GraphNode<>("Path", i, j));
                }

            }
        }
        System.out.println(nodes.size());
        return nodes;
    }

    

    public void duoColour() {

        System.out.println(agendaList.size());

        // creates a writable image from the image that is currently in the image view
        WritableImage writableImage = new WritableImage(
                startImage.getPixelReader(),
                (int) startImage.getWidth(),
                (int) startImage.getHeight());

        // used to change the colours on a writable image
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // used to read the values from an image
        PixelReader pixelReader = writableImage.getPixelReader();

        // nested for loop to go through each pixel in the image
        for (int i = 0; i < writableImage.getHeight(); i++) {
            for (int j = 0; j < writableImage.getWidth(); j++) {

                // gets the colour of the current pixel and sets it to
                // a value between 0 - 3
                Color c = pixelReader.getColor(j, i);
                double colourSum = c.getRed() + c.getBlue() + c.getGreen();

                // if the total colour is above a certain value then it
                // is probably close enough to white so it
                // should be set to white, else it should be black
                if (colourSum > duoColourSlider.getValue()) {
                    pixelWriter.setColor(j, i, Color.WHITE);
                } else {
                    pixelWriter.setColor(j, i, Color.BLACK);
                }
            }
        }

        // returns the new black and white image to use
        imageView.setImage(writableImage);
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