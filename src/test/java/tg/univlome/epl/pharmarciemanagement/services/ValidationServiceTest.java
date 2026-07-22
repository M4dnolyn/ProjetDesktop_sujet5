package tg.univlome.epl.pharmarciemanagement.services;

import org.junit.Test;
import tg.univlome.epl.pharmarciemanagement.exceptions.DatabaseException;
import tg.univlome.epl.pharmarciemanagement.exceptions.ValidationException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ValidationServiceTest {

    private final ValidationService service = new ValidationService();

    // Tests Quantité
    @Test(expected = ValidationException.class)
    public void testQuantite_invalidText() throws ValidationException {
        service.validateQuantite("abc");
    }

    @Test(expected = ValidationException.class)
    public void testQuantite_negative() throws ValidationException {
        service.validateQuantite("-3");
    }

    @Test(expected = ValidationException.class)
    public void testQuantite_empty() throws ValidationException {
        service.validateQuantite("");
    }

    @Test
    public void testQuantite_valid() throws ValidationException {
        service.validateQuantite("10");
    }

    @Test
    public void testQuantite_zero() throws ValidationException {
        service.validateQuantite("0");
    }

    // Tests Prix
    @Test
    public void testPrix_withComma() throws ValidationException {
        service.validatePrix("12,5");
    }

    @Test
    public void testPrix_withDot() throws ValidationException {
        service.validatePrix("12.5");
    }

    @Test(expected = ValidationException.class)
    public void testPrix_invalidText() throws ValidationException {
        service.validatePrix("abc");
    }

    @Test(expected = ValidationException.class)
    public void testPrix_negative() throws ValidationException {
        service.validatePrix("-10.5");
    }

    @Test(expected = ValidationException.class)
    public void testPrix_empty() throws ValidationException {
        service.validatePrix("");
    }

    @Test
    public void testPrix_zero() throws ValidationException {
        service.validatePrix("0");
    }

    // Tests Code
    @Test(expected = ValidationException.class)
    public void testCode_empty() throws ValidationException, DatabaseException {
        service.validateCode("");
    }

    @Test(expected = ValidationException.class)
    public void testCode_null() throws ValidationException, DatabaseException {
        service.validateCode(null);
    }

    // Tests Désignation
    @Test(expected = ValidationException.class)
    public void testDesignation_empty() throws ValidationException {
        service.validateDesignation("");
    }

    @Test(expected = ValidationException.class)
    public void testDesignation_null() throws ValidationException {
        service.validateDesignation(null);
    }

    @Test
    public void testDesignation_valid() throws ValidationException {
        service.validateDesignation("Paracétamol");
    }

    // Tests Date de Péremption
    @Test(expected = ValidationException.class)
    public void testDatePeremption_empty() throws ValidationException {
        service.validateDatePeremption("");
    }

    @Test(expected = ValidationException.class)
    public void testDatePeremption_null() throws ValidationException {
        service.validateDatePeremption(null);
    }

    @Test(expected = ValidationException.class)
    public void testDatePeremption_invalidFormat() throws ValidationException {
        service.validateDatePeremption("31/12/2025");
    }

    @Test(expected = ValidationException.class)
    public void testDatePeremption_pastDate() throws ValidationException {
        String pastDate = LocalDate.now().minusDays(1).toString();
        service.validateDatePeremption(pastDate);
    }

    @Test
    public void testDatePeremption_today() throws ValidationException {
        String today = LocalDate.now().toString();
        service.validateDatePeremption(today);
    }

    @Test
    public void testDatePeremption_futureDate() throws ValidationException {
        String futureDate = LocalDate.now().plusMonths(6).toString();
        service.validateDatePeremption(futureDate);
    }

    @Test
    public void testDatePeremption_validFormat_YYYY_MM_DD() throws ValidationException {
        String futureDate = LocalDate.now().plusMonths(6).toString();
        service.validateDatePeremption(futureDate);
    }

    // Tests validateAll
    @Test(expected = ValidationException.class)
    public void testValidateAll_invalidQuantite() throws ValidationException, DatabaseException {
        String futureDate = LocalDate.now().plusMonths(6).toString();
        service.validateAll("CODE001", "Test", "abc", "100", futureDate);
    }

    @Test(expected = ValidationException.class)
    public void testValidateAll_invalidDate() throws ValidationException, DatabaseException {
        service.validateAll("CODE001", "Test", "10", "100", "31/12/2025");
    }
}

