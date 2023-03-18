package chinesecheckers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BoardController {

    @FXML
    private AnchorPane root;

    @FXML
    private Pane marblePane;
    private int numHoles = 120;
    private List<Boolean> occupiedList = new ArrayList<>(Collections.nCopies(numHoles, false));
    private Circle currentMarble;

    public void initialize() {
        for (int i = 0; i <= numHoles; i++) {
            String id = "hole" + i;
            Circle hole = (Circle) root.lookup("#" + id);
            placeMarbles(hole, i);
        }

        marblePane.setOnMouseClicked(event -> {
            resetMarbleColors();
            currentMarble = null;
        });


    }

    private void placeMarbles(Circle hole, int i) {
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

    private void createMarble(Circle hole, int i, Color color) {
        occupiedList.set(i, true);
        Circle marble = new Circle(hole.getLayoutX(), hole.getLayoutY(), 18, color);
        marble.toFront();
        marblePane.getChildren().add(marble);
        marble.setUserData(color);

        marble.setOnMouseClicked(event -> {
            if (marble != currentMarble) {
                if (currentMarble != null) {
                    resetMarbleColors();
                }
                highlightMarble(marble);
                currentMarble = marble;
                event.consume();
            }
        });
    }

    private void resetMarbleColors() {
        for (Node node : marblePane.getChildren()) {
            ((Circle) node).setFill((Color) ((Circle) node).getUserData());
        }
    }

    private void highlightMarble(Circle marble) {
        if (marble.getFill().equals(Color.BLACK)) {
            marble.setFill(((Color) marble.getUserData()).brighter().brighter().brighter().brighter());
        } else if (marble.getFill().equals(Color.WHITE) || marble.getFill().equals(Color.YELLOW)
                || marble.getFill().equals(Color.BLUE) || marble.getFill().equals(Color.RED)
                || marble.getFill().equals(Color.GREEN)) {
            marble.setFill(((Color) marble.getUserData()).darker());
        }
    }
}