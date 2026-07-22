package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tg.univlome.epl.pharmarciemanagement.exceptions.AuthenticationException;
import tg.univlome.epl.pharmarciemanagement.exceptions.DatabaseException;
import tg.univlome.epl.pharmarciemanagement.models.User;
import tg.univlome.epl.pharmarciemanagement.services.AuthService;
import tg.univlome.epl.pharmarciemanagement.utils.AlertUtils;
import tg.univlome.epl.pharmarciemanagement.utils.SessionManager;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label feedbackLabel;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        AlertUtils.clearInline(feedbackLabel);
        if (username.isEmpty() || password.isEmpty()) {
            AlertUtils.showInline(feedbackLabel, "Veuillez remplir tous les champs.", false);
            return;
        }

        try {
            User user = authService.login(username, password);
            SessionManager.login(user);
            loadHome();
        } catch (AuthenticationException e) {
            AlertUtils.showInline(feedbackLabel, e.getMessage(), false);
        } catch (DatabaseException e) {
            AlertUtils.showInline(feedbackLabel, "Erreur de base de données : " + e.getMessage(), false);
        }
    }

    @FXML
    private void handleGoToSignUp() {
        loadView("signup.fxml");
    }

    private void loadHome() {
        loadView("home.fxml");
    }

    private void loadView(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + fxml));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
