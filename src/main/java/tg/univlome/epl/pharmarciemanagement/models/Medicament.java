package tg.univlome.epl.pharmarciemanagement.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Medicament {

    private final StringProperty code;
    private final StringProperty designation;
    private final IntegerProperty quantite;
    private final DoubleProperty prixUnitaire;
    private final StringProperty datePeremption;

    public Medicament() {
        this.code = new SimpleStringProperty();
        this.designation = new SimpleStringProperty();
        this.quantite = new SimpleIntegerProperty();
        this.prixUnitaire = new SimpleDoubleProperty();
        this.datePeremption = new SimpleStringProperty();
    }

    public Medicament(String code, String designation, int quantite, double prixUnitaire, String datePeremption) {
        this.code = new SimpleStringProperty(code);
        this.designation = new SimpleStringProperty(designation);
        this.quantite = new SimpleIntegerProperty(quantite);
        this.prixUnitaire = new SimpleDoubleProperty(prixUnitaire);
        this.datePeremption = new SimpleStringProperty(datePeremption);
    }

    public String getCode() { return code.get(); }
    public StringProperty codeProperty() { return code; }
    public void setCode(String code) { this.code.set(code); }

    public String getDesignation() { return designation.get(); }
    public StringProperty designationProperty() { return designation; }
    public void setDesignation(String designation) { this.designation.set(designation); }

    public int getQuantite() { return quantite.get(); }
    public IntegerProperty quantiteProperty() { return quantite; }
    public void setQuantite(int quantite) { this.quantite.set(quantite); }

    public double getPrixUnitaire() { return prixUnitaire.get(); }
    public DoubleProperty prixUnitaireProperty() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire.set(prixUnitaire); }

    public String getDatePeremption() { return datePeremption.get(); }
    public StringProperty datePeremptionProperty() { return datePeremption; }
    public void setDatePeremption(String datePeremption) { this.datePeremption.set(datePeremption); }
}
