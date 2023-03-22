package chinesecheckers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Marble extends Circle {

    private final Color color;
    private Hole hole;

    public Marble(Hole hole, Color color) {
        super(hole.getCircle().getLayoutX(), hole.getCircle().getLayoutY(), BoardController.marbleRadius, color);
        this.color = color;
        this.hole = hole;
        setupClickHandler();
    }

    public void resetColor() {
        this.setFill(color);
    }

    public void move(Hole hole) {
        this.setCenterX(hole.getCircle().getLayoutX());
        this.setCenterY(hole.getCircle().getLayoutY());
        this.hole.setOccupied(false);
        this.hole = hole;
        this.hole.setOccupied(true);
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

    private void markLegalMoves() {

    }

    private void setupClickHandler() {
        this.setOnMouseClicked(event -> {
            if (this != BoardController.selectedMarble) {
                if (BoardController.selectedMarble != null) {
                    BoardController.selectedMarble.resetColor();
                }
                highlight();
                markLegalMoves();
                BoardController.selectedMarble = this;
                event.consume();
            }
        });
    }
}