package chinesecheckers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BoardController {

    @FXML
    private AnchorPane root;

    @FXML
    private Group marbleGroup;
    private final int numHoles = 120;
    public static Marble selectedMarble = null;

    public void initialize() {
        for (int i = 0; i <= numHoles; i++) {
            String id = "hole" + i;
            Circle circle = (Circle) root.lookup("#" + id);
            Hole hole = new Hole(i, circle);
            placeMarbles(hole, i);
        }
        setupClickHandler();
    }

    private void placeMarbles(Hole hole, int i) {
        if (i < 10) {
            createMarble(hole, i, Color.BLACK);
        } else if (i < 20) {
            createMarble(hole, i, Color.GREEN);
        } else if (i < 30) {
            createMarble(hole, i, Color.BLUE);
        } else if (i < 40) {
            createMarble(hole, i, Color.WHITE);
        } else if (i < 50) {
            createMarble(hole, i, Color.RED);
        } else if (i < 60) {
            createMarble(hole, i, Color.YELLOW);
        }
    }

    private void createMarble(Hole hole, int i, Color color) {
        hole.setOccupied(true);
        Marble marble = new Marble(hole.getCircle().getLayoutX(), hole.getCircle().getLayoutY(), 18, color);
        marble.toFront();
        marbleGroup.getChildren().add(marble);
    }

    private void setupClickHandler() {
        root.setOnMouseClicked(event -> {
            System.out.println("Board clicked");
            resetAllMarbleColors();
            selectedMarble = null;
        });
    }

    public void resetAllMarbleColors() {
        for (Node marble : marbleGroup.getChildren()) {
            ((Marble) marble).resetColor();
        }
    }
}