package gui;

import algo.Node;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Vector;

public class SquaredGrid extends AnchorPane {

    /*Declaring the gridPane size for all screens 880 * 880 (PX)*/
    private static final double gridWidthNHeight = 880d;
    /*Adjusting the row min and pref height*/
    private static final double rowMinAndPrefHeight = gridWidthNHeight / Main.graph.length;
    /*Adjusting the col min and pref width*/
    private static final double colMinAndPrefWidth = gridWidthNHeight / Main.graph.length;
    /*Adjusting the rectangle dimensions*/
    private static final double recDimensions = gridWidthNHeight / Main.graph.length;

    private static ControlPanel controlPanelView; // control panel view
    private static GridPane gridPane;
    private static ImageView actualMap;

    private static Vector<Rectangle> gridBoxes, lastDrawnPath;
    private static Vector<Text> gridNumbers, graphWeight;
    private static Vector<Circle> lastCheckedNeighbours;
    private static Rectangle startPosRec, targetPosRec;

    SquaredGrid(ControlPanel controlPanelView) {
        SquaredGrid.controlPanelView = controlPanelView;
        gridPane = new GridPane();
        initLayoutComponents();
    }

    private void initLayoutComponents() {

        // Setting up the main anchor pane
        setPrefSize(gridWidthNHeight, gridWidthNHeight);
        // Changing the background color
        setStyle("-fx-background-color: #232323");

        gridPane.setLayoutX(10); // X position
        gridPane.setLayoutY(10); // Y position
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setGridLinesVisible(true);
        gridPane.setPrefSize(gridWidthNHeight, gridWidthNHeight);

        for (int[] ignored /* Element Ignored */ : Main.graph) {
            // Adding the rows to the GridPane
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            row.setMinHeight(rowMinAndPrefHeight);
            row.setPrefHeight(rowMinAndPrefHeight);
            row.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(row);

            // Adding columns for the GridPane
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setMinWidth(colMinAndPrefWidth);
            col.setPrefWidth(colMinAndPrefWidth);
            col.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(col);
        }

        // This is for reference add/remove elements.
        gridBoxes = new Vector<>(); // To keep track of the colored rectangles.
        gridNumbers = new Vector<>(); // To keep track of the grid coordinates text node.
        graphWeight = new Vector<>(); // To keep track of the grid weight text node.

        lastDrawnPath = new Vector<>(); // Keep track of the last drawnPath nodes
        lastCheckedNeighbours = new Vector<>(); // Keep track of the last checkedNeighbours nodes

        /*For indicate the clicked source node*/
        startPosRec = new Rectangle(recDimensions / 2, recDimensions / 2);
        startPosRec.setStroke(Color.TRANSPARENT);
        startPosRec.setFill(Color.LIGHTGREEN);

        /*For indicate the clicked target node*/
        targetPosRec = new Rectangle(recDimensions / 2, recDimensions / 2);
        targetPosRec.setStroke(Color.TRANSPARENT);
        targetPosRec.setFill(Color.DARKRED);

        addGridBoxes(); // Adding the rectangles in to each box
        getChildren().add(gridPane); // setting the gridPane inside to the anchorPane
    }

    private void addGridBoxes() {
        Platform.runLater(() -> {
            for (int r = 0; r < Main.graph.length; r++) {
                for (int c = 0; c < Main.graph[r].length; c++) {

                    final Rectangle rec = new Rectangle(recDimensions, recDimensions);
                    final int row = r, col = c;
                    rec.setArcHeight(5);
                    rec.setArcWidth(5);
                    rec.setStrokeType(StrokeType.OUTSIDE);
                    rec.setStroke(GridColors.NON_COLORED_GRID_BORDER.getPaint());
                    rec.setFill(getGreyScaleColor(Main.graph[row][col]));

                    // adding the event listener for the mouse.
                    rec.setOnMouseClicked(e -> {
                        controlPanelView.getCbShowGridNumbers().setSelected(false);
                        controlPanelView.getCbShowGridWeight().setSelected(false);
                        viewGridNumbers(false);
                        viewNodeWeights(false);

                        if (e.getButton() == MouseButton.PRIMARY) {
                            if (Main.graph[row][col] != 0) { // Checking if the node is blocked
                                gridPane.getChildren().remove(startPosRec);
                                controlPanelView.getTxtSourceRow().setText(String.valueOf(row));
                                controlPanelView.getTxtSourceCol().setText(String.valueOf(col));
                                gridPane.add(startPosRec, col, row);
                            } else {
                                Utils.alertWarning("Source Node blocked! Please click a open node.");
                            }
                        } else if (e.getButton() == MouseButton.SECONDARY) {
                            if (Main.graph[row][col] != 0) { // Checking if the node is blocked
                                gridPane.getChildren().remove(targetPosRec);
                                controlPanelView.getTxtTargetRow().setText(String.valueOf(row));
                                controlPanelView.getTxtTargetCol().setText(String.valueOf(col));
                                gridPane.add(targetPosRec, col, row);
                            } else {
                                Utils.alertWarning("Target Node blocked! Please click a open node.");
                            }
                        }
                    });

                    gridBoxes.add(rec);
                    gridPane.add(rec, c, r);
                }
            }
        });
    }

    public void viewColoredGrid(final boolean show) {
        Platform.runLater(() -> {
            for (Rectangle r : gridBoxes) {
                final int row = GridPane.getRowIndex(r), col = GridPane.getColumnIndex(r);
                r.setStroke(show ? GridColors.COLORED_GRID_BORDER.getPaint() :
                        GridColors.NON_COLORED_GRID_BORDER.getPaint());
                r.setFill(show ? getRGBColor(Main.graph[row][col]) :
                        getGreyScaleColor(Main.graph[row][col]));
            }
        });
    }

    public void viewGridNumbers(final boolean show) {
        Platform.runLater(() -> {
            if (show)
                for (int r = 0; r < Main.graph.length; r++) {
                    for (int c = 0; c < Main.graph[r].length; c++) {
                        Text helper = new Text(r + "," + c);
                        helper.setFontSmoothingType(FontSmoothingType.LCD);
                        helper.setFont(new Font(12));
                        helper.setFill(GridColors.GRID_BOX_TEXT_COLOR.getPaint());
                        gridNumbers.add(helper);
                        gridPane.add(helper, c, r);
                    }
                }
            else if (gridNumbers != null && gridNumbers.size() > 0)
                gridPane.getChildren().removeAll(gridNumbers);
        });
    }

    public void viewNodeWeights(final boolean show) {
        Platform.runLater(() -> {
            if (show)
                for (int r = 0; r < Main.graph.length; r++) {
                    for (int c = 0; c < Main.graph[r].length; c++) {
                        Text weight = new Text(Integer.toString(Main.graph[r][c]));
                        weight.setFontSmoothingType(FontSmoothingType.LCD);
                        weight.setFont(new Font(12));
                        weight.setFill(GridColors.GRID_BOX_TEXT_COLOR.getPaint());

                        graphWeight.add(weight);
                        gridPane.add(weight, c, r);
                    }
                }
            else if (graphWeight != null && graphWeight.size() > 0)
                gridPane.getChildren().removeAll(graphWeight);
        });
    }

    public void viewActualMap(boolean show) {
        Platform.runLater(() -> {
            if (show) {
                actualMap = new ImageView(new Image("gui/images/map.jpg"));
                actualMap.setFitWidth(880);
                actualMap.setFitHeight(880);
                actualMap.setLayoutX(10);
                actualMap.setLayoutY(10);
                actualMap.setPickOnBounds(true);
                actualMap.setPreserveRatio(true);
                actualMap.setSmooth(true);
                actualMap.setCache(true);
                actualMap.setOpacity(0.1);
                getChildren().remove(gridPane);
                getChildren().add(actualMap);
            } else {
                if (!getChildren().contains(gridPane)) {
                    getChildren().remove(actualMap);
                    getChildren().add(gridPane);
                }
            }
        });
    }

    public static void drawPath(List<Node> finalPath) {
        for (Node n : finalPath) {
            Rectangle p = new Rectangle(recDimensions / 1.7, recDimensions / 1.7);
            p.setStroke(Color.TRANSPARENT);
            p.setFill(Color.GREEN);
            lastDrawnPath.add(p);
            gridPane.add(p, n.getXColNo(), n.getYRowNo());
        }
    }

    public static void colorCheckingNeighbours(int row, int col) {
        Circle checked = new Circle(2, Color.ROYALBLUE);
        checked.setStroke(Color.TRANSPARENT);
        lastCheckedNeighbours.add(checked);
        gridPane.add(checked, col, row);
    }

    public static void removeLastDrawnPath() {
        gridPane.getChildren().removeAll(lastDrawnPath);
    }

    public static void removeLastCheckedNeighbours() {
        gridPane.getChildren().removeAll(lastCheckedNeighbours);
    }

    // Gets the Monochrome version of colors
    private Paint getGreyScaleColor(int weight) {
        switch (weight) {
            case 1:
                return GridColors.WHITE_SHADE_OF_GREY.getPaint();
            case 2:
                return GridColors.LIGHT_SHADE_OF_GREY.getPaint();
            case 3:
                return GridColors.MIDDLE_SHADE_OF_GREY.getPaint();
            case 4:
                return GridColors.DARK_SHADE_OF_GREY.getPaint();
            default:
                return GridColors.DARK_BLACK.getPaint();
        }
    }

    // Gets the RGB version of colors
    private Paint getRGBColor(int weight) {
        switch (weight) {
            case 1:
                return GridColors.COLORED_GRASS.getPaint();
            case 2:
                return GridColors.COLORED_BUSHES.getPaint();
            case 3:
                return GridColors.COLORED_TREE.getPaint();
            case 4:
                return GridColors.COLORED_ROCKS.getPaint();
            default:
                return GridColors.COLORED_WATER.getPaint();
        }
    }

    // Enumeration Class for Colors (Paints)
    private enum GridColors {

        DARK_BLACK(Paint.valueOf("#000000")),
        WHITE_SHADE_OF_GREY(Paint.valueOf("#FFFFFF")),
        LIGHT_SHADE_OF_GREY(Paint.valueOf("#F0F0F0")),
        MIDDLE_SHADE_OF_GREY(Paint.valueOf("#E0E0E0")),
        DARK_SHADE_OF_GREY(Paint.valueOf("#D0D0D0")),

        COLORED_GRASS(Paint.valueOf("#61ff00")),
        COLORED_BUSHES(Paint.valueOf("#3b9b00")),
        COLORED_TREE(Paint.valueOf("#173d00")),
        COLORED_ROCKS(Paint.valueOf("#545b57")),
        COLORED_WATER(Paint.valueOf("#01569b")),

        COLORED_GRID_BORDER(Color.BLACK),
        NON_COLORED_GRID_BORDER(Color.ROYALBLUE),
        GRID_BOX_TEXT_COLOR(Color.ROYALBLUE);

        private final Paint paint;

        private GridColors(Paint paint) {
            this.paint = paint;
        }

        public Paint getPaint() {
            return paint;
        }
    }
}
