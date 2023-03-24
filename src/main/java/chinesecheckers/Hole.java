package chinesecheckers;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Circle;

public class Hole {
    private final Circle circle;
    private final int[] coordinates;
    private boolean occupied;
    private final List<Hole> neighbours = new ArrayList<>();

    public Hole(Circle circle, int[] coordinates) {
        this.circle = circle;
        this.coordinates = coordinates;
        this.occupied = false;
        setupClickHandler();
    }

    public Circle getCircle() {
        return circle;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public List<Hole> getNeighbours() {
        return neighbours;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void findNeighbours() {
        int[][] neighboursRelativeCoordinates = {
            {1, 0, -1},
            {1, -1, 0},
            {0, -1, 1},
            {-1, 0, 1},
            {-1, 1, 0},
            {0, 1, -1}
        };
        for (int i = 0; i < neighboursRelativeCoordinates.length; i++) {
            int[] neighbourCoordinate = new int[] { coordinates[0] + neighboursRelativeCoordinates[i][0], coordinates[1] + neighboursRelativeCoordinates[i][1], coordinates[2] + neighboursRelativeCoordinates[i][2] };
            Hole neighbour = BoardController.getHole(neighbourCoordinate);
            if (neighbour != null) {
                neighbours.add(neighbour);
            }
        }
    }

    private void setupClickHandler() {
        this.circle.setOnMouseClicked(event -> {
            if (!occupied) {
                if (BoardController.selectedMarble != null) {
                    BoardController.selectedMarble.move(this);
                }
            }
        });
    }
}