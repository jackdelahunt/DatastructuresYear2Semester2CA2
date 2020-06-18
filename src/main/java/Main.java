
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Rome Route Finder");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        settingsScene = new Scene(root);
    }

    public static void main(String[] args) {
        File file = new File("resources/images/rome.jpg");
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);

        launch(args);
    }

    public static void openSettingsStage() {
        settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        settingsStage.setScene(settingsScene);

        settingsStage.show();
    }

    public static void closeSettings() {
        settingsStage.close();
    }
}
