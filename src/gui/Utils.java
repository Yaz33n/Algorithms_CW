package gui;

import javafx.scene.control.Alert;

public class Utils {

    /* DIVIDE NanoSeconds from this CONSTANT to get the MilliSeconds */
    private static final int MILLISECONDS = 1000000;

    public static void alertWarning(String m) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Warning Message | Inputs Error");
        alert.setContentText(m);
        alert.show();
    }

    public static void alertInfo(String m) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Runtime Information | Searching");
        alert.setContentText(m);
        alert.show();
    }

    public static long nanoTimeStamp() {
        return System.nanoTime();
    }

    public static int elapsedTimeMS(final long start) {
        return (int) (System.nanoTime() - start) / MILLISECONDS;
    }


}
