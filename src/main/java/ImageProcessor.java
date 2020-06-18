import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageProcessor {

    /**
     * used to create a black and white image from an existing image
     *
     * @param image     the image that you want to convert
     * @param threshold the threshold for a single pixel to be considered white
     * @return the new black and white image
     */
    public static Image convertImageToBlackAndWhite(Image image, double threshold) {

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
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {

                // gets the colour of the current pixel and sets it to
                // a value between 0 - 3
                Color c = pixelReader.getColor(i, j);
                double colourSum = c.getRed() + c.getBlue() + c.getGreen();

                // if the total colour is above a certain value then it
                // is probably close enough to white so it
                // should be set to white, else it should be black
                if (colourSum > threshold) {
                    pixelWriter.setColor(i, j, Color.WHITE);
                } else {
                    pixelWriter.setColor(i, j, Color.BLACK);
                }
            }
        }

        // returns the new black and white image to use
        return writableImage;
    }

    /**
     * creates an array of graph nodes based on a back and white image
     *
     * @param image the black and white image that the nodes are based off of
     * @return the node array based on the image
     */
    public static GraphNode<Color>[] createGraphNodesFromBlackAndWhiteImage(Image image) {

        // this array is all nodes that are made from the image
        // no matter the colour
        // --each pixel is one node

        // if the pixel is white it is then a new node
        // if the pixel is black then it remains null
        GraphNode<Color>[] nodes = new GraphNode[(int) (image.getWidth() * image.getHeight())];

        PixelReader pixelReader = image.getPixelReader();

        // nested for loop to go through each pixel in the image
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {

                // if this pixel is black do not create a new node
                if(pixelReader.getColor(j, i).equals(Color.BLACK)) {
                    continue;
                }

                // Creating a node of type colour with the x and y of the current node
                // i refers to the y (height) and j refers to the x (width)
                GraphNode<Color> node = new GraphNode<>("PATH", j, i);

                // giving the node its colour based on it's x and y
                node.setData(pixelReader.getColor(node.getxCoordinate(), node.getyCoordinate()));

                // setting the correct note in the array to the new node
                // this converts the 2D coords to work with the 1D array
                nodes[(int) ((i * image.getWidth()) + j)] = node;
            }
        }
        return createEdgesBetweenNodesFromImage(image, nodes);
    }

    /**
     * adds the edges to each node based on their position in the image
     *
     * @param image the image that the nodes are based off of
     * @param nodes the array of nodes that will get the edges
     * @return the array of nodes but with the edge data
     */
    private static GraphNode[] createEdgesBetweenNodesFromImage(Image image, GraphNode[] nodes) {

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
                if (nodes[i + (int) image.getWidth()] != null) { // make sure its not black
                    nodes[i].connectToNodeUndirected(nodes[i + (int) image.getWidth()], 1);
                }
            }
        }
        return nodes;
    }

    /**
     * returns a node based on the place where the mouse clicked from the node array
     * based on the image
     *
     * @param image the image that the node array is created from
     * @param x     the x of mouse when clicked
     * @param y     the y of the mouse when clicked
     * @param nodes the node array that is created from the image
     * @return the node that matched the location of the mouse click
     */
    public static GraphNode<Color> getNodesBasedOnMouseCoordinates(Image image, int x, int y, GraphNode<Color>[] nodes) {

        // gets the node that should have that x and y
        return nodes[(int) ((y * image.getWidth()) + x)];
    }

    /**
     * sets the pixels on an image along the path in a colour
     * @param image the image that you want to draw the path on
     * @param nodePath a ordered list of the nodes that are the path you want to display
     * @param colour the colour of the path
     * @param isPathFabulous no explanation needed
     * @return the image with the path drawn
     */
    public static Image drawPathOnImage(Image image, List<GraphNode<?>> nodePath, Color colour, boolean isPathFabulous) {
        WritableImage writableImage = new WritableImage(image.getPixelReader() ,(int)image.getWidth(), (int)image.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // this if else may seem stupid but it fixed the problem of having a if inside
        // a single for each loop which was annoying
        if(isPathFabulous) {
            // this is here so if the colour the user entered is black or white,
            // the hue shift would not work so just set it to a color that will work
            colour = Color.RED;
            for(GraphNode node : nodePath) {
                colour = colour.deriveColor(1.1, 1, 1, 1);
                pixelWriter.setColor(node.getxCoordinate(), node.getyCoordinate(), colour);
            }
        } else {
            for(GraphNode node : nodePath) {
                pixelWriter.setColor(node.getxCoordinate(), node.getyCoordinate(), colour);
            }
        }

        return writableImage;
    }

    /**
     * gets the total cost of a path form the start node to the end node
     * @param path the path that you want to evaluate
     * @return the cost of the path
     */
    public static int getCostOfPath(List<GraphNode<?>> path) {

        if(path.size() <= 1) return 0;

        int cost = 0;

        for(int i = 0; i < path.size() - 1; i++) {
            GraphNode<?> currentNode = path.get(i);
            GraphNode<?> nextNode = path.get(i + 1);

            for(GraphEdge adjEdge : currentNode.getAdjList()){
                if(adjEdge.getDestinationNode().equals(nextNode))
                    cost += adjEdge.getCost();
            }

        }

        return cost;
    }
}
