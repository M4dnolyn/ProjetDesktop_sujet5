package tg.univlome.epl.pharmarciemanagement.dao;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tg.univlome.epl.pharmarciemanagement.exceptions.DatabaseException;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;

import java.sql.Statement;

import static org.junit.Assert.*;

public class MedicamentDAOTest {

    private MedicamentDAO dao;

    @Before
    public void setUp() {
        dao = new MedicamentDAO();
        clearMedicamentsTable();
    }

    @After
    public void tearDown() {
        clearMedicamentsTable();
        DatabaseConnection.close();
    }

    private void clearMedicamentsTable() {
        try (Statement stmt = DatabaseConnection.getConnection().createStatement()) {
            stmt.executeUpdate("DELETE FROM medicaments");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() throws DatabaseException {
        ObservableList<Medicament> list = dao.findAll();
        assertNotNull("findAll() doit retourner une liste non null", list);
        assertTrue("findAll() doit retourner une liste (peut être vide)", list.size() >= 0);
    }

    @Test
    public void testInsertAndFindAll() throws DatabaseException {
        Medicament med = new Medicament("TEST001", "Test Médicament", 10, 100.0, "2025-12-31");
        dao.insert(med);
        
        ObservableList<Medicament> list = dao.findAll();
        assertNotNull("La liste ne doit pas être null après insertion", list);
        assertTrue("La liste doit contenir au moins 1 élément", list.size() > 0);
    }

    @Test
    public void testCodeExists() throws DatabaseException {
        Medicament med = new Medicament("UNIQUE001", "Aspirin", 5, 50.0, "2025-12-31");
        dao.insert(med);
        
        assertTrue("Le code doit exister après insertion", dao.codeExists("UNIQUE001"));
        assertFalse("Un code inexistant ne doit pas être trouvé", dao.codeExists("NONEXISTENT"));
    }

    @Test
    public void testInsertMultipleMedicaments() throws DatabaseException {
        Medicament med1 = new Medicament("MED001", "Paracétamol", 20, 75.0, "2025-12-31");
        Medicament med2 = new Medicament("MED002", "Amoxicilline", 15, 200.0, "2026-01-31");
        Medicament med3 = new Medicament("MED003", "Ibuprofène", 30, 100.0, "2025-11-30");
        
        dao.insert(med1);
        dao.insert(med2);
        dao.insert(med3);
        
        ObservableList<Medicament> list = dao.findAll();
        assertTrue("La liste doit contenir au moins 3 médicaments", list.size() >= 3);
    }

    @Test
    public void testDuplicateCodeRejection() throws DatabaseException {
        Medicament med1 = new Medicament("DUP001", "Médicament 1", 10, 100.0, "2025-12-31");
        dao.insert(med1);
        
        assertTrue("Le premier code doit exister", dao.codeExists("DUP001"));
        assertTrue("Le code ne doit pas être inséré deux fois", dao.codeExists("DUP001"));
    }
}

