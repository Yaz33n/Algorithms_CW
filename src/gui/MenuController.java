package gui;//package gui;
//
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
//public class MenuController {
//
//    private Stage gridWindow;
//    private ControlPanel menuView;
//    private SquaredGrid grid;
//    private AnchorPane gridPaneWrapper;
//
//    MenuController(ControlPanel menuView) {
//
//        this.menuView = menuView;
//        this.gridWindow = new Stage(StageStyle.DECORATED);
//        this.grid = new SquaredGrid(menuView, graph, 1);
//
//        gridPaneWrapper = new AnchorPane(grid);
//        gridPaneWrapper.setPrefSize(800.0, 800.0);
//        gridPaneWrapper.setStyle("-fx-background-color: #232323");
//
//        gridWindow.setTitle("PATH FINDING ON SQUARED GRID");
//        gridWindow.setScene(new Scene(gridPaneWrapper, 800.0, 800.0));
//        gridWindow.setResizable(false);
//        gridWindow.show();
//
        // <Label layoutX="30.0" layoutY="331.0" prefHeight="26.0" prefWidth="66.0" text="RESULTS" textFill="WHITE">
        //            <font>
        //                <Font name="Consolas Bold" size="16.0" />
        //            </font>
        //        </Label>
        //      <TextArea layoutX="29.0" layoutY="363.0" prefHeight="74.0" prefWidth="538.0" />
//    }
//
//    public void showGridNumbers(boolean val) {
//        grid.viewGridNumbers(val);
//    }
//
//    public void showNodeWeights(boolean val) {
//        grid.viewNodeWeights(val);
//    }
//
//    public void showColoredGrid(boolean val) {
//        grid.viewColoredGrid(val);
//    }
//
//    public void showActualMap(boolean val) {
//
//        if (val) {
//
//    }
//
//    public void findPath() {
//
//        int sX = Integer.parseInt(menuView.getTxtSourceX().getText());
//        int sY = Integer.parseInt(menuView.getTxtSourceY().getText());
//        int tX = Integer.parseInt(menuView.getTxtTargetX().getText());
//        int tY = Integer.parseInt(menuView.getTxtTargetY().getText());
//
//        System.out.println(sX + " " + sY + "\n" + tX + " " + tY);
//
//
//    }


//}
