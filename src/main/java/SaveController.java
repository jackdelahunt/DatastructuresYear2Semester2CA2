import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SaveController {

    @FXML
    private TextField name, path;

    public void saveImage() {
        try {
            File out = new File(formatPathCheck(path.getText()) + name.getText());
            System.out.println(out.getAbsolutePath());
            BufferedImage bImage = SwingFXUtils.fromFXImage(Settings.currentImage, null);
            ImageIO.write(bImage, "png", out);
            close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String formatPathCheck(String s) {
        // replaces all incorrect slashes with correct ones if they occur
        String pathText = s.replace('\\', '/');;

        // adds the extras slash if the user did not add it
        if (pathText.charAt( pathText.length() - 1 ) != '/')
            pathText += "/";

        return pathText;
    }

    public void close() {
        Main.closeSave();
    }

}
