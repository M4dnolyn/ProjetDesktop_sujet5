package tg.univlome.epl.pharmarciemanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tg.univlome.epl.pharmarciemanagement.dao.DatabaseConnection;

public class PharmarcieManagement extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseConnection.getConnection();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        primaryStage.setTitle("Gestion de Pharmacie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
