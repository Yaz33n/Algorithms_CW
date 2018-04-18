package gui;

import algo.AStarAlgorithm;
import algo.Node;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {

//    @Override
    public void start(Stage primaryStage) {
        // Creates a new control panel
        ControlPanel controlPanel = new ControlPanel();
        // Sets the control panel to the scene
        Scene scene = new Scene(controlPanel);
        // Adding the stylesheet for styling
        scene.getStylesheets().add("gui/css/main.css");

        primaryStage.setTitle("PATH FINDING ON SQUARED GRID");
        primaryStage.setScene(scene);// Sets the scene
        primaryStage.setOnCloseRequest(e -> controlPanel.getStageForGraph().close());
        primaryStage.show();
    }

    public static void main(String[] args) {
//        launch(args);
        AStarAlgorithm as = new AStarAlgorithm(graph, 0, 0, 4, 7,
                AStarAlgorithm.Heuristic.MANHATTAN);
        as.findShortestPath();

        for (Node n : as.getFinalPathNodes()) {
            System.out.println("V     " + n);
        }

    }

    public static final int[][] graph = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1},
            {4, 4, 1, 4, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1},
            {4, 4, 4, 4, 4, 4, 1, 1, 2, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1, 1},
            {4, 4, 4, 4, 4, 4, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 1, 1, 1},
            {1, 1, 4, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 2, 1, 1, 2, 2, 1, 1},
            {1, 4, 4, 1, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 1, 1},
            {4, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1},
            {1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4},
            {1, 1, 2, 3, 3, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4},
            {1, 2, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1},
            {1, 2, 3, 2, 2, 2, 3, 2, 4, 1, 1, 1, 4, 4, 4, 4, 2, 1, 1, 1},
            {1, 2, 2, 1, 1, 1, 4, 4, 4, 4, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1},
            {1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 4, 4, 4, 4, 1, 1, 1, 2, 2, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0, 0, 0, 1, 1, 0, 0, 0},
            {1, 2, 2, 2, 2, 2, 1, 1, 1, 2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
}
