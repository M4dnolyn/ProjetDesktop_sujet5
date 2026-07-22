package tg.univlome.epl.pharmarciemanagement.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseConnection {

    private static final String DB_FILENAME = "app.db";
    private static Connection connection;
    private static boolean shutdownHookRegistered = false;

    private static String getDbPath() {
        // Utilise le répertoire utilisateur pour stocker la BD
        String userHome = System.getProperty("user.home");
        Path appDir = Paths.get(userHome, ".pharmacie");
        try {
            Files.createDirectories(appDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appDir.resolve(DB_FILENAME).toString();
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String dbPath = getDbPath();
                Path dbFile = Paths.get(dbPath);
                boolean needsInit = !Files.exists(dbFile);

                connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

                // Toujours s'assurer que le schéma nécessaire existe (création idempotente)
                ensureSchema();

                if (!shutdownHookRegistered) {
                    shutdownHookRegistered = true;
                    Runtime.getRuntime().addShutdownHook(new Thread(DatabaseConnection::close));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void initDatabase() throws Exception {
        InputStream in = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("database/schema.sql");
        if (in == null) {
            throw new RuntimeException("schema.sql not found in classpath");
        }
        String sql = new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining("\n"));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    private static void ensureSchema() {
        String createUsers = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    username TEXT UNIQUE NOT NULL,\n" +
                "    password_hash TEXT NOT NULL,\n" +
                "    created_at TEXT DEFAULT CURRENT_TIMESTAMP\n" +
                ")";

        String createMedicaments = "CREATE TABLE IF NOT EXISTS medicaments (\n" +
                "    code TEXT PRIMARY KEY,\n" +
                "    designation TEXT NOT NULL,\n" +
                "    quantite INTEGER NOT NULL CHECK (quantite >= 0),\n" +
                "    prix_unitaire REAL NOT NULL CHECK (prix_unitaire >= 0),\n" +
                "    date_peremption TEXT NOT NULL\n" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsers);
            stmt.execute(createMedicaments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
