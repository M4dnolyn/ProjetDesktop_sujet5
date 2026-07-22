package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import tg.univlome.epl.pharmarciemanagement.exceptions.AuthenticationException;
import tg.univlome.epl.pharmarciemanagement.exceptions.DatabaseException;
import tg.univlome.epl.pharmarciemanagement.services.AuthService;
import tg.univlome.epl.pharmarciemanagement.utils.AlertUtils;

public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label feedbackLabel;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        AlertUtils.clearInline(feedbackLabel);
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            AlertUtils.showInline(feedbackLabel, "Veuillez remplir tous les champs.", false);
            return;
        }

        if (!password.equals(confirm)) {
            AlertUtils.showInline(feedbackLabel, "Les mots de passe ne correspondent pas.", false);
            return;
        }

        try {
            authService.signup(username, password);
            AlertUtils.showInline(feedbackLabel, "Compte créé avec succès !", true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> goToLogin());
            pause.play();
        } catch (AuthenticationException e) {
            AlertUtils.showInline(feedbackLabel, e.getMessage(), false);
        } catch (DatabaseException e) {
            AlertUtils.showInline(feedbackLabel, "Erreur de base de données : " + e.getMessage(), false);
        }
    }

    @FXML
    private void handleGoToLogin() {
        goToLogin();
    }

    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
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
