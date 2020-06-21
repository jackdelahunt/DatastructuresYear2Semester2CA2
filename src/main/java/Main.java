
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static Scene settingsScene;
    private static Stage settingsStage;

    private static Scene saveScene;
    private static Stage saveStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Rome Route Finder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        settingsScene = new Scene(root);

        root = FXMLLoader.load(getClass().getResource("save.fxml"));
        saveScene = new Scene(root);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void openSettingsStage() {
        settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        settingsStage.setScene(settingsScene);

        settingsStage.show();
    }

    public static void openSaveStage() {
        saveStage = new Stage();
        saveStage.setTitle("Save");
        saveStage.setScene(saveScene);

        saveStage.show();
    }

    public static void closeSettings() {
        settingsStage.close();
    }

    public static void closeSave() {
        saveStage.close();
    }
}
