package chinesecheckers;

import javafx.scene.shape.Circle;

public class Hole {
    private final int index;
    private final Circle circle;
    private boolean occupied;

    public Hole(int index, Circle circle) {
        this.index = index;
        this.circle = circle;
        this.occupied = false;
        setupClickHandler();
    }

    public int getIndex() {
        return index;
    }

    public Circle getCircle() {
        return circle;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    private void setupClickHandler() {
        this.circle.setOnMouseClicked(event -> {
            if (!occupied) {
                System.out.println("HOLE CLICKED");
            }
        });
    }
}