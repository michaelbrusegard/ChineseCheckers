package chinesecheckers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BoardController {

    @FXML
    private AnchorPane root;

    @FXML
    private Button save;

    @FXML
    private Button load;

    public static Group marbleGroup;
    public static CheckBox highlightMoves;
    public static CheckBox enforceMoves;
    public static Marble selectedMarble = null;
    private final static List<Hole> holes = new ArrayList<>();

    public void initialize() {
        highlightMoves = (CheckBox) root.lookup("#highlightMoves");
        enforceMoves = (CheckBox) root.lookup("#enforceMoves");
        marbleGroup = (Group) root.lookup("#marbleGroup");
        setupClickHandler(root);
        setupClickHandler(highlightMoves);
        setupClickHandler(enforceMoves);
        createHoleObjects();
        loadStartBoard();
        load.setOnAction((event) -> {
            FileManager.load(FileManager.selectorWindow("Open file", false));
        });
        save.setOnAction((event) -> {
            FileManager.save(FileManager.selectorWindow("Save to file", true));
        });
    }

    private void loadStartBoard() {
        InputStream inputStream = getClass().getResourceAsStream("/chinesecheckers/StartBoard.chc");
        try {
            String startBoardContent = new String(inputStream.readAllBytes());
            String[] lines = startBoardContent.split("\\r?\\n");
            for (String line : lines) {
                String[] lineData = line.split(":");
                createMarble(lineData[1], lineData[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHoleObjects() {
        List<Node> circleNodes = new ArrayList<>();
        for (Node child : root.getChildren()) {
            if (child instanceof Circle && child.getId().startsWith("hole")) {
                circleNodes.add(child);
            }
        }

        for (Node node : circleNodes) {
            Circle circle = (Circle) node;
            String positionId = circle.getId().substring(4);
            int posX = posIdNumToInt(positionId.substring(0, 2));
            int posY = posIdNumToInt(positionId.substring(2, 4));
            int posZ = posIdNumToInt(positionId.substring(4, 6));
            Hole hole = new Hole(circle, new int[] { posX, posY, posZ });
            holes.add(hole);
        }

        for (Hole hole : holes) {
            hole.findNeighbours();
        }
    }

    private int posIdNumToInt(String posIdNum) {
        char sign = posIdNum.charAt(0);
        int value = Integer.parseInt(posIdNum.substring(1));
        if (sign == 'N') {
            value = -value;
        }
        return value;
    }

    public static Hole getHole(int[] neighbourCoordinate) {
        for (Hole hole : holes) {
            if (Arrays.equals(hole.getCoordinates(), neighbourCoordinate)) {
                return hole;
            }
        }
        return null;
    }

    public static void createMarble(String holeId, String color) {
        Hole currentHole = null;
        for (Hole hole : holes) {
            if (hole.getCircle().getId().substring(4).equals(holeId)) {
                currentHole = hole;
            }
        }
        currentHole.setOccupied(true);
        Marble marble = new Marble(currentHole, color);
        marbleGroup.getChildren().add(marble);
    }

    private void setupClickHandler(Node node) {
        node.setOnMouseClicked(event -> {
            resetAllMarbleColors();
            resetAllHoleColors();
            selectedMarble = null;
        });
    }

    public static void resetAllMarbleColors() {
        for (Node marble : marbleGroup.getChildren()) {
            ((Marble) marble).resetColor();
        }
    }

    public static void cleanSlateMarbles() {
        marbleGroup.getChildren().clear();

        for (Hole hole : holes) {
            hole.setOccupied(false);
        }
    }

    public static void resetAllHoleColors() {
        for (Hole hole : holes) {
            hole.getCircle().setFill(Color.web("#8B4513"));
        }
    }
}