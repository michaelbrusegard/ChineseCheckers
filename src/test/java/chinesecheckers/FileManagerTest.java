package chinesecheckers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

public class FileManagerTest {
    @BeforeEach
    public void setUp() throws IOException {
        Platform.startup(() -> {
        });
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chinesecheckers/Board.fxml"));
        loader.load();
        BoardController mockController = loader.getController();
        mockController.initialize();
    }

    @Test
    public void testSave() throws IOException {
        BoardController.cleanSlateMarbles();
        File tempFile = File.createTempFile("testSave", ".chc");
        String filePath = tempFile.getAbsolutePath();
        Marble redMarble = new Marble(BoardController.getHole(new int[] { 0, 0, 0 }), "red");
        Marble blueMarble = new Marble(BoardController.getHole(new int[] { 1, 0, -1 }), "blue");
        Marble greenMarble = new Marble(BoardController.getHole(new int[] { 1, -1, 0 }), "green");
        BoardController.marbleGroup.getChildren().addAll(redMarble, greenMarble, blueMarble);
        FileManager.save(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();
        String[] lineData = line.split(":");
        String savedArmy = lineData[0];
        String savedHole = lineData[1];
        assertEquals("red", savedArmy);
        assertEquals("P0P0P0", savedHole);
        String line2 = reader.readLine();
        String[] lineData2 = line2.split(":");
        String savedArmy2 = lineData2[0];
        String savedHole2 = lineData2[1];
        assertEquals("green", savedArmy2);
        assertEquals("P1N1P0", savedHole2);
        String line3 = reader.readLine();
        String[] lineData3 = line3.split(":");
        String savedArmy3 = lineData3[0];
        String savedHole3 = lineData3[1];
        assertEquals("blue", savedArmy3);
        assertEquals("P1P0N1", savedHole3);
        reader.close();
        tempFile.delete();
    }

    @Test
    public void testLoad() throws IOException {
        File tempFile = File.createTempFile("testLoad", ".chc");
        String filePath = tempFile.getAbsolutePath();
        FileWriter writer = new FileWriter(filePath);
        writer.write("red:P4N6P2\nblue:P0P0P0\ngreen:P0N2P2");
        writer.close();
        FileManager.load(filePath);
        assertEquals(3, BoardController.marbleGroup.getChildren().size());
        assertTrue(BoardController.marbleGroup.getChildren().get(0) instanceof Marble);
        assertTrue(BoardController.marbleGroup.getChildren().get(1) instanceof Marble);
        assertTrue(BoardController.marbleGroup.getChildren().get(2) instanceof Marble);
        assertEquals("red", ((Marble) BoardController.marbleGroup.getChildren().get(0)).getArmy());
        assertEquals("blue", ((Marble) BoardController.marbleGroup.getChildren().get(1)).getArmy());
        assertEquals("green", ((Marble) BoardController.marbleGroup.getChildren().get(2)).getArmy());
        tempFile.delete();
    }
}