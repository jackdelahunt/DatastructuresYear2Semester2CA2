import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SaveController {

    @FXML
    private TextField name, path;

    /**
     * saves the image that is stored in Settings to a specified path
     */
    public void saveImage() {
        try {
            // creates a location based on the name and path
            File out = new File(formatPathCheck(path.getText()) + name.getText());

            // converts the saved image to a buffered image
            BufferedImage bImage = SwingFXUtils.fromFXImage(Settings.savedImage, null);

            // writes that new file
            ImageIO.write(bImage, "png", out);

            // closes the save window
            close();
        } catch (Exception e) {

            // print me that error
            System.out.println(e);
        }
    }

    /**
     * converts the normal windows path to a compatible one for javaFX
     * @param s the string you want to convert
     * @return the newly formatted string
     */
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
