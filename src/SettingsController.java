import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML
    private TextField colourField;

    @FXML
    private CheckBox rainbowRoad, searchType, animation;

    @FXML
    public void initialize() {
        colourField.setText(Settings.pathColour);
        rainbowRoad.setSelected(Settings.isFabulous);
        searchType.setSelected(Settings.searchType);
        animation.setSelected(Settings.animation);
    }

    public void applyChanges() {
        Settings.pathColour = colourField.getText();
        Settings.isFabulous = rainbowRoad.isSelected();
        Settings.searchType = searchType.isSelected();
        Settings.animation = animation.isSelected();

        close();
    }

    public void close() {
        Main.closeSettings();
    }

}
