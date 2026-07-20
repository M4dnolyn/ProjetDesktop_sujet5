package tg.univlome.epl.pharmarciemanagement.services;

import org.junit.Test;
import tg.univlome.epl.pharmarciemanagement.exceptions.ValidationException;

import static org.junit.Assert.*;

public class ValidationServiceTest {

    private final ValidationService service = new ValidationService();

    @Test(expected = ValidationException.class)
    public void testQuantite_invalidText() throws ValidationException {
        service.validateQuantite("abc");
    }

    @Test(expected = ValidationException.class)
    public void testQuantite_negative() throws ValidationException {
        service.validateQuantite("-3");
    }

    @Test
    public void testQuantite_valid() throws ValidationException {
        service.validateQuantite("10");
    }

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
    public void testCode_empty() throws ValidationException {
        service.validateCode("");
    }
}
