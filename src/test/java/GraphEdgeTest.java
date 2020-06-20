import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphEdgeTest {

    GraphNode node01;
    GraphNode node02;
    GraphEdge edge;

    @BeforeEach
    void setup() {
        node01 = new GraphNode(100);
        node02 = new GraphNode(1000);
        edge = new GraphEdge(node01, 100);
    }

    @Test
    void setCostCorrectly() {
        edge.setCost(20);
        assertEquals(20, edge.getCost());
    }

    @Test
    void setCostIncorrectly() {
        edge.setCost(-100);
        assertEquals(0, edge.getCost());
    }

    @Test
    void setDestinationNodeTest() {
        edge.setDestinationNode(node02);
        assertEquals(node02, edge.getDestinationNode());
    }

}
