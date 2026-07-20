package tg.univlome.epl.pharmarciemanagement.services;

import tg.univlome.epl.pharmarciemanagement.dao.MedicamentDAO;
import tg.univlome.epl.pharmarciemanagement.exceptions.ValidationException;

public class ValidationService {

    private final MedicamentDAO medicamentDAO;

    public ValidationService() {
        this.medicamentDAO = new MedicamentDAO();
    }

    public void validateCode(String code) throws ValidationException {
        if (code == null || code.trim().isEmpty()) {
            throw new ValidationException("Le code ne peut pas être vide.");
        }
        if (medicamentDAO.codeExists(code.trim())) {
            throw new ValidationException("Ce code existe déjà en base.");
        }
    }

    public void validateDesignation(String designation) throws ValidationException {
        if (designation == null || designation.trim().isEmpty()) {
            throw new ValidationException("La désignation est obligatoire.");
        }
    }

    public void validateQuantite(String quantiteText) throws ValidationException {
        if (quantiteText == null || quantiteText.trim().isEmpty()) {
            throw new ValidationException("La quantité est obligatoire.");
        }
        try {
            int qte = Integer.parseInt(quantiteText.trim());
            if (qte < 0) {
                throw new ValidationException("La quantité doit être un entier positif ou nul.");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("La quantité doit être un nombre entier.");
        }
    }

    public void validatePrix(String prixText) throws ValidationException {
        if (prixText == null || prixText.trim().isEmpty()) {
            throw new ValidationException("Le prix unitaire est obligatoire.");
        }
        try {
            double prix = Double.parseDouble(prixText.trim().replace(",", "."));
            if (prix < 0) {
                throw new ValidationException("Le prix doit être un nombre positif ou nul.");
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Le prix doit être un nombre valide.");
        }
    }

    public void validateDatePeremption(String date) throws ValidationException {
        if (date == null || date.trim().isEmpty()) {
            throw new ValidationException("La date de péremption est obligatoire.");
        }
    }

    public void validateAll(String code, String designation, String quantite, String prix, String datePeremption)
            throws ValidationException {
        validateCode(code);
        validateDesignation(designation);
        validateQuantite(quantite);
        validatePrix(prix);
        validateDatePeremption(datePeremption);
    }
}
