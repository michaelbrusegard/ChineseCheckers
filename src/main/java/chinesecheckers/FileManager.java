package chinesecheckers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileManager {
    public static void load() {
        String filePath = selectorWindow("Open file", false);
        if (filePath == null) {return;}
        BoardController.cleanSlateMarbles();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineData = line.split(":");
                BoardController.createMarble(lineData[1], lineData[0]);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        String filePath = selectorWindow("Save to file", true);
        if (filePath == null) {return;}
        try {
            String saveInfo = "";
            for (Node marble : BoardController.marbleGroup.getChildren()) {
                String army = ((Marble) marble).getArmy();
                String hole = ((Marble) marble).getHole().getCircle().getId();
                saveInfo += army + ":" + hole + "\n";
            }
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(saveInfo);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String selectorWindow(String title, boolean save) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("ChineseCheckers files (*.cc)", "*.cc");
        fileChooser.getExtensionFilters().add(filter);
        File file = save ? fileChooser.showSaveDialog(stage) : fileChooser.showOpenDialog(stage);

        if (file != null) {
            return file.getPath();
        } else {
            return null;
        }
    }
}
