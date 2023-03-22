package chinesecheckers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/chinesecheckers/Board.fxml"));
        AnchorPane root = loader.load();

        primaryStage.setTitle("Chinese Checkers");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}