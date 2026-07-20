package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tg.univlome.epl.pharmarciemanagement.utils.SessionManager;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        String username = SessionManager.getCurrentUser().getUsername();
        welcomeLabel.setText("Bienvenue, " + username + " !");
    }
}
