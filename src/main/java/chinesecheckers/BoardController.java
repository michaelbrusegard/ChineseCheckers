package chinesecheckers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BoardController {

    @FXML
    private AnchorPane root;

    @FXML
    private Pane holePane;
    private int numHoles = 120;
    private List<Boolean> occupiedList = new ArrayList<>(Collections.nCopies(numHoles, false));

    public void initialize() {
        for (int i = 0; i <= numHoles; i++) {
            String id = "hole" + i;
            Circle hole = (Circle) holePane.lookup("#" + id);

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

            hole.setOnMouseClicked((MouseEvent event) -> {
            });
        }
    }

    private void createMarble(Circle hole, int i, Color color) {
        occupiedList.set(i, true);
        Circle marble = new Circle(hole.getLayoutX(), hole.getLayoutY(), 18, color);
        marble.toFront();
        holePane.getChildren().add(marble);
    }
}