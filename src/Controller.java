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

public class Controller {
    @FXML
    private ImageView imageView;
    @FXML
    private TextField addPointX, addPointY;
    @FXML
    private Button addMarkerBtn;
    private static File file;
    private Image img;
    private int imageWidth = 800, imageHeight = 800;

    public void initialize() {
        fileChooser();
    }

    public void fileChooser() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.jfif", "*.gif"));
        file = fc.showOpenDialog(null);

        if (file != null) {
            String path = file.toURI().toString();
            img = new Image(path, imageWidth, imageHeight, false, true);
            imageView.setImage(img);
        }
    }

    //Adds a marker onto map where user has clicked
    public void addMarkerOnMap(int x, int y) {
        //new GraphNode(x,y);

        Button markerBtn = new Button();
        markerBtn.getStyleClass().add("mapBtn");
        //Makes button circular
        double radius = 5;
        markerBtn.setShape(new Circle(radius));
        markerBtn.setMinSize(2 * radius, 2 * radius);
        markerBtn.setMaxSize(2 * radius, 2 * radius);

        markerBtn.setTranslateX(x-radius);
        markerBtn.setTranslateY(y-radius);

        ((Pane) imageView.getParent()).getChildren().add(markerBtn);
    }

    //detects when map is clicked and gets coordinates
    public void mapClicked(MouseEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        //Displays x and y values in textfield's
        addPointX.setText(String.valueOf((int) e.getX()));
        addPointY.setText(String.valueOf((int) e.getY()));

        addMarkerOnMap(x, y);
    }
}
