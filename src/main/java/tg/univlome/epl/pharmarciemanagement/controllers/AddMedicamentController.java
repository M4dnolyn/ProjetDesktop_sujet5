package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
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

        try {
            validationService.validateAll(code, designation, quantiteText, prixText, dateText);
            int quantite = Integer.parseInt(quantiteText);
            Medicament m = new Medicament(code, designation, quantite, prixText, dateText);
            homeController.medicamentService.addMedicament(m);
            Window owner = codeField.getScene().getWindow();
            AlertUtils.showInfo(owner, "Succès", "Médicament ajouté !");
            goHome();
        } catch (ValidationException e) {
            AlertUtils.showError(codeField.getScene().getWindow(), "Erreur de validation", e.getMessage());
        } catch (NumberFormatException e) {
            AlertUtils.showError(codeField.getScene().getWindow(), "Erreur", "Une erreur est survenue lors du traitement des données.");
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
