package tg.univlome.epl.pharmarciemanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tg.univlome.epl.pharmarciemanagement.dao.DatabaseConnection;

public class PharmarcieManagement extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseConnection.getConnection();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        // Load platform-appropriate icon: prefer .ico on Windows, PNG multires on Linux/others
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                java.io.InputStream icoStream = getClass().getResourceAsStream("/images/icon.ico");
                if (icoStream != null) primaryStage.getIcons().add(new Image(icoStream));
                java.io.InputStream logoStream = getClass().getResourceAsStream("/images/logo.png");
                if (logoStream != null) primaryStage.getIcons().add(new Image(logoStream));
            } else {
                // Prefer higher-resolution PNGs on Linux/macOS
                java.io.InputStream png256 = getClass().getResourceAsStream("/images/icon-256.png");
                java.io.InputStream png128 = getClass().getResourceAsStream("/images/icon-128.png");
                java.io.InputStream png64 = getClass().getResourceAsStream("/images/icon-64.png");
                if (png256 != null) primaryStage.getIcons().add(new Image(png256));
                if (png128 != null) primaryStage.getIcons().add(new Image(png128));
                if (png64 != null) primaryStage.getIcons().add(new Image(png64));
                // fallback to logo.png
                java.io.InputStream logoStream = getClass().getResourceAsStream("/images/logo.png");
                if (logoStream != null) primaryStage.getIcons().add(new Image(logoStream));
            }
        } catch (Exception e) {
            // silent fallback
        }
        primaryStage.setTitle("Gestion de Pharmacie");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        DatabaseConnection.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
