package chinesecheckers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

// import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Circle;

public class HoleTest {
    
    // @BeforeAll
    // public static void initialize() {
    //     Platform.startup(() -> {});
    // }

    @Test
    public void testConstructor() {
        Circle testCircle = new Circle(1, 1, 1);
        for (int[] coordinates : new int[][] {
                { 0, 0, 0 },
                { 1, 0, -1 },
                { 1, -1, 0 },
                { 0, -1, 1 },
                { -1, 0, 1 },
                { -1, 1, 0 },
                { 0, 1, -1 }
        }) {
            Hole hole = new Hole(testCircle, coordinates);
            assertEquals(testCircle, hole.getCircle());
            assertEquals(coordinates, hole.getCoordinates());
            assertEquals(false, hole.isOccupied());
        }
    }
    
    @Test
    public void testFindNeighbours() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chinesecheckers/Board.fxml"));
        loader.load();
        BoardController mockController = loader.getController();
        mockController.initialize();

        Circle testCircle = new Circle();
        int[] coordinates = new int[] {0, 0, 0};
        Hole hole = new Hole(testCircle, coordinates);

        hole.findNeighbours();
        List<Hole> neighbours = hole.getNeighbours();
        assertEquals(6, neighbours.size());
        assertTrue(neighbours.contains(BoardController.getHole(new int[] {1, 0, -1})));
        assertTrue(neighbours.contains(BoardController.getHole(new int[] {1, -1, 0})));
        assertTrue(neighbours.contains(BoardController.getHole(new int[] {0, -1, 1})));
        assertTrue(neighbours.contains(BoardController.getHole(new int[] {-1, 0, 1})));
        assertTrue(neighbours.contains(BoardController.getHole(new int[] {-1, 1, 0})));
        assertTrue(neighbours.contains(BoardController.getHole(new int[] {0, 1, -1})));
    }

    @Test
    public void testSetOccupied() {
        Circle circle = new Circle();
        int[] coordinates = new int[] {1, 2, 3};
        Hole hole = new Hole(circle, coordinates);
        hole.setOccupied(true);
        assertTrue(hole.isOccupied());
    }
}

