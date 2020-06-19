import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GraphNodeTest {

    GraphNode<Integer> node01;
    GraphNode<Integer> node02;

    @BeforeEach
    void setup() {
        node01 = new GraphNode<>(100);
        node02 = new GraphNode<>(150);
    }

    @Test
    void basicConstructorTest() {
        assertEquals(100, node01.getData());
    }
    



}
