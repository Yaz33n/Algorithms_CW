package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main extends Application {

    private Stage primaryStage;
    private GridPane optionsGridPane;
    private ControlPanel controlPanel;
    private boolean useHeuristics = false;
    private int doublingHypothesisVal = 1;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage; // Sets the primary stage.
        initComponents(); // Initialize the components
        Scene scene = new Scene(optionsGridPane); // Add the startup selection option menu
        scene.getStylesheets().add("gui/css/main.css"); // Adding the stylesheet for styling
        this.primaryStage.setTitle("Options"); // Setting title
        this.primaryStage.setResizable(false); // Restrict from resizing the window
        this.primaryStage.setScene(scene); // Sets the scene
        this.primaryStage.show(); // Present the window to the user
    }

    private void initComponents() {

        this.optionsGridPane = new GridPane(); // GridPane for add all the child nodes.
        optionsGridPane.setId("startup_optionsGridPane"); // Id for styling and sizing

        // Adding the rows first
        for (int y = 0; y < 3; y++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            row.setMinHeight(10.0);
            row.setPrefHeight(30.0);
            optionsGridPane.getRowConstraints().add(row);
        }

        // Adding the columns
        for (int x = 0; x < 6; x++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.SOMETIMES);
            col.setMinWidth(10.0);
            col.setPrefWidth(100.0);
            optionsGridPane.getColumnConstraints().add(col);
        }

        Button btnShowGrid = new Button("Create Grid");
        btnShowGrid.setId("startup_btnShowGrid");
        GridPane.setMargin(btnShowGrid, new Insets(10, 20, 20, 20));
        this.optionsGridPane.add(btnShowGrid, 0, 3, 6, 1);
        btnShowGrid.setOnAction(e -> {

            // Re-Assigns the current map with the new map
            Main.graph = new DoublingHypothesis(Main.graph, doublingHypothesisVal).getMap();
            controlPanel = new ControlPanel(useHeuristics); // Open control panel
            Scene scene = new Scene(controlPanel); // Add the startup selection option menu
            scene.getStylesheets().add("gui/css/main.css"); // Adding the stylesheet for styling

            this.primaryStage.setTitle("Control Panel");
            this.primaryStage.setScene(scene); // Sets the scene
            this.primaryStage.show(); // Show the scene
            this.primaryStage.setOnCloseRequest(e1 -> controlPanel.getStageForGraph().close());
        });

        Label helperTitle = new Label("Select the grid size and toggle Heuristics");
        helperTitle.setId("startup_HelperText");
        GridPane.setMargin(helperTitle, new Insets(0, 10, 0, 10));
        this.optionsGridPane.add(helperTitle, 0, 0, 6, 1);

        // Checkbox for selecting the heuristic toggle
        CheckBox toggleHeuristics = new CheckBox("Use Heuristics (Faster)");
        toggleHeuristics.setId("startup_cbToggleHeuristics");
        GridPane.setMargin(toggleHeuristics, new Insets(10, 10, 10, 10));
        this.optionsGridPane.add(toggleHeuristics, 1, 2, 4, 1);
        toggleHeuristics.setOnAction(e -> this.useHeuristics = !useHeuristics);

        RadioButton rb20x20 = new RadioButton("20x20");
        rb20x20.setId("startup_rbs");
        GridPane.setMargin(rb20x20, new Insets(10, 10, 10, 10));
        this.optionsGridPane.add(rb20x20, 0, 1, 2, 1);
        rb20x20.setOnAction(e -> doublingHypothesisVal = 1);
        rb20x20.setSelected(true);

        RadioButton rb40x40 = new RadioButton("40x40");
        rb40x40.setId("startup_rbs");
        GridPane.setMargin(rb40x40, new Insets(10, 10, 10, 10));
        this.optionsGridPane.add(rb40x40, 2, 1, 2, 1);
        rb40x40.setOnAction(e -> doublingHypothesisVal = 2);

        RadioButton rb80x80 = new RadioButton("80x80");
        rb80x80.setId("startup_rbs");
        GridPane.setMargin(rb80x80, new Insets(10, 10, 10, 10));
        this.optionsGridPane.add(rb80x80, 4, 1, 2, 1);
        rb80x80.setOnAction(e -> doublingHypothesisVal = 3);

        ToggleGroup gridToggle = new ToggleGroup();
        gridToggle.getToggles().addAll(rb20x20, rb40x40, rb80x80);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int[][] graph = new int[][]{
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

    /* Util Class for ZoomIn and ZoomOut the given map */
    private final class DoublingHypothesis {

        private int[][] arrayToZoom;
        private int[][] zoomedArray;
        private int hypo;

        private DoublingHypothesis(int[][] arrayToZoom, /* Standard size 1 */ int hypo) {
            if (hypo > 5) throw new UnsupportedOperationException("Maximum is 5");
            this.arrayToZoom = arrayToZoom;
            this.hypo = hypo <= 0 ? 1 : hypo;
            int len = arrayToZoom.length << --this.hypo;
            this.zoomedArray = new int[len][len];
        }

        // Not Stable but still works.
        // Max Hypothesis value is 5. Where Over 5 will take forever lol.
        private int[][] getMap() {
            if (++hypo == 1) return arrayToZoom;
            int x = 0, z = 0, c = (int) Math.pow(2, hypo) / 2;
            for (int[] a : arrayToZoom) {
                while (z < c) zoomedArray[x + z++] = repElem(a, c /*Re-Check*/);
                x += z; /*Sync*/
                z = 0; /*Reset*/
            }

            return zoomedArray;
        }

        private int[] repElem(int[] arr, int times) {
            if (times == 1) return arr;
            int[] rep = new int[]{};
            for (int i : arr) {
                // !boxed()-- Insanely Slower than arrayCopy but gets the job done.
                rep = IntStream.concat(Arrays.stream(rep), Arrays.stream(fill(i, times))).toArray();
            }
            return rep;
        }

        private int[] fill(int elem, int times) {
            int[] arr = new int[ /* times == 1 ? ++times : */ times];
            Arrays.fill(arr, elem);
            return arr;
        }

        /* Utils */
        private void print() {
            zoomedArray = getMap();
            for (int[] a : zoomedArray) {
                for (int i : a) System.out.print(i);
                System.out.println();
            }
        }
    }
}
