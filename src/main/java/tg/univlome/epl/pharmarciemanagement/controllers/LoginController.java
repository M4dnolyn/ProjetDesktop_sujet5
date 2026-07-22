package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
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

    private final AuthService authService = new AuthService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        Window owner = usernameField.getScene().getWindow();
        if (username.isEmpty() || password.isEmpty()) {
            AlertUtils.showError(owner, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            User user = authService.login(username, password);
            SessionManager.login(user);
            loadHome();
        } catch (AuthenticationException e) {
            AlertUtils.showError(owner, "Erreur", e.getMessage());
        } catch (DatabaseException e) {
            AlertUtils.showError(owner, "Erreur", "Erreur de base de données : " + e.getMessage());
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
