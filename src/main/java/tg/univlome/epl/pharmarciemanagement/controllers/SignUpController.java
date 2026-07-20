package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tg.univlome.epl.pharmarciemanagement.utils.AlertUtils;
import tg.univlome.epl.pharmarciemanagement.services.AuthService;
import tg.univlome.epl.pharmarciemanagement.utils.AlertUtils;

public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            AlertUtils.showError("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        if (!password.equals(confirm)) {
            AlertUtils.showError("Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        if (authService.signup(username, password)) {
            AlertUtils.showInfo("Succès", "Compte créé avec succès !");
            goToLogin();
        } else {
            AlertUtils.showError("Erreur", "Ce nom d'utilisateur existe déjà.");
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
