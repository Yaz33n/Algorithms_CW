package gui;

import algo.PathFindingAlgorithm;
import algo.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControlPanel extends AnchorPane {

    private static final int MILLISECONDS = 1000000;
    /*GUI COMPONENTS*/
    private static Stage stageForGraph;
    private static Label lblTitle, lblSource, lblTarget, lblMetrics, lblResult;
    private static TextField txtSourceRow, txtSourceCol, txtTargetRow, txtTargetCol;
    private static TextArea resultText;
    private static RadioButton rbManhattan, rbChebyshev, rbEuclidean;
    private static CheckBox cbShowGridNumbers, cbShowGridWeight, cbShowActualMap, cbShowGridColored;
    private static Separator spInputs, spOperations;
    private static Button btnReset, btnFSP;

    private static SquaredGrid grid;
    private static boolean useHeuristics;

    ControlPanel(boolean useHeuristics) {
        ControlPanel.useHeuristics = useHeuristics;
        initComponents();
    }

    private void initComponents() {

        initLabels();
        initTextBoxes();
        initRadioButtons();
        initCheckBoxes();
        initSeparators();
        initButtons();
        addEventHandlers();

        super.setPrefSize(600, 465);
        super.setStyle("-fx-background-color: #232323");
        super.getChildren().addAll(lblTitle, lblSource, lblTarget, lblMetrics, txtSourceCol, txtSourceRow,
                txtTargetCol, txtTargetRow, rbManhattan, rbChebyshev, rbEuclidean, cbShowGridNumbers,
                cbShowGridWeight, cbShowActualMap, cbShowGridColored, spInputs, spOperations, btnReset, btnFSP,
                lblResult, resultText);

        /* Doubling Hypothesis :: 1 is default */
        grid = new SquaredGrid(this); // Creating the grid

        stageForGraph = new Stage(StageStyle.DECORATED);
        stageForGraph.setTitle("PATH FINDING ON SQUARED GRID");
        stageForGraph.setScene(new Scene(grid, 850, 850));
        stageForGraph.setResizable(false);
        stageForGraph.show();
    }

    private void initLabels() {

        lblTitle = new Label("PATH FINDING ON SQUARED GRID"); // Creates a new label with text
        lblTitle.setFont(new Font("Consolas Bold", 25.0)); // Setting font & size
        lblTitle.setTextFill(Color.WHITE); // Setting label font color
        lblTitle.setLayoutX(114.0); // Sets the element in X axis
        lblTitle.setLayoutY(22.0); // Sets the element in Y axis

        lblSource = new Label("SOURCE DESTINATION");
        lblSource.setPrefSize(134.0, 26.0);
        lblSource.setFont(new Font(/*"Consolas Bold",*/ 13.0)); // Setting font size
        lblSource.setTextFill(Color.WHITE); // Setting label font color
        lblSource.setLayoutX(31.0); // Sets the element in X axis
        lblSource.setLayoutY(101.0); // Sets the element in Y axis

        lblTarget = new Label("TARGET DESTINATION");
        lblTarget.setPrefSize(134.0, 26.0);
        lblTarget.setFont(new Font(/*"Consolas Bold",*/ 13.0)); // Setting font size
        lblTarget.setTextFill(Color.WHITE); // Setting label font color
        lblTarget.setLayoutX(315.0); // Sets the element in X axis
        lblTarget.setLayoutY(101.0); // Sets the element in Y axis

        lblMetrics = new Label("Select a distance based metric: "); // Creates a new label with text
        lblMetrics.setPrefSize(258.0, 20.0);
        lblMetrics.setFont(new Font("System", 15.0)); // Setting font & size
        lblMetrics.setTextFill(Color.WHITE); // Setting label font color
        lblMetrics.setLayoutX(29.0); // Sets the element in X axis
        lblMetrics.setLayoutY(156.0); // Sets the element in Y axis

        lblResult = new Label("RESULT");
        lblResult.setPrefSize(66.0, 26.0);
        lblResult.setFont(new Font("Consolas Bold", 16.0)); // Setting font & size
        lblResult.setTextFill(Color.WHITE); // Setting label font color
        lblResult.setLayoutX(30.0); // Sets the element in X axis
        lblResult.setLayoutY(331.0); // Sets the element in Y axis

    }

    private void initRadioButtons() {

        this.rbManhattan = new RadioButton("Manhattan"); // Creates the radio button
        this.rbManhattan.setPrefSize(87.0, 28.0); // Setting the preferred sizes
        this.rbManhattan.setLayoutX(250.0); // Sets the element in X axis
        this.rbManhattan.setLayoutY(152.0); // Sets the element in Y axis
        this.rbManhattan.setFont(new Font(13));
        this.rbManhattan.setTextFill(Paint.valueOf("#1aff00")); // Change the color in radio button text

        this.rbEuclidean = new RadioButton("Euclidean"); // Creates the radio button
        this.rbEuclidean.setPrefSize(87.0, 28.0); // Setting the preferred sizes
        this.rbEuclidean.setLayoutX(365.0); // Sets the element in X axis
        this.rbEuclidean.setLayoutY(152.0); // Sets the element in Y axis
        this.rbEuclidean.setFont(new Font(13));
        this.rbEuclidean.setTextFill(Paint.valueOf("#ff8686")); // Change the color in radio button text

        this.rbChebyshev = new RadioButton("Chebyshev"); // Creates the radio button
        this.rbChebyshev.setPrefSize(87.0, 28.0); // Setting the preferred sizes
        this.rbChebyshev.setLayoutX(470.0); // Sets the element in X axis
        this.rbChebyshev.setLayoutY(152.0); // Sets the element in Y axis
        this.rbChebyshev.setFont(new Font(13));
        this.rbChebyshev.setTextFill(Paint.valueOf("#ffcb3e")); // Change the color in radio button text

        ToggleGroup toggleGMetrics = new ToggleGroup();
        toggleGMetrics.getToggles().addAll(rbManhattan, rbEuclidean, rbChebyshev); // Adding to radio buttons group
        if(useHeuristics) {
            rbManhattan.setSelected(true); // Initial metric
        } else {
            rbManhattan.setDisable(true);
            rbEuclidean.setDisable(true);
            rbChebyshev.setDisable(true);
        }


    }

    private void initTextBoxes() {

        this.txtSourceRow = new TextField();
        this.txtSourceRow.setPromptText("srY");
        this.txtSourceRow.setLayoutX(178.0);
        this.txtSourceRow.setLayoutY(100.0);
        this.txtSourceRow.setPrefSize(47.0, 28.0);

        this.txtSourceCol = new TextField();
        this.txtSourceCol.setPromptText("scX");
        this.txtSourceCol.setLayoutX(231.0);
        this.txtSourceCol.setLayoutY(100.0);
        this.txtSourceCol.setPrefSize(47.0, 28.0);

        this.txtTargetRow = new TextField();
        this.txtTargetRow.setPromptText("trY");
        this.txtTargetRow.setLayoutX(463.0);
        this.txtTargetRow.setLayoutY(100.0);
        this.txtTargetRow.setPrefSize(47.0, 28.0);

        this.txtTargetCol = new TextField();
        this.txtTargetCol.setPromptText("tcX");
        this.txtTargetCol.setLayoutX(516.0);
        this.txtTargetCol.setLayoutY(100.0);
        this.txtTargetCol.setPrefSize(47.0, 28.0);

        this.resultText = new TextArea("No Results Yet!");
        this.resultText.setPrefSize(538.0, 74.0);
        this.resultText.setEditable(false);
        this.resultText.setLayoutX(30.0);
        this.resultText.setLayoutY(363.0);

    }

    private void initCheckBoxes() {

        this.cbShowGridNumbers = new CheckBox("Show Grid Numbers");
        this.cbShowGridNumbers.setFont(new Font(12));
        this.cbShowGridNumbers.setTextFill(Color.WHITE);
        this.cbShowGridNumbers.setLayoutX(33.0);
        this.cbShowGridNumbers.setLayoutY(213.0);

        this.cbShowGridWeight = new CheckBox("Show Graph Weights");
        this.cbShowGridWeight.setFont(new Font(12));
        this.cbShowGridWeight.setTextFill(Color.WHITE);
        this.cbShowGridWeight.setLayoutX(171.0);
        this.cbShowGridWeight.setLayoutY(213.0);

        this.cbShowActualMap = new CheckBox("Show Actual Map");
        this.cbShowActualMap.setFont(new Font(12));
        this.cbShowActualMap.setTextFill(Color.WHITE);
        this.cbShowActualMap.setLayoutX(318.0);
        this.cbShowActualMap.setLayoutY(213.0);

        this.cbShowGridColored = new CheckBox("Show Colored Grid");
        this.cbShowGridColored.setFont(new Font(12));
        this.cbShowGridColored.setTextFill(Color.WHITE);
        this.cbShowGridColored.setLayoutX(443.0);
        this.cbShowGridColored.setLayoutY(213.0);

    }

    private void initSeparators() {

        this.spInputs = new Separator();
        this.spInputs.setLayoutX(30.0);
        this.spInputs.setLayoutY(192.0);
        this.spInputs.setPrefSize(538.0, 2.0);

        this.spOperations = new Separator();
        this.spOperations.setLayoutX(30.0);
        this.spOperations.setLayoutY(322.0);
        this.spOperations.setPrefSize(538.0, 2.0);

    }

    private void initButtons() {

        btnReset = new Button("RESET");
        btnReset.setId("btnReset");
        btnReset.setLayoutX(509.0);
        btnReset.setLayoutY(56.0);

        btnFSP = new Button("FIND SHORTEST PATH");
        btnFSP.setFont(new Font("System Bold", 18.0));
        btnFSP.setTextFill(Color.DARKGRAY);
        btnFSP.setLayoutX(176.0);
        btnFSP.setLayoutY(258.0);
        btnFSP.setPrefSize(246.0, 42.0);
    }

    private void addEventHandlers() {

        /* Add/Clears cell coordinates in the gridPane. */
        cbShowGridNumbers.setOnAction(e -> grid.viewGridNumbers(cbShowGridNumbers.isSelected()));

        /* Add/Clear node weights in the gridPane. */
        cbShowGridWeight.setOnAction(e -> grid.viewNodeWeights(cbShowGridWeight.isSelected()));

        /* Hide/Reveal the colored cells in weighted coordinates. */
        cbShowGridColored.setOnAction(e -> grid.viewColoredGrid(cbShowGridColored.isSelected()));

        /* Hide/Reveal the Actual map in the grid. */
        cbShowActualMap.setOnAction(e -> grid.viewActualMap(cbShowActualMap.isSelected()));

        /* Resets all the changes made to the UI */
        btnReset.setOnAction(e -> {

            cbShowGridWeight.setSelected(false);
            cbShowGridColored.setSelected(false);
            cbShowGridNumbers.setSelected(false);
            cbShowActualMap.setSelected(false);

            grid.viewNodeWeights(false);
            grid.viewGridNumbers(false);
            grid.viewActualMap(false);
            grid.viewColoredGrid(false);

            txtSourceCol.setText("");
            txtSourceRow.setText("");
            txtTargetCol.setText("");
            txtTargetRow.setText("");
            resultText.setText("");
        });

        btnFSP.setOnAction(e -> {

            /*
             * This method will initiate the PathFindingAlgorithm Class.
             * The User will choose whether use heuristics or use the exhaustive search.
             * The user will choose the graph zoom level(Doubling hypothesis value)
             */

            // Clear the existing path or use different colors to each path.
            int sY = -1, sX = -1, tY = -1, tX = -1;

            try {
                sY = Integer.parseInt(txtSourceRow.getText());
                sX = Integer.parseInt(txtSourceCol.getText());
                tY = Integer.parseInt(txtTargetRow.getText());
                tX = Integer.parseInt(txtTargetCol.getText());
            } catch (NumberFormatException exception) {
                // show alert
                return;
            }

            if (sY + sX + tY + tX == -4) {
                // show alert
                return;
            }

            System.out.println(getHeuType().toString());
            /* SENSITIVE PART - After this, the code will never checks for input exceptions. */
            // -------------------------------------------------------------------------------
            // Record the start time in ms.
            long startTime_Nano = System.nanoTime();
            /*Instantiate the PathFinding class. with the static graph & source,target & selected distance metric type*/
            PathFindingAlgorithm as = new PathFindingAlgorithm(Main.graph, sY, sX, tY, tX, getHeuType());
            /*Find the shortest path.*/
            as.findShortestPath();
            /* Record the elapsed time in milliseconds. */
            long elapsedTime = elapsedTimeMS(startTime_Nano);

            resultText.setText(""); // Clear the existing text.

            // For efficient string concatenating.
            StringBuilder sb = new StringBuilder();
            // Sets the final G cost and the elapsed time to solve the problem
            sb.append("Elapsed Time for the algorithm: ").append(elapsedTime).append("ms").append("\n")
                    .append("Final G Cost: ").append(as.getMatrix()[tY][tX].getGCost())
                    .append("\n Path Through Backwards: ");

            for (Node n : as.getFinalPathNodes())
                sb.append(n.getYRowNo()).append(",").append(n.getXColNo()).append("-> ");

            resultText.setText(sb.toString());
            grid.drawPath(as.getFinalPathNodes());

        });
    }

    private static long nanoTimeStamp() {
        return System.nanoTime();
    }

    private static int elapsedTimeMS(final long start) {
        return (int) (System.nanoTime() - start) / MILLISECONDS;
    }

    private PathFindingAlgorithm.Heuristic getHeuType() {

        if (!useHeuristics) {
            // Alert using exhaustive search.
            System.out.println("Using Exhaustive Search!");
            return PathFindingAlgorithm.Heuristic.NONE;
        }

        if (rbManhattan.isSelected()) {
            return PathFindingAlgorithm.Heuristic.MANHATTAN;
        } else if (rbEuclidean.isSelected()) {
            return PathFindingAlgorithm.Heuristic.EUCLIDEAN;
        } else {
            return PathFindingAlgorithm.Heuristic.CHEBYSHEV;
        }

    }

    public TextField getTxtSourceCol() {
        return txtSourceCol;
    }

    public TextField getTxtSourceRow() {
        return txtSourceRow;
    }

    public TextField getTxtTargetCol() {
        return txtTargetCol;
    }

    public TextField getTxtTargetRow() {
        return txtTargetRow;
    }

    public CheckBox getCbShowGridNumbers() {
        return cbShowGridNumbers;
    }

    public CheckBox getCbShowGridWeight() {
        return cbShowGridWeight;
    }

    public Stage getStageForGraph() {
        return stageForGraph;
    }

}
