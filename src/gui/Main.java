package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PATH FINDING ON SQUARED GRID");
        ControlPanel controlPanel = new ControlPanel();
        primaryStage.setScene(new Scene(controlPanel));
        primaryStage.setOnCloseRequest(e -> controlPanel.getStageForGraph().close());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
