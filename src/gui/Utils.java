package gui;

import javafx.scene.control.Alert;

public class Utils {

    /* DIVIDE NanoSeconds from this CONSTANT to get the MilliSeconds */
    private static final double SECONDS = 1000.0;

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
        return System.currentTimeMillis();
    }

    public static double elapsedTimeMS(final long start) {
        return (System.currentTimeMillis() - start);
    }

    public static double elapsedTimeSC(final long start) {
        return (System.currentTimeMillis() - start) / SECONDS;
    }

}
