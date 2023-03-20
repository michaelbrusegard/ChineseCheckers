package chinesecheckers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Marble extends Circle {

    private final Color color;

    public Marble(double posX, double posY, double radius, Color color) {
        super(posX, posY, radius, color);
        this.color = color;
        this.setUserData(color);
        setupClickHandler();
    }

    public void resetColor() {
        this.setFill(color);
    }

    private void highlight() {
        if (this.getFill().equals(Color.BLACK)) {
            this.setFill(color.brighter().brighter().brighter().brighter());
        } else if (this.getFill().equals(Color.WHITE) || this.getFill().equals(Color.YELLOW)
                || this.getFill().equals(Color.BLUE) || this.getFill().equals(Color.RED)
                || this.getFill().equals(Color.GREEN)) {
            this.setFill(color.darker());
        }
    }

    private void setupClickHandler() {
        this.setOnMouseClicked(event -> {
            if (this != BoardController.selectedMarble) {
                if (BoardController.selectedMarble != null) {
                    BoardController.selectedMarble.resetColor();
                }
                highlight();
                BoardController.selectedMarble = this;
                event.consume();
            }
        });
    }
}