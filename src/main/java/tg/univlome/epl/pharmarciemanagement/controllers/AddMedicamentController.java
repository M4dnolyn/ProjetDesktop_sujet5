package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;
import tg.univlome.epl.pharmarciemanagement.utils.AlertUtils;

public class AddMedicamentController {

    @FXML private TextField codeField;
    @FXML private TextField designationField;
    @FXML private TextField quantiteField;
    @FXML private TextField prixField;
    @FXML private DatePicker datePicker;

    private HomeController homeController;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    private void handleSave() {
        String code = codeField.getText().trim();
        String designation = designationField.getText().trim();
        String quantiteText = quantiteField.getText().trim();
        String prixText = prixField.getText().trim();

        if (code.isEmpty() || designation.isEmpty() || quantiteText.isEmpty() || prixText.isEmpty() || datePicker.getValue() == null) {
            AlertUtils.showError("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteText);
            if (quantite < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            AlertUtils.showError("Erreur", "La quantité doit être un entier positif.");
            return;
        }

        double prix;
        try {
            prix = Double.parseDouble(prixText.replace(",", "."));
            if (prix < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            AlertUtils.showError("Erreur", "Le prix doit être un nombre positif.");
            return;
        }

        if (homeController != null && homeController.medicamentService.codeExists(code)) {
            AlertUtils.showError("Erreur", "Ce code médicament existe déjà.");
            return;
        }

        Medicament m = new Medicament(code, designation, quantite, prixText, datePicker.getValue().toString());
        homeController.medicamentService.addMedicament(m);
        AlertUtils.showInfo("Succès", "Médicament ajouté !");
        goHome();
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
