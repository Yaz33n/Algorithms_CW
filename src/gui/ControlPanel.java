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

    /*GUI COMPONENTS*/
    private static Stage stageForGrid;
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

        super.setId("controlPanel_AnchorPane");
        super.getChildren().addAll(lblTitle, lblSource, lblTarget, lblMetrics, txtSourceCol, txtSourceRow,
                txtTargetCol, txtTargetRow, rbManhattan, rbChebyshev, rbEuclidean, cbShowGridNumbers,
                cbShowGridWeight, cbShowActualMap, cbShowGridColored, spInputs, spOperations, btnReset, btnFSP,
                lblResult, resultText);

        grid = new SquaredGrid(this); // Creating the grid
        stageForGrid = new Stage(StageStyle.DECORATED);
        stageForGrid.setTitle("PATH FINDING ON SQUARED GRID");
        stageForGrid.setScene(new Scene(grid, 890, 890));
        stageForGrid.setResizable(false);
        stageForGrid.setOnCloseRequest(e -> ((Stage) getScene().getWindow()).close());
        stageForGrid.show();
    }

    private void initLabels() {

        lblTitle = new Label("PATH FINDING ON SQUARED GRID"); // Creates a new label with text
        lblTitle.setId("controlPanel_lblTitle");
        lblTitle.setLayoutX(114.0); // Sets the element in X axis
        lblTitle.setLayoutY(22.0); // Sets the element in Y axis

        lblSource = new Label("SOURCE DESTINATION");
        lblSource.setId("controlPanel_lblSource");
        lblSource.setLayoutX(31.0); // Sets the element in X axis
        lblSource.setLayoutY(101.0); // Sets the element in Y axis

        lblTarget = new Label("TARGET DESTINATION");
        lblTarget.setId("controlPanel_lblTarget");
        lblTarget.setLayoutX(315.0); // Sets the element in X axis
        lblTarget.setLayoutY(101.0); // Sets the element in Y axis

        lblMetrics = new Label("Select a distance based metric: "); // Creates a new label with text
        lblMetrics.setId("controlPanel_lblMetrics");
        lblMetrics.setLayoutX(29.0); // Sets the element in X axis
        lblMetrics.setLayoutY(156.0); // Sets the element in Y axis

        lblResult = new Label("RESULT");
        lblResult.setId("controlPanel_lblResult");
        lblResult.setLayoutX(30.0); // Sets the element in X axis
        lblResult.setLayoutY(331.0); // Sets the element in Y axis

    }

    private void initRadioButtons() {

        rbManhattan = new RadioButton("Manhattan"); // Creates the radio button
        rbManhattan.setId("controlPanel_rbManhattan");
        rbManhattan.setLayoutX(250.0); // Sets the element in X axis
        rbManhattan.setLayoutY(152.0); // Sets the element in Y axis

        rbEuclidean = new RadioButton("Euclidean"); // Creates the radio button
        rbEuclidean.setId("controlPanel_rbEuclidean");
        rbEuclidean.setLayoutX(365.0); // Sets the element in X axis
        rbEuclidean.setLayoutY(152.0); // Sets the element in Y axis

        rbChebyshev = new RadioButton("Chebyshev"); // Creates the radio button
        rbChebyshev.setId("controlPanel_rbChebyshev");
        rbChebyshev.setLayoutX(470.0); // Sets the element in X axis
        rbChebyshev.setLayoutY(152.0); // Sets the element in Y axis

        ToggleGroup toggleGMetrics = new ToggleGroup();
        toggleGMetrics.getToggles().addAll(rbManhattan, rbEuclidean, rbChebyshev); // Adding to radio buttons group

        if (useHeuristics) {
            rbManhattan.setSelected(true); // Initial metric
        } else {
            rbManhattan.setDisable(true);
            rbEuclidean.setDisable(true);
            rbChebyshev.setDisable(true);
        }
    }

    private void initTextBoxes() {

        txtSourceRow = new TextField();
        txtSourceRow.setPromptText("srY");
        txtSourceRow.setLayoutX(178.0);
        txtSourceRow.setLayoutY(100.0);
        txtSourceRow.setPrefSize(47.0, 28.0);

        txtSourceCol = new TextField();
        txtSourceCol.setPromptText("scX");
        txtSourceCol.setLayoutX(231.0);
        txtSourceCol.setLayoutY(100.0);
        txtSourceCol.setPrefSize(47.0, 28.0);

        txtTargetRow = new TextField();
        txtTargetRow.setPromptText("trY");
        txtTargetRow.setLayoutX(463.0);
        txtTargetRow.setLayoutY(100.0);
        txtTargetRow.setPrefSize(47.0, 28.0);

        txtTargetCol = new TextField();
        txtTargetCol.setPromptText("tcX");
        txtTargetCol.setLayoutX(516.0);
        txtTargetCol.setLayoutY(100.0);
        txtTargetCol.setPrefSize(47.0, 28.0);

        resultText = new TextArea("Click Left Mouse Button for entering \nSource Node/Destination &" +
                " Click Right Mouse Button for \nentering the Target Destination Node.");
        resultText.setPrefSize(538.0, 74.0);
        resultText.setEditable(false);
        resultText.setLayoutX(30.0);
        resultText.setLayoutY(363.0);

    }

    private void initCheckBoxes() {

        cbShowGridNumbers = new CheckBox("Show Grid Numbers");
        cbShowGridNumbers.setId("controlPanel_cbEFs");
        cbShowGridNumbers.setLayoutX(33.0);
        cbShowGridNumbers.setLayoutY(213.0);

        cbShowGridWeight = new CheckBox("Show Graph Weights");
        cbShowGridWeight.setId("controlPanel_cbEFs");
        cbShowGridWeight.setLayoutX(171.0);
        cbShowGridWeight.setLayoutY(213.0);

        cbShowActualMap = new CheckBox("Show Actual Map");
        cbShowActualMap.setId("controlPanel_cbEFs");
        cbShowActualMap.setLayoutX(318.0);
        cbShowActualMap.setLayoutY(213.0);

        cbShowGridColored = new CheckBox("Show Colored Grid");
        cbShowGridColored.setId("controlPanel_cbEFs");
        cbShowGridColored.setLayoutX(443.0);
        cbShowGridColored.setLayoutY(213.0);

    }

    private void initSeparators() {

        spInputs = new Separator();
        spInputs.setLayoutX(30.0);
        spInputs.setLayoutY(192.0);
        spInputs.setPrefSize(538.0, 2.0);

        spOperations = new Separator();
        spOperations.setLayoutX(30.0);
        spOperations.setLayoutY(322.0);
        spOperations.setPrefSize(538.0, 2.0);

    }

    private void initButtons() {

        btnReset = new Button("RESET");
        btnReset.setId("controlPanel_btnReset");
        btnReset.setLayoutX(509.0);
        btnReset.setLayoutY(56.0);

        btnFSP = new Button("FIND SHORTEST PATH");
        btnFSP.setId("controlPanel_btnFSP");
        btnFSP.setLayoutX(176.0);
        btnFSP.setLayoutY(258.0);
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

            SquaredGrid.removeLastDrawnPath();
            SquaredGrid.removeLastCheckedNeighbours();
            SquaredGrid.removeSandTDestinations();
        });

        btnFSP.setOnAction(e -> {
            btnFSP.setDisable(true); // Avoid clicking multiple times
            SquaredGrid.removeLastDrawnPath();
            SquaredGrid.removeLastCheckedNeighbours();
            findPath();
            btnFSP.setDisable(false); // Re-Enable the button
        });
    }

    private void findPath() {

        /*The User will choose whether use heuristics or use the exhaustive search.*/
        /*The user will choose the graph zoom level(Doubling hypothesis value)*/
        /*Clear the existing path or use different colors to each path. TODO*/

        int sY, sX, tY, tX, len = Main.graph.length;

        try {
            sY = Integer.parseInt(txtSourceRow.getText());
            sX = Integer.parseInt(txtSourceCol.getText());
            tY = Integer.parseInt(txtTargetRow.getText());
            tX = Integer.parseInt(txtTargetCol.getText());

            if (sY + sX + tY + tX == 0) {
                Utils.alertInfo("Oops, you're in the same node as the Source node!");
                return;
            } else if (sY >= len || sY < 0 || sX >= len || sX < 0) {
                throw new UnsupportedOperationException();
            } else if (tY >= len || tY < 0 || tX >= len || tX < 0) {
                throw new UnsupportedOperationException();
            } else if(Main.graph[sY][sX] == 0 || Main.graph[tY][tX] == 0) {
                Utils.alertInfo("Oops, one of the selected nodes is blocked!");
                return;
            }
        } catch (NumberFormatException | UnsupportedOperationException e1) {
            Utils.alertWarning("The Coordinates either out of Range or Invalid Characters.");
            return;
        }

        // Record the start time in ms.
        final long startTime_Nano = Utils.nanoTimeStamp();
        /*Instantiate the PathFinding class. with the static graph & source,target & selected distance metric type*/
        PathFindingAlgorithm as = new PathFindingAlgorithm(Main.graph, sY, sX, tY, tX, getHeuType());
        /*Find the shortest path.*/
        as.findShortestPath();
        /* Record the elapsed time in milliseconds. */
        final double elapsedTimeMS = Utils.elapsedTimeMS(startTime_Nano);

        // For efficient string concatenating.
        StringBuilder sb = new StringBuilder();
        // Sets the final G cost and the elapsed time to solve the problem
        sb.append("Elapsed Time ms: ").append(String.format("%.2f", elapsedTimeMS)).append("ms")
                .append("\nElapsed Time s: ").append(String.format("%.2f", elapsedTimeMS / 1000.0))
                .append("\nFinal G Cost: ").append(as.getMatrix()[tY][tX].getGCost())
                .append("\nExhaustive Search: ").append(!useHeuristics)
                .append("\nBlue Dots: ").append("The checked neighbouring nodes.")
                .append("\nGreen Boxes: ").append("The final shortest path including start and target.")
                .append("\nPath Through Backwards: ");

        for (Node n : as.getFinalPathNodes()) // Get reconstructed path list and show
            sb.append(" -> ").append(n.getYRowNo()).append(",").append(n.getXColNo());

        resultText.setText(""); // Clear the existing text.
        resultText.setText(sb.toString()); // Set the new run results.

        SquaredGrid.drawPath(as.getFinalPathNodes());
    }

    private PathFindingAlgorithm.Heuristic getHeuType() {

        if (!useHeuristics) {
            Utils.alertInfo("Algorithm used exhaustive search for T without h(n).");
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
        return stageForGrid;
    }
}