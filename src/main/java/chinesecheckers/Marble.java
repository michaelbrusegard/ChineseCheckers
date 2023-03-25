package chinesecheckers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Marble extends Circle {

    private final Color color;
    private final String army;
    private Hole hole;

    private static final Map<String, Color> colors;

    static {
        Map<String, Color> map = new HashMap<>();
        map.put("red", Color.RED);
        map.put("green", Color.GREEN);
        map.put("blue", Color.BLUE);
        map.put("black", Color.BLACK);
        map.put("white", Color.WHITE);
        map.put("yellow", Color.YELLOW);
        colors = Collections.unmodifiableMap(map);
    }

    public Marble(Hole hole, String color) {
        super(hole.getCircle().getLayoutX(), hole.getCircle().getLayoutY(), 18, colors.get(color));
        this.color = colors.get(color);
        this.hole = hole;
        this.army = color;
        setupClickHandler();
    }

    public Color getColor() {
        return this.color;
    }

    public String getArmy() {
        return this.army;
    }

    public Hole getHole() {
        return this.hole;
    }

    public void resetColor() {
        this.setFill(color);
    }

    public void move(Hole hole) {
        boolean isLegalMove = true;
        if (BoardController.enforceMoves.isSelected()) {
            isLegalMove = false;
            for (Hole legalHole : findHoles(this.hole)) {
                if (hole == legalHole) {
                    isLegalMove = true;
                }
            }
        }
        if (isLegalMove) {
            this.setCenterX(hole.getCircle().getLayoutX());
            this.setCenterY(hole.getCircle().getLayoutY());
            this.hole.setOccupied(false);
            this.hole = hole;
            this.hole.setOccupied(true);
        }
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

    private void highlightLegalMoves() {
        if (BoardController.highlightMoves.isSelected()) {
            for (Hole hole : findHoles(this.hole)) {
                hole.getCircle().setFill(Color.web("#A06E0D"));
            }
        }
    }

    private List<Hole> findHoles(Hole currentHole) {
        List<Hole> holes = new ArrayList<>();
        for (Hole hole : currentHole.getNeighbours()) {
            if (!hole.isOccupied()) {
                holes.add(hole);
            }
        }
        holes.addAll(findJumpHoles(this.hole, new ArrayList<>()));
        holes.remove(this.hole);
        return holes;
    }

    private List<Hole> findJumpHoles(Hole currentHole, List<Hole> jumpHoles) {
        jumpHoles.add(currentHole);

        for (Hole currentHoleNeighbour : currentHole.getNeighbours()) {
            if (currentHoleNeighbour.isOccupied()) {
                for (Hole occupiedHoleNeighbour : currentHoleNeighbour.getNeighbours()) {
                    if (occupiedOnSameAxis(occupiedHoleNeighbour, currentHole)) {
                        if (!occupiedHoleNeighbour.isOccupied()
                                && !currentHole.getNeighbours().contains(occupiedHoleNeighbour)
                                && !jumpHoles.contains(occupiedHoleNeighbour)) {
                            findJumpHoles(occupiedHoleNeighbour, jumpHoles);
                        }
                    }
                }
            }
        }
        return jumpHoles;
    }

    private boolean occupiedOnSameAxis(Hole occupiedHoleNeighbour, Hole currentHole) {
        return occupiedHoleNeighbour.getCoordinates()[0] == currentHole.getCoordinates()[0]
        || occupiedHoleNeighbour.getCoordinates()[1] == currentHole.getCoordinates()[1]
        || occupiedHoleNeighbour.getCoordinates()[2] == currentHole.getCoordinates()[2];
    }

    private void setupClickHandler() {
        this.setOnMouseClicked(event -> {
            if (this != BoardController.selectedMarble) {
                if (BoardController.selectedMarble != null) {
                    BoardController.selectedMarble.resetColor();
                    BoardController.resetAllHoleColors();
                }
                highlight();
                highlightLegalMoves();
                BoardController.selectedMarble = this;
                event.consume();
            }
        });
    }
}