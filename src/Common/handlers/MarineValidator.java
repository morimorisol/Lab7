package Common.handlers;



import Common.entities.SpaceMarine;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
//алмтмвща
public final class MarineValidator {

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final javax.validation.Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    private MarineValidator() {

    }

    public static boolean validateSpaceMarine(SpaceMarine spaceMarine) {
        Set<ConstraintViolation<SpaceMarine>> validateResult = VALIDATOR.validate(spaceMarine);
        if (validateResult.size() > 0) {
            for (ConstraintViolation<SpaceMarine> violation : validateResult) {
                TextFormatter.printErrorMessage(violation.getPropertyPath() + " " + violation.getMessage());
            }
            return false;
        }
        return true;
    }

    public static <T> boolean validateField(T t, String fieldName) {
        Set<ConstraintViolation<T>> validateResult = VALIDATOR.validateProperty(t, fieldName);
        if (validateResult.size() > 0) {
            for (ConstraintViolation<T> violation : validateResult) {
                TextFormatter.printErrorMessage(violation.getPropertyPath() + " " + violation.getMessage());
            }
            return false;
        }
        return true;
    }
}