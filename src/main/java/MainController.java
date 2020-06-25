import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {

    // this is the image view the image is stored in
    @FXML
    private ImageView imageView;

    // the label that shows loading, generating etc.
    @FXML
    private Label contextLabel, lengthLabel;

    // these are some text fields that show the data about the selected points
    @FXML
    private TextField addPointX, addPointY;

    // the table that stores all of the landmark nodes
    @FXML
    private TableView<GraphNode<?>> landmarkTable;

    @FXML
    private TableColumn<GraphNode<?>, String> nameColumn, xColumn, yColumn;

    // this is the image that is loaded on at the start and is used
    // when changing the image as this does not have alterations
    private Image rawImage;

    // just used an array instead of having four ints
    // 0 -> x1, 1 -> y1, 2 -> x2, 3 -> y2
    private ArrayList<Integer> pointCoordinates = new ArrayList<>(4);

    public static ArrayList<GraphNode<?>> agendaList = new ArrayList<>();

    // limiting the amount of points on the map to 10
    int limit = 10;

    public void initialize() {
        setStartImage();



        try {
            GraphNode<?>[] nodes = Main.loadNodesFromFile();

            addCulturalNodesOnMap(nodes);
            handleLandmarkTable(nodes);

        } catch (Exception e) {
            System.out.println(e);
        }

        // this is used so when editing the image we
        // can use the original image instead of the
        // already edited image
        rawImage = imageView.getImage();
    }

    /**
     * gets the default rome image from the resources directory at runtime
     */
    public void setStartImage() {
        Image startImage = new Image("/images/rome.jpg");
        imageView.setImage(startImage);
    }

    public void fileChooser() {
        FileChooser fc = new FileChooser();

        // sets the start directory to the root directory
        fc.setInitialDirectory(new File("/"));

        // sets what type of file will be displayed in the file chooser
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.jfif", "*.gif"));

        // gets the return of the file chooser when displayed
        File imageFile = fc.showOpenDialog(null);

        // if a correct image was selected then set that to the displaying image
        if (imageFile != null) {
            String path = imageFile.toURI().toString();
            int imageHeight = 800, imageWidth = 1180;
            Image img = new Image(path, imageWidth, imageHeight, false, true);
            imageView.setImage(img);
            rawImage = img;
        }
    }

    public void findPathBetweenSelectedPoints() {

        try {
            // setting up the data to get the start and end node
            // turn the start image to black and white based on the slider (may change to a const)
            Image blackWhiteImage = ImageProcessor.convertImageToBlackAndWhite(rawImage, 2.62);

            // get the nodes based on the map with their edges
            GraphNode<Color>[] nodes = ImageProcessor.createGraphNodesFromBlackAndWhiteImage(blackWhiteImage);

            // list of all the paths found based on the points selected
            List<List<GraphNode<?>>> bfsPaths = new ArrayList<>();
            for (int i = 0; i < pointCoordinates.size(); i += 2) {
                if (i + 2 >= pointCoordinates.size()) {
                    continue;
                }
                // get the start nodes based on the first mouse click
                GraphNode<Color> start = ImageProcessor.getNodesBasedOnMouseCoordinates(blackWhiteImage, pointCoordinates.get(i), pointCoordinates.get(i + 1), nodes);

                // get the end nodes based on the second mouse click
                GraphNode<Color> end = ImageProcessor.getNodesBasedOnMouseCoordinates(blackWhiteImage, pointCoordinates.get(i + 2), pointCoordinates.get(i + 3), nodes);

                // creating the searching object that the draw path on image will use
                // searching object is based on the start and end node so threading can be used
                Searching searching = new Searching(start, end);

                if (Settings.searchType) {

                    // perform the BFS search
                    searching.BFS();
                } else {

                    // perform the dijkstra search
                    searching.dijkstra(nodes);
                }

                // tell the user what happened in the label
                contextLabel.setText("Generated path from (" + start.getX() + ", " + start.getY() + ") to (" + end.getX() + ", " + end.getY() + ")");


                bfsPaths.add(searching.getPath());
            }

            // add all the individual paths together as one -> total path
            List<GraphNode<?>> totalPath = Searching.addNodePaths(bfsPaths);
            lengthLabel.setText(ImageProcessor.getCostOfPath(totalPath) + "m");

            // perform the search
            imageView.setImage(ImageProcessor.drawPathOnImage(rawImage, totalPath, Color.web(Settings.pathColour), Settings.isFabulous));

        } catch (Exception e) {
            if (e.getMessage() == null)
                contextLabel.setText("The path cannot be found, Try again Later");
            else if (e.getMessage().equals("Invalid color specification"))
                contextLabel.setText("Try a new colour like: #00ff00");
            else
                contextLabel.setText("Oops... Something went wrong");

        }
    }

    // called by the reset map button,
    // reset the points selected and sets the image view to the original image
    public void resetMap() {
        pointCoordinates = new ArrayList<>(4);
        imageView.setImage(rawImage);
        contextLabel.setText("");
        lengthLabel.setText("");
    }


    /**
     * called at the start of the controller to draw points of landmarks to the map
     * @param nodes the nodes that you want to draw at
     */
    public void addCulturalNodesOnMap(GraphNode<?>[] nodes) {
        int buttonSize = 25;
        int labelOffsetX = 30;
        int labelOffsetY = 5;

        Button[] landmarkButtons = new Button[nodes.length];
        Label[] landmarkLabels = new Label[nodes.length];

        for (int i = 0; i < nodes.length; i++) {
            landmarkButtons[i] = new Button();
            landmarkButtons[i].getStyleClass().clear();
            landmarkButtons[i].setStyle("-fx-background-radius: 50%; -fx-background-image: url('geopoint.png');");
            landmarkButtons[i].setMinSize(buttonSize, buttonSize);
            landmarkButtons[i].setMaxSize(buttonSize, buttonSize);
            landmarkButtons[i].setPrefSize(buttonSize, buttonSize);
            landmarkButtons[i].setTranslateX(nodes[i].getX());
            landmarkButtons[i].setTranslateY(nodes[i].getY());

            landmarkLabels[i] = new Label(nodes[i].getName());
            landmarkLabels[i].setTranslateX(nodes[i].getX() + labelOffsetX);
            landmarkLabels[i].setTranslateY(nodes[i].getY() + labelOffsetY);

            ((Pane) imageView.getParent()).getChildren().add(landmarkButtons[i]);
            ((Pane) imageView.getParent()).getChildren().add(landmarkLabels[i]);
        }
    }

    public void handleLandmarkTable(GraphNode<?>[] landmarks) {
        ObservableList landmarkList = FXCollections.observableList(new ArrayList(Arrays.asList(landmarks)));
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        xColumn.setCellValueFactory(new PropertyValueFactory("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory("y"));

        landmarkTable.setItems(landmarkList);

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
        if (pointCoordinates.size() / 2 < limit) {
            addMarkerOnMap(x, y);

            // add the twp points to the pointco-ordinate array
            pointCoordinates.add(x);
            pointCoordinates.add(y);
        }
    }

    public void openSettings() {
        Main.openSettingsStage();
    }

    public void openSave() {
        Settings.savedImage = imageView.getImage();
        Main.openSaveStage();
    }

}