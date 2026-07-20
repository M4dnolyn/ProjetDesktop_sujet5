package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;
import tg.univlome.epl.pharmarciemanagement.services.MedicamentService;
import tg.univlome.epl.pharmarciemanagement.utils.SessionManager;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private Label totalCountLabel;
    @FXML private Label stockValueLabel;
    @FXML private Label expiredCountLabel;

    @FXML private TableView<Medicament> medicamentTable;
    @FXML private TableColumn<Medicament, String> codeColumn;
    @FXML private TableColumn<Medicament, String> designationColumn;
    @FXML private TableColumn<Medicament, Integer> quantiteColumn;
    @FXML private TableColumn<Medicament, String> prixColumn;
    @FXML private TableColumn<Medicament, String> datePeremptionColumn;

    final MedicamentService medicamentService = new MedicamentService();
    private ObservableList<Medicament> medicamentList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String username = SessionManager.getCurrentUser().getUsername();
        welcomeLabel.setText("Connecté : " + username);

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        datePeremptionColumn.setCellValueFactory(new PropertyValueFactory<>("datePeremption"));

        datePeremptionColumn.setCellFactory(column -> new javafx.scene.control.TableCell<Medicament, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    String today = java.time.LocalDate.now().toString();
                    if (item.compareTo(today) < 0) {
                        setStyle("-fx-background-color: #ffcccc;");
                    } else if (item.compareTo(java.time.LocalDate.now().plusDays(30).toString()) < 0) {
                        setStyle("-fx-background-color: #ffeaa7;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        loadData();
    }

    public void loadData() {
        medicamentList = medicamentService.getAllMedicaments();
        medicamentTable.setItems(medicamentList);
        updateStats();
    }

    private void updateStats() {
        totalCountLabel.setText(String.valueOf(medicamentService.getTotalCount(medicamentList)));
        stockValueLabel.setText(String.format("%.0f FCFA", medicamentService.getStockValue(medicamentList)));
        expiredCountLabel.setText(String.valueOf(medicamentService.getExpiredCount(medicamentList)));
    }

    @FXML
    private void handleAddMedicament() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_medicament.fxml"));
            Parent root = loader.load();
            AddMedicamentController controller = loader.getController();
            controller.setHomeController(this);
            Stage stage = (Stage) medicamentTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout() {
        SessionManager.logout();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
