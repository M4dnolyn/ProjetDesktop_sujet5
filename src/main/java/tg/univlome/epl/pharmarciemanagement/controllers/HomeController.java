package tg.univlome.epl.pharmarciemanagement.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import tg.univlome.epl.pharmarciemanagement.exceptions.DatabaseException;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;
import tg.univlome.epl.pharmarciemanagement.services.MedicamentService;
import tg.univlome.epl.pharmarciemanagement.utils.AlertUtils;
import tg.univlome.epl.pharmarciemanagement.utils.SessionManager;

public class HomeController implements Initializable {

    @FXML private Label welcomeLabel;
    @FXML private Label totalCountLabel;
    @FXML private Label stockValueLabel;
    @FXML private Label expiredCountLabel;

    @FXML private TableView<Medicament> medicamentTable;
    @FXML private TableColumn<Medicament, String> codeColumn;
    @FXML private TableColumn<Medicament, String> designationColumn;
    @FXML private TableColumn<Medicament, Integer> quantiteColumn;
    @FXML private TableColumn<Medicament, Double> prixColumn;
    @FXML private TableColumn<Medicament, String> datePeremptionColumn;
    @FXML private TableColumn<Medicament, String> statusColumn;
    @FXML private TableColumn<Medicament, Void> deleteColumn;

    private final MedicamentService medicamentService = new MedicamentService();
    private ObservableList<Medicament> medicamentList;

    public MedicamentService getMedicamentService() {
        return medicamentService;
    }
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String username = SessionManager.getCurrentUser().getUsername();
        welcomeLabel.setText("Connecté en tant que : " + username);

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        datePeremptionColumn.setCellValueFactory(new PropertyValueFactory<>("datePeremption"));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getStatusLabel(cellData.getValue())));
        statusColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
        setupDeleteColumn();

        styleExpirationCells(codeColumn);
        styleExpirationCells(designationColumn);
        styleExpirationCells(quantiteColumn);
        setupPrixColumn();
        styleExpirationCells(datePeremptionColumn);
        styleExpirationCells(statusColumn);

        loadData();
    }

    public void loadData() {
        try {
            medicamentList = medicamentService.getAllMedicaments();
            medicamentTable.setItems(medicamentList);
            updateStats();
        } catch (DatabaseException e) {
            AlertUtils.showError(medicamentTable.getScene().getWindow(), "Erreur", "Impossible de charger les données : " + e.getMessage());
        }
    }

    private void updateStats() {
        totalCountLabel.setText(String.valueOf(medicamentService.getTotalCount(medicamentList)));
        stockValueLabel.setText(String.format("%.0f FCFA", medicamentService.getStockValue(medicamentList)));
        expiredCountLabel.setText(String.valueOf(medicamentService.getExpiredCount(medicamentList)));
    }

    private void setupDeleteColumn() {
        deleteColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Medicament, Void> call(TableColumn<Medicament, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        deleteButton.setOnAction(event -> {
                            Medicament medicament = getTableView().getItems().get(getIndex());
                            confirmAndDeleteMedicament(medicament);
                        });
                        deleteButton.setMaxWidth(Double.MAX_VALUE);
                        deleteButton.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER);
                        if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        });
    }

    private void confirmAndDeleteMedicament(Medicament medicament) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.initOwner(medicamentTable.getScene().getWindow());
        confirmationAlert.initModality(Modality.WINDOW_MODAL);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText("Supprimer le médicament");
        confirmationAlert.setContentText("Voulez-vous vraiment supprimer le médicament '" + medicament.getDesignation() + "' ?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                medicamentService.deleteMedicament(medicament);
                loadData();
            } catch (DatabaseException e) {
                AlertUtils.showError(medicamentTable.getScene().getWindow(), "Erreur", "Impossible de supprimer le médicament : " + e.getMessage());
            }
        }
    }

    private void setupPrixColumn() {
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        prixColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("%.2f", item));
                    styleCellForStatus(getTableRow().getItem());
                }
            }

            private void styleCellForStatus(Medicament medicament) {
                String status = getStatusLabel(medicament);
                switch (status) {
                    case "Périmé" -> setStyle("-fx-background-color: rgba(254, 202, 202, 0.8); -fx-text-fill: #b91c1c;");
                    case "Expire bientôt" -> setStyle("-fx-background-color: rgba(254, 243, 199, 0.8); -fx-text-fill: #b45309;");
                    default -> setStyle("");
                }
            }
        });
    }

    private <T> void styleExpirationCells(TableColumn<Medicament, T> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item == null ? "" : item.toString());
                    styleCellForStatus(getTableRow().getItem());
                }
            }

            private void styleCellForStatus(Medicament medicament) {
                String status = getStatusLabel(medicament);
                switch (status) {
                    case "Périmé" -> setStyle("-fx-background-color: rgba(254, 202, 202, 0.8); -fx-text-fill: #b91c1c;");
                    case "Expire bientôt" -> setStyle("-fx-background-color: rgba(254, 243, 199, 0.8); -fx-text-fill: #b45309;");
                    default -> setStyle("");
                }
            }
        });
    }

    private String getStatusLabel(Medicament medicament) {
        try {
            LocalDate peremptionDate = LocalDate.parse(medicament.getDatePeremption(), DATE_FORMATTER);
            LocalDate today = LocalDate.now();
            LocalDate soonThreshold = today.plusDays(7);

            if (peremptionDate.isBefore(today)) {
                return "Périmé";
            } else if (!peremptionDate.isAfter(soonThreshold)) {
                return "Expire bientôt";
            }
        } catch (Exception ignored) {
        }
        return "OK";
    }

    @FXML
    private void handleAddMedicament() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/add_medicament.fxml"));
            Parent root = loader.load();
            AddMedicamentController controller = loader.getController();
            controller.setHomeController(this);
            Stage stage = (Stage) medicamentTable.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRefresh() {
        loadData();
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