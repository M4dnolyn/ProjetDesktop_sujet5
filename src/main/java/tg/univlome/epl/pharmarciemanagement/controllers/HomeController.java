package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tg.univlome.epl.pharmarciemanagement.utils.SessionManager;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        String username = SessionManager.getCurrentUser().getUsername();
        welcomeLabel.setText("Bienvenue, " + username + " !");
    }

    @FXML
    private void handleLogout() {
        SessionManager.logout();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
