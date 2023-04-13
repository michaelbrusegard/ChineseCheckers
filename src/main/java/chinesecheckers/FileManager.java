package chinesecheckers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileManager implements IFileManager {
    public static void load(String filePath) {
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

    public static void save(String filePath) {
        if (filePath == null) {return;}
        try {
            String saveInfo = "";
            for (Node marble : BoardController.marbleGroup.getChildren()) {
                String army = ((Marble) marble).getArmy();
                String hole = ((Marble) marble).getHole().getCircle().getId().substring(4);
                saveInfo += army + ":" + hole + "\n";
            }
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(saveInfo);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String selectorWindow(String title, boolean save) {
        BoardController.resetAllMarbleColors();
        BoardController.resetAllHoleColors();
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("ChineseCheckers files (*.chc)", "*.chc");
        fileChooser.getExtensionFilters().add(filter);
        File file = save ? fileChooser.showSaveDialog(stage) : fileChooser.showOpenDialog(stage);

        if (file != null) {
            return file.getPath();
        } else {
            return null;
        }
    }
}
