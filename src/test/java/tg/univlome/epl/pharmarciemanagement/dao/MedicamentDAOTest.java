package tg.univlome.epl.pharmarciemanagement.dao;

import org.junit.Test;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;

import static org.junit.Assert.*;

public class MedicamentDAOTest {

    @Test
    public void testInsertAndFindAll() {
        DatabaseConnection.close();
        java.nio.file.Path dbPath = java.nio.file.Paths.get("app.db");
        try {
            java.nio.file.Files.deleteIfExists(dbPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatabaseConnection.getConnection();
        MedicamentDAO dao = new MedicamentDAO();
        assertNotNull(dao.findAll());
    }
}
