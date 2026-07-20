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

    private static final String DB_NAME = "app.db";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Path dbPath = Paths.get(DB_NAME);
                boolean needsInit = !Files.exists(dbPath);

                connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

                if (needsInit) {
                    initDatabase();
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
