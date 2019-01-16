package codesquad.validate;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsResponse  {
    private List<ValidationError> errors;

    public ValidationErrorsResponse() {
        errors = new ArrayList<>();
    }

    public void addValidationError(ValidationError error) {
        errors.add(error);
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
