package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import tg.univlome.epl.pharmarciemanagement.exceptions.DatabaseException;
import tg.univlome.epl.pharmarciemanagement.exceptions.ValidationException;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;
import tg.univlome.epl.pharmarciemanagement.services.ValidationService;
import tg.univlome.epl.pharmarciemanagement.utils.AlertUtils;

public class AddMedicamentController {

    @FXML private TextField codeField;
    @FXML private TextField designationField;
    @FXML private TextField quantiteField;
    @FXML private TextField prixField;
    @FXML private DatePicker datePicker;
    @FXML private Label feedbackLabel;

    private HomeController homeController;
    private final ValidationService validationService = new ValidationService();

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    private void handleSave() {
        String code = codeField.getText().trim();
        String designation = designationField.getText().trim();
        String quantiteText = quantiteField.getText().trim();
        String prixText = prixField.getText().trim();
        String dateText = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "";

        AlertUtils.clearInline(feedbackLabel);
        try {
            validationService.validateAll(code, designation, quantiteText, prixText, dateText);
            int quantite = Integer.parseInt(quantiteText);
            double prix = Double.parseDouble(prixText.trim().replace(",", "."));
            Medicament m = new Medicament(code, designation, quantite, prix, dateText);
            homeController.getMedicamentService().addMedicament(m);
            AlertUtils.showInline(feedbackLabel, "Médicament ajouté !", true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> goHome());
            pause.play();
        } catch (ValidationException e) {
            AlertUtils.showInline(feedbackLabel, e.getMessage(), false);
        } catch (DatabaseException e) {
            AlertUtils.showInline(feedbackLabel, "Erreur de base de données : " + e.getMessage(), false);
        } catch (NumberFormatException e) {
            AlertUtils.showInline(feedbackLabel, "Une erreur est survenue lors du traitement des données.", false);
        }
    }

    @FXML
    private void handleCancel() {
        goHome();
    }

    private void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
            Parent root = loader.load();
            ((HomeController) loader.getController()).loadData();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
            Stage stage = (Stage) codeField.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
