package Controllers;

import Models.Main;
import Models.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML
    private TextField colourField;

    @FXML
    private CheckBox rainbowRoad, searchType;

    @FXML
    public void initialize() {
        colourField.setText(Settings.pathColour);
        rainbowRoad.setSelected(Settings.isFabulous);
        searchType.setSelected(Settings.searchType);
    }

    public void applyChanges() {
        Settings.pathColour = colourField.getText();
        Settings.isFabulous = rainbowRoad.isSelected();
        Settings.searchType = searchType.isSelected();

        close();
    }

    public void close() {
        Main.closeSettings();
    }

}
