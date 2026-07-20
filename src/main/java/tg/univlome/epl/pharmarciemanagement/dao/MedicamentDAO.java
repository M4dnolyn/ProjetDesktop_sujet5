package tg.univlome.epl.pharmarciemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tg.univlome.epl.pharmarciemanagement.models.Medicament;

public class MedicamentDAO {

    public ObservableList<Medicament> findAll() {
        ObservableList<Medicament> list = FXCollections.observableArrayList();
        String sql = "SELECT code, designation, quantite, prix_unitaire, date_peremption FROM medicaments ORDER BY code";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Medicament(
                    rs.getString("code"),
                    rs.getString("designation"),
                    rs.getInt("quantite"),
                    rs.getString("prix_unitaire"),
                    rs.getString("date_peremption")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Medicament m) {
        String sql = "INSERT INTO medicaments (code, designation, quantite, prix_unitaire, date_peremption) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, m.getCode());
            stmt.setString(2, m.getDesignation());
            stmt.setInt(3, m.getQuantite());
            stmt.setString(4, m.getPrixUnitaire());
            stmt.setString(5, m.getDatePeremption());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean codeExists(String code) {
        String sql = "SELECT COUNT(*) FROM medicaments WHERE code = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
