import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageProcessor {

    /**
     used to create a black and white image from an existing image
     * @param image the image that you want to convert
     * @param threshold the threshold for a single pixel to be considered white
     * @return the new black and white image
     */
    public static WritableImage convertImageToBlackAndWhite(Image image, double threshold) {

        // creates a writable image from the image that is currently in the image view
        WritableImage writableImage = new WritableImage(
                image.getPixelReader(),
                (int) image.getWidth(),
                (int) image.getHeight());

        // used to change the colours on a writable image
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // used to read the values from an image
        PixelReader pixelReader = writableImage.getPixelReader();

        // nested for loop to go through each pixel in the image
        for (int i = 0; i < writableImage.getHeight(); i++) {
            for (int j = 0; j < writableImage.getWidth(); j++) {

                // gets the colour of the current pixel and sets it to
                // a value between 0 - 3
                Color c = pixelReader.getColor(j, i);
                double colourSum = c.getRed() + c.getBlue() + c.getGreen();

                // if the total colour is above a certain value then it
                // is probably close enough to white so it
                // should be set to white, else it should be black
                if (colourSum > threshold) {
                    pixelWriter.setColor(j, i, Color.WHITE);
                } else {
                    pixelWriter.setColor(j, i, Color.BLACK);
                }
            }
        }

        // returns the new black and white image to use
        return writableImage;
    }

    /**
     * creates an array of graph nodes based on a back and white image
     * @param image the black and white image that the nodes are based off of
     * @return the node array based on the image
     */
    public static GraphNode[] createGraphNodesFromBlackAndWhiteImage(Image image) {

        // this array is all nodes that are made from the image
        // no matter the colour
        // --each pixel is one node

        // if the pixel is white it is then a new node
        // if the pixel is black then it remains null
        GraphNode[] nodes = new GraphNode[(int) (image.getWidth() * image.getHeight())];

        PixelReader pixelReader = image.getPixelReader();

        // nested for loop to go through each pixel in the image
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {

                // creating the node to add to the array,
                // Path is just used as a place holder
                if (pixelReader.getColor(i, j).equals(Color.WHITE)) {
                    nodes[i * j] = new GraphNode<Integer>("Path", i, j);
                    nodes[i * j].setData(nodes[i * j].hashCode());
                }
            }
        }
        return nodes;
    }

    /**
     * adds the edges to each node based on their position in the image
     * @param image the image that the nodes are based off of
     * @param nodes the array of nodes that will get the edges
     * @return the array of nodes but with the edge data
     */
    public static GraphNode[] createEdgesBetweenNodesFromImage(Image image, GraphNode[] nodes) {

        for (int i = 0; i < nodes.length; i++) {

            // if this is a black pixel then just skip it
            // this works well with TRIPLE nested if statement below
            if (nodes[i] == null)
                continue;

            // checking the node to the right
            // if the pixel is not last in column this will execute
            if ((i + 1) % (int) image.getWidth() != 0) {
                if (i + 1 < nodes.length) {
                    if (nodes[i + 1] != null) {

                        // no need to check for colour as the only colour left is
                        // white, connect to the node on your right -  undirected
                        nodes[i].connectToNodeUndirected(nodes[i + 1], 1);
                    }
                }
            }

            // checking the node underneath this node
            // if the pixel is not last in row this will execute
            if (!(i + image.getWidth() >= nodes.length)) {
                if (nodes[i + (int) image.getWidth()] != null) { //make sure its not white
                    nodes[i].connectToNodeUndirected(nodes[i + (int) image.getWidth()], 1);
                }
            }
        }
        return nodes;
    }

}
