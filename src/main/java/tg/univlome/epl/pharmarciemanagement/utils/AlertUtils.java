package tg.univlome.epl.pharmarciemanagement.utils;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Duration;

public class AlertUtils {

    public static void showError(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(owner);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInfo(Window owner, String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInline(Label label, String message, boolean success) {
        label.setText(message);
        label.getStyleClass().removeAll("feedback-error", "feedback-success");
        label.getStyleClass().add(success ? "feedback-success" : "feedback-error");
        label.setVisible(true);
        label.setManaged(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(e -> {
            label.setVisible(false);
            label.setManaged(false);
        });
        pause.play();
    }

    public static void clearInline(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }
}
