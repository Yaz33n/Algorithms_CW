package gui;

import javafx.scene.control.Alert;

public class Utils {

    /* DIVIDE MilliSeconds from this CONSTANT to get the Seconds */
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

    public static long currentMilliseconds() {
        return System.currentTimeMillis();
    }

    public static double elapsedTimeMS(final long start) {
        return (System.currentTimeMillis() - start);
    }

    @SuppressWarnings("unused")
    public static double elapsedTimeSC(final long start) {
        return (System.currentTimeMillis() - start) / SECONDS;
    }

}
