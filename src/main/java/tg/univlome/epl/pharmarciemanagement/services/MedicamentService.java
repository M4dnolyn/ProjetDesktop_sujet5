package tg.univlome.epl.pharmarciemanagement.services;

import javafx.collections.ObservableList;
import tg.univlome.epl.pharmarciemanagement.dao.MedicamentDAO;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;

public class MedicamentService {

    private final MedicamentDAO medicamentDAO;

    public MedicamentService() {
        this.medicamentDAO = new MedicamentDAO();
    }

    public ObservableList<Medicament> getAllMedicaments() {
        return medicamentDAO.findAll();
    }

    public void addMedicament(Medicament m) {
        medicamentDAO.insert(m);
    }

    public boolean codeExists(String code) {
        return medicamentDAO.codeExists(code);
    }

    public int getTotalCount(ObservableList<Medicament> list) {
        return list.size();
    }

    public double getStockValue(ObservableList<Medicament> list) {
        return list.stream()
                .mapToDouble(m -> m.getQuantite() * Double.parseDouble(m.getPrixUnitaire().replace(",", ".")))
                .sum();
    }

    public long getExpiredCount(ObservableList<Medicament> list) {
        String today = java.time.LocalDate.now().toString();
        return list.stream()
                .filter(m -> m.getDatePeremption().compareTo(today) < 0)
                .count();
    }
}
