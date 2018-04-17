package gui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControlPanel extends AnchorPane {

    private Label lblTitle, lblSource, lblTarget, lblMetrics, lblResult;
    private TextField txtSourceX, txtSourceY, txtTargetX, txtTargetY;
    private TextArea resultText;
    private ToggleGroup toggleGMetrics;
    private RadioButton rbManhattan, rbChebyshev, rbEuclidean;
    private CheckBox cbShowGridNumbers, cbShowGridWeight, cbShowActualMap, cbShowGridColored;
    private Separator spInputs, spOperations;
    private Button btnReset, btnFSP;

    private SquaredGrid grid;
    private Stage stageForGraph;

    ControlPanel() {
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
        super.getChildren().addAll(lblTitle, lblSource, lblTarget, lblMetrics, txtSourceX, txtSourceY,
                txtTargetX, txtTargetY, rbManhattan, rbChebyshev, rbEuclidean, cbShowGridNumbers,
                cbShowGridWeight, cbShowActualMap, cbShowGridColored, spInputs, spOperations, btnReset, btnFSP,
                lblResult, resultText);

        this.grid = new SquaredGrid(this, ControlPanel.graph,
                /* Doubling Hypothesis value 1 is default*/3); // Creating the grid
        System.out.println(graph.length);

        stageForGraph = new Stage(StageStyle.DECORATED);
        stageForGraph.setTitle("PATH FINDING ON SQUARED GRID");
        stageForGraph.setScene(new Scene(this.grid, 800, 800));
        stageForGraph.setResizable(false);
        stageForGraph.show();

    }

    private void initLabels() {

        this.lblTitle = new Label("PATH FINDING ON SQUARED GRID"); // Creates a new label with text
        this.lblTitle.setFont(new Font("Consolas Bold", 25.0)); // Setting font & size
        this.lblTitle.setTextFill(Color.WHITE); // Setting label font color
        this.lblTitle.setLayoutX(114.0); // Sets the element in X axis
        this.lblTitle.setLayoutY(22.0); // Sets the element in Y axis

        this.lblSource = new Label("SOURCE DESTINATION");
        this.lblSource.setPrefSize(134.0, 26.0);
        this.lblSource.setFont(new Font(/*"Consolas Bold",*/ 13.0)); // Setting font size
        this.lblSource.setTextFill(Color.WHITE); // Setting label font color
        this.lblSource.setLayoutX(31.0); // Sets the element in X axis
        this.lblSource.setLayoutY(101.0); // Sets the element in Y axis

        this.lblTarget = new Label("TARGET DESTINATION");
        this.lblTarget.setPrefSize(134.0, 26.0);
        this.lblTarget.setFont(new Font(/*"Consolas Bold",*/ 13.0)); // Setting font size
        this.lblTarget.setTextFill(Color.WHITE); // Setting label font color
        this.lblTarget.setLayoutX(315.0); // Sets the element in X axis
        this.lblTarget.setLayoutY(101.0); // Sets the element in Y axis

        this.lblMetrics = new Label("Select a distance based metric: "); // Creates a new label with text
        this.lblMetrics.setPrefSize(258.0, 20.0);
        this.lblMetrics.setFont(new Font("System", 15.0)); // Setting font & size
        this.lblMetrics.setTextFill(Color.WHITE); // Setting label font color
        this.lblMetrics.setLayoutX(29.0); // Sets the element in X axis
        this.lblMetrics.setLayoutY(156.0); // Sets the element in Y axis

        this.lblResult = new Label("RESULT");
        this.lblResult.setPrefSize(66.0, 26.0);
        this.lblResult.setFont(new Font("Consolas Bold", 16.0)); // Setting font & size
        this.lblResult.setTextFill(Color.WHITE); // Setting label font color
        this.lblResult.setLayoutX(30.0); // Sets the element in X axis
        this.lblResult.setLayoutY(331.0); // Sets the element in Y axis


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

        this.toggleGMetrics = new ToggleGroup(); // Creates a toggle group for metrics
        this.toggleGMetrics.getToggles().addAll(rbManhattan, rbEuclidean, rbChebyshev); // Adding to radio buttons group

    }

    private void initTextBoxes() {

        this.txtSourceX = new TextField();
        this.txtSourceX.setPromptText("X");
        this.txtSourceX.setLayoutX(178.0);
        this.txtSourceX.setLayoutY(100.0);
        this.txtSourceX.setPrefSize(47.0, 28.0);

        this.txtSourceY = new TextField();
        this.txtSourceY.setPromptText("Y");
        this.txtSourceY.setLayoutX(231.0);
        this.txtSourceY.setLayoutY(100.0);
        this.txtSourceY.setPrefSize(47.0, 28.0);

        this.txtTargetX = new TextField();
        this.txtTargetX.setPromptText("X");
        this.txtTargetX.setLayoutX(463.0);
        this.txtTargetX.setLayoutY(100.0);
        this.txtTargetX.setPrefSize(47.0, 28.0);

        this.txtTargetY = new TextField();
        this.txtTargetY.setPromptText("Y");
        this.txtTargetY.setLayoutX(516.0);
        this.txtTargetY.setLayoutY(100.0);
        this.txtTargetY.setPrefSize(47.0, 28.0);

        this.resultText = new TextArea("No Results Yet!");
        this.resultText.setPrefSize(538.0, 74.0);
        this.resultText.setDisable(true);
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

        this.btnReset = new Button("RESET");
        this.btnReset.setFont(new Font("System Bold", 11.0));
        this.btnReset.setTextFill(Color.GRAY);
        this.btnReset.setLayoutX(512.0);
        this.btnReset.setLayoutY(56.0);
        this.btnReset.setPrefSize(54.0, 28.0);

        this.btnFSP = new Button("FIND SHORTEST PATH");
        this.btnFSP.setFont(new Font("System Bold", 18.0));
        this.btnFSP.setTextFill(Color.GRAY);
        this.btnFSP.setLayoutX(176.0);
        this.btnFSP.setLayoutY(258.0);
        this.btnFSP.setPrefSize(246.0, 42.0);
    }

    private void addEventHandlers() {

        cbShowGridNumbers.setOnAction(e -> {
            grid.viewGridNumbers(cbShowGridNumbers.isSelected());
        });

        cbShowGridWeight.setOnAction(e -> {
            grid.viewNodeWeights(cbShowGridWeight.isSelected());
        });

        cbShowGridColored.setOnAction(e -> {
            grid.viewColoredGrid(cbShowGridColored.isSelected());
        });

        cbShowActualMap.setOnAction(e -> {
            grid.viewActualMap(cbShowActualMap.isSelected());
        });

        btnReset.setOnAction(e -> {

            cbShowGridWeight.setSelected(false);
            cbShowGridColored.setSelected(false);
            cbShowGridNumbers.setSelected(false);
            cbShowActualMap.setSelected(false);

            grid.viewNodeWeights(false);
            grid.viewGridNumbers(false);
            grid.viewActualMap(false);
            grid.viewColoredGrid(false);

            txtSourceX.setText("");
            txtSourceY.setText("");
            txtTargetX.setText("");
            txtTargetY.setText("");
        });

        btnFSP.setOnAction(e -> {
//            controller.findPath();
        });

    }

    public TextField getTxtSourceX() {
        return txtSourceX;
    }

    public TextField getTxtSourceY() {
        return txtSourceY;
    }

    public TextField getTxtTargetX() {
        return txtTargetX;
    }

    public TextField getTxtTargetY() {
        return txtTargetY;
    }

    public ToggleGroup getToggleGMetrics() {
        return toggleGMetrics;
    }

    public CheckBox getCbShowGridNumbers() {
        return cbShowGridNumbers;
    }

    public CheckBox getCbShowGridWeight() {
        return cbShowGridWeight;
    }

    public CheckBox getCbShowActualMap() {
        return cbShowActualMap;
    }

    public CheckBox getCbShowGridColored() {
        return cbShowGridColored;
    }

    public Stage getStageForGraph() {
        return stageForGraph;
    }

    private static final int[][] graph = new int[][]{
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
