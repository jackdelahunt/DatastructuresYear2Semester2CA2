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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FinderController {

    @FXML
    TableView startTable, endTable, resultTable;

    @FXML
    TableColumn startLandmarksColumn, endLandmarksColumn, resultLandmarkName;

    @FXML
    TextField startLandmarkField, endLandmarkField;

    GraphNode<String> startLandmark, endLandmark;

    private GraphNode<String>[] landmarks;

    public void initialize() {

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
        startLandmark = (GraphNode<String>)startTable.getSelectionModel().getSelectedItem();
        if(startLandmark != null)
            startLandmarkField.setText(startLandmark.getName());
    }

    public void setEndLandmarkField() {
        endLandmark = (GraphNode<String>)endTable.getSelectionModel().getSelectedItem();
        if(endLandmark != null)
            endLandmarkField.setText(endLandmark.getName());
    }

    public void findPath() {
        System.out.println(startLandmark.toString() + endLandmark.toString());
        try {
            Searching<String> searching = new Searching(startLandmark, endLandmark);
            searching.dijkstra(landmarks);

            Object[] landmarkPath = searching.getPath().toArray();

            ArrayList landmarkList = new ArrayList(Arrays.asList(landmarkPath));
            landmarkList.add(startLandmark);

            Collections.reverse(landmarkList);

            ObservableList path = FXCollections.observableList(landmarkList);
            resultLandmarkName.setCellValueFactory(new PropertyValueFactory<>("name"));

            resultTable.setItems(path);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
        }
    }

}
