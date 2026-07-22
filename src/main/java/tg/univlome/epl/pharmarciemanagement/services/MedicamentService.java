package tg.univlome.epl.pharmarciemanagement.services;

import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import tg.univlome.epl.pharmarciemanagement.dao.MedicamentDAO;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;

public class MedicamentService {

    private final MedicamentDAO medicamentDAO;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public MedicamentService() {
        this.medicamentDAO = new MedicamentDAO();
    }

    public ObservableList<Medicament> getAllMedicaments() {
        return medicamentDAO.findAll();
    }

    public void addMedicament(Medicament m) {
        medicamentDAO.insert(m);
    }

    public void deleteMedicament(Medicament m) {
        medicamentDAO.deleteByCode(m.getCode());
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
        LocalDate today = LocalDate.now();
        return list.stream()
                .filter(m -> {
                    try {
                        LocalDate peremptionDate = LocalDate.parse(m.getDatePeremption(), DATE_FORMATTER);
                        return peremptionDate.isBefore(today);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();
    }
}
