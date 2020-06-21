import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SaveController {


    public void saveImage() {
        try {
            File out = new File("image.png");
            System.out.println(out.getAbsolutePath());
            BufferedImage bImage = SwingFXUtils.fromFXImage(Settings.currentImage, null);
            ImageIO.write(bImage, "png", out);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
