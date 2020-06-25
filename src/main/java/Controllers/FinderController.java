package Controllers;

import Models.GraphNode;
import Models.Searching;
import Models.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.Arrays;

public class FinderController {

    @FXML
    TableView startTable, endTable, resultTable;

    @FXML
    TableColumn startLandmarksColumn, endLandmarksColumn, resultLandmarkName;

    @FXML
    TextField startLandmarkField, endLandmarkField;

    GraphNode<?> startLandmark, endLandmark;

    public void initialize() {

        GraphNode<?>[] landmarks;

        try {

            // nodes loaded in from file
            landmarks = Main.loadNodesFromFile();

            // convert the nodes return to a observable list
            ObservableList landmarkList = FXCollections.observableList(new ArrayList(Arrays.asList(landmarks)));

            startLandmarksColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            endLandmarksColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            startTable.setItems(landmarkList);
            endTable.setItems(landmarkList);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void setStartLandmarkField() {
        startLandmark = (GraphNode)startTable.getSelectionModel().getSelectedItem();
        System.out.println(startLandmark.getName());
        if(startLandmark != null)
            startLandmarkField.setText(startLandmark.getName());
    }

    public void setEndLandmarkField() {
        endLandmark = (GraphNode)endTable.getSelectionModel().getSelectedItem();
        System.out.println(endLandmark.getName());
        if(endLandmark != null)
            endLandmarkField.setText(endLandmark.getName());
    }

    public void findPath() {
        try {
            Searching searching = new Searching(startLandmark, endLandmark);
            searching.dijkstra(Main.loadNodesFromFile());

            ObservableList path = FXCollections.observableList(new ArrayList(Arrays.asList(searching.getPath())));
            resultLandmarkName.setCellValueFactory(new PropertyValueFactory<>("name"));

            resultTable.setItems(path);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
