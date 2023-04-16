package chinesecheckers;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MarbleTest {

    // @BeforeAll
    // public static void initialize() {
    //     Platform.startup(() -> {});
    // }

    @BeforeEach
    public void setup() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chinesecheckers/Board.fxml"));
        loader.load();
        BoardController mockController = loader.getController();
        mockController.initialize();
    }

    @Test
    public void testMove() {
        Hole hole1 = BoardController.getHole(new int[] {0, 0, 0});
        Hole hole2 = BoardController.getHole(new int[] {1, 0, -1});
        Marble marble = new Marble(hole1, "red");
        assertEquals(hole1.getCircle().getLayoutX(), marble.getCenterX());
        assertEquals(hole1.getCircle().getLayoutY(), marble.getCenterY());
        marble.move(hole2);
        assertEquals(hole2, marble.getHole());
        assertTrue(hole2.isOccupied());
        assertFalse(hole1.isOccupied());
        assertEquals(hole2.getCircle().getLayoutX(), marble.getCenterX());
        assertEquals(hole2.getCircle().getLayoutY(), marble.getCenterY());
    }

    @Test
    public void testLegalMove() {
        BoardController.enforceMoves.setSelected(true);
        Hole hole = BoardController.getHole(new int[] {0, 0, 0});
        Marble marble = new Marble(hole, "red");
        hole.setOccupied(true);
        Hole illegalHole = BoardController.getHole(new int[] { 2, 0, -2 });
        marble.move(illegalHole);
        assertEquals(hole.getCircle().getLayoutX(), marble.getCenterX());
        assertEquals(hole.getCircle().getLayoutY(), marble.getCenterY());
        assertTrue(BoardController.getHole(new int[] { 0, 0, 0 }).isOccupied());
        Hole legalHole = BoardController.getHole(new int[] { 1, 0, -1 });
        marble.move(legalHole);
        assertEquals(legalHole.getCircle().getLayoutX(), marble.getCenterX());
        assertEquals(legalHole.getCircle().getLayoutY(), marble.getCenterY());
        assertTrue(legalHole.isOccupied());
    }
}